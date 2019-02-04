package com.game.tictactoe.areas.pushNotifications.services;

import com.cyecize.summer.common.annotations.PostConstruct;
import com.cyecize.summer.common.annotations.Service;
import com.game.tictactoe.utils.Pair;
import com.game.tictactoe.areas.pushNotifications.models.PushNotification;
import com.game.tictactoe.areas.pushNotifications.sockets.NotificationsWebSocketServer;
import com.game.tictactoe.areas.users.entities.User;
import com.game.tictactoe.constants.WebConstants;
import com.game.tictactoe.utils.SocketUtils;
import com.google.gson.Gson;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Gson GSON_INSTANCE = new Gson();

    private final NotificationsWebSocketServer webSocketServer;

    /**
     * Stores currently authorized sessions.
     */
    private Map<String, User> sessionUserMap;

    /**
     * Stores a list of notifications that need to be sent a given sessionId
     */
    private Map<String, List<PushNotification>> sessionNotificationsMap;

    /**
     * Stores a id of a use as a key and a list of notifications and expire time
     * that a user needs to receive.
     * <p>
     * This is a temporary storage for the notifications until a session with the same user pops up.
     * Then the data will the transferred to @sessionNotificationsMap.
     */
    private Map<Long, List<Pair<Long, PushNotification>>> offlineUserIdNotificationMap;

    public NotificationServiceImpl(NotificationsWebSocketServer webSocketServer) {
        this.webSocketServer = webSocketServer;
        this.sessionUserMap = new ConcurrentHashMap<>();
        this.sessionNotificationsMap = new ConcurrentHashMap<>();
        this.offlineUserIdNotificationMap = new ConcurrentHashMap<>();
        this.initSocketEvents();
    }

    @PostConstruct
    public void initAsyncMessageWatcher() {
        Timer offlineUsersTransferWatcher = new Timer(2000, this::onUserToSessionIdTransfer);
        offlineUsersTransferWatcher.setRepeats(true);
        offlineUsersTransferWatcher.start();

        Timer asyncMessageSendWatcher = new Timer(1000, this::onAsyncMessageSend);
        asyncMessageSendWatcher.setRepeats(true);
        asyncMessageSendWatcher.start();
    }

    @Override
    public void sendToAll(PushNotification message) {
        this.webSocketServer.sendMessage(stringifyPushNotification(message));
    }

    /**
     * Checks if any sessions are found for the given user and if they are found, adds the notification
     * to a map of session and a list of notifications.
     * <p>
     * If no user session is found, adds the notification to a temporary map of userId and a list of notifications
     * where the data will be kept until a session is present or time expires.
     *
     * @param user
     * @param message
     */
    @Override
    public void sendAsync(User user, PushNotification message) {
        List<String> sessionIds = this.findSessionIdFromUser(user.getId());

        if (sessionIds.size() > 0) {
            for (String sessionId : sessionIds) {
                this.addNotificationToSessionNotificationMap(sessionId, message);
            }
        } else {
            if (!this.offlineUserIdNotificationMap.containsKey(user.getId())) {
                this.offlineUserIdNotificationMap.put(user.getId(), new ArrayList<>());
            }

            this.offlineUserIdNotificationMap.get(user.getId()).add(new Pair<>(new Date().getTime() + WebConstants.ONLINE_PLAYER_LEASE_TIME_MILLIS, message));
        }
    }

    @Override
    public void sendAsync(String sessionId, PushNotification message) {
        this.addNotificationToSessionNotificationMap(sessionId, message);
    }

    @Override
    public boolean sendToUser(User user, PushNotification message) {
        List<String> sessionIds = this.findSessionIdFromUser(user.getId());

        if (sessionIds.size() < 1) {
            return false;
        }

        for (String sessionId : sessionIds) {
            this.webSocketServer.sendMessageToSpecificSubscriber(sessionId, stringifyPushNotification(message));
        }

        return true;
    }

    @Override
    public boolean sendToUser(String sessionId, PushNotification message) {
        return this.webSocketServer.sendMessageToSpecificSubscriber(sessionId, stringifyPushNotification(message));
    }

    private synchronized void addNotificationToSessionNotificationMap(String sessionId, PushNotification notification) {
        if (!this.sessionNotificationsMap.containsKey(sessionId)) {
            this.sessionNotificationsMap.put(sessionId, new ArrayList<>());
        }

        this.sessionNotificationsMap.get(sessionId).add(notification);
    }

    private List<String> findSessionIdFromUser(Long userId) {
        return this.sessionUserMap.entrySet().stream()
                .filter(kvp -> kvp.getValue().getId().equals(userId))
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    //Socket Events
    private void initSocketEvents() {
        this.webSocketServer.setOnClose(this::onSocketClose);
        this.webSocketServer.setOnOpen(this::onSocketOpen);
    }

    private boolean onSocketOpen(WebSocket webSocket, ClientHandshake handshake, String sessionId) {
        User user = SocketUtils.getUserFromSession(sessionId);

        if (user != null) {
            this.sessionUserMap.put(sessionId, user);
            return true;
        }

        return false;
    }

    private void onSocketClose(WebSocket conn, int code, String reason, boolean remote) {
        String sessionId = SocketUtils.extractSessionId(conn.getResourceDescriptor());
        this.sessionUserMap.remove(sessionId);
    }

    //Cron Job Events

    /**
     * Iterates through all entries in the temporary map of userIds and notifications.
     * <p>
     * If suitable session for userId is present, transfers the data to a map of session and notifications.
     * Otherwise it filters out the expired notifications and moves on.
     *
     * @param event
     */
    private synchronized void onUserToSessionIdTransfer(ActionEvent event) {
        long currentTime = new Date().getTime();
        List<Long> usersToRemoveFromMap = new ArrayList<>();

        //Iterate all entries and filter out the users that went online.
        for (Map.Entry<Long, List<Pair<Long, PushNotification>>> userPairsEntry : this.offlineUserIdNotificationMap.entrySet()) {
            Long userId = userPairsEntry.getKey();
            List<Pair<Long, PushNotification>> timeoutAndNotificationPairs = userPairsEntry.getValue();

            List<String> sessionIds = this.findSessionIdFromUser(userPairsEntry.getKey());
            //If a session is not found yet, filter out the expired notifications.
            if (sessionIds.size() < 1) {
                var filteredNotificationPairs = timeoutAndNotificationPairs.stream().filter(kvp -> kvp.getKey() < currentTime).collect(Collectors.toList());

                if (filteredNotificationPairs.size() < 1) {
                    usersToRemoveFromMap.add(userId);
                } else {
                    this.offlineUserIdNotificationMap.put(userId, filteredNotificationPairs);
                }

                continue;
            }

            //Transfer all notifications to the sessionNotificationsMap
            for (Pair<Long, PushNotification> timeoutAndNotificationPair : timeoutAndNotificationPairs) {
                for (String sessionId : sessionIds) {
                    this.addNotificationToSessionNotificationMap(sessionId, timeoutAndNotificationPair.getValue());
                }
            }

            usersToRemoveFromMap.add(userId);
        }

        this.offlineUserIdNotificationMap = this.offlineUserIdNotificationMap.entrySet().stream()
                .filter((k) -> !usersToRemoveFromMap.contains(k.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Iterates all entries of a map with sessionId as a key and a list of notifications as a value.
     * Calls the webSocket service to send the message to the given sessionId.
     * <p>
     * Clears the list of notifications for that session in the end.
     *
     * @param event
     */
    private synchronized void onAsyncMessageSend(ActionEvent event) {
        for (Map.Entry<String, List<PushNotification>> sessionIdNotificationsEntry : this.sessionNotificationsMap.entrySet()) {
            String sessionId = sessionIdNotificationsEntry.getKey();
            if (!this.webSocketServer.getSockets().containsKey(sessionId)) {
                continue;
            }

            List<PushNotification> notifications = sessionIdNotificationsEntry.getValue();

            for (PushNotification notification : notifications) {
                webSocketServer.sendMessageToSpecificSubscriber(sessionId, stringifyPushNotification(notification));
            }

            this.sessionNotificationsMap.put(sessionId, new ArrayList<>());
        }
    }

    //Static Utils
    private static String stringifyPushNotification(PushNotification pushNotification) {
        return GSON_INSTANCE.toJson(pushNotification);
    }
}

