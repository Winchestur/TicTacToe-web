package com.game.tictactoe.areas.pushNotifications.services;

import com.cyecize.summer.common.annotations.PostConstruct;
import com.cyecize.summer.common.annotations.Service;
import com.game.tictactoe.areas.pushNotifications.models.Pair;
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

    private Map<String, User> sessionUserMap;

    private Map<String, List<PushNotification>> sessionNotificationMap;

    private Map<Long, List<Pair<Long, PushNotification>>> offlineUserIdNotificationMap;

    public NotificationServiceImpl(NotificationsWebSocketServer webSocketServer) {
        this.webSocketServer = webSocketServer;
        this.sessionUserMap = new ConcurrentHashMap<>();
        this.sessionNotificationMap = new ConcurrentHashMap<>();
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

    @Override
    public void sendAsync(User user, PushNotification message) {
        long time = new Date().getTime();
        String sessionId = this.findSessionIdFromUser(user.getId());

        if (sessionId != null) {
            this.addNotificationToSessionNotificationMap(sessionId, message);
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
        String sessionId = this.findSessionIdFromUser(user.getId());

        if (sessionId == null) {
            return false;
        }

        return this.webSocketServer.sendMessageToSpecificSubscriber(sessionId, stringifyPushNotification(message));
    }

    @Override
    public boolean sendToUser(String sessionId, PushNotification message) {
        return this.webSocketServer.sendMessageToSpecificSubscriber(sessionId, stringifyPushNotification(message));
    }

    private synchronized void addNotificationToSessionNotificationMap(String sessionId, PushNotification notification) {
        if (!this.sessionNotificationMap.containsKey(sessionId)) {
            this.sessionNotificationMap.put(sessionId, new ArrayList<>());
        }

        this.sessionNotificationMap.get(sessionId).add(notification);
    }

    private String findSessionIdFromUser(Long userId) {
        Map.Entry<String, User> stringUserEntry = this.sessionUserMap.entrySet().stream()
                .filter((kvp) -> kvp.getValue().getId().equals(userId))
                .findFirst().orElse(null);

        if (stringUserEntry != null) {
            return stringUserEntry.getKey();
        }

        return null;
    }

    //Socket Events
    private void initSocketEvents() {
        this.webSocketServer.setOnClose(this::onSocketClose);
        this.webSocketServer.setOnOpen(this::onSocketOpen);
    }

    private boolean onSocketOpen(WebSocket webSocket, ClientHandshake handshake, String sessionId) {
        User user = SocketUtils.getUserFromSession(sessionId);
        System.out.println("adding " + sessionId);
        if (user != null) {
            this.sessionUserMap.put(sessionId, user);
            return true;
        }

        return false;
    }

    private void onSocketClose(WebSocket conn, int code, String reason, boolean remote) {
        String sessionId = SocketUtils.extractSessionId(conn.getResourceDescriptor());
        System.out.println("removing " + sessionId);
        this.sessionUserMap.remove(sessionId);
    }

    //Cron Job Events
    private synchronized void onUserToSessionIdTransfer(ActionEvent event) {
        long currentTime = new Date().getTime();
        List<Long> usersToRemoveFromMap = new ArrayList<>();

        //Iterate all entries and filter out the users that went online.
        for (Map.Entry<Long, List<Pair<Long, PushNotification>>> userPairsEntry : this.offlineUserIdNotificationMap.entrySet()) {
            Long userId = userPairsEntry.getKey();
            List<Pair<Long, PushNotification>> timeoutAndNotificationPairs = userPairsEntry.getValue();

            String sessionId = this.findSessionIdFromUser(userPairsEntry.getKey());
            System.out.println(sessionId);
            //If the session is not found yet, filter out the expired notifications.
            if (sessionId == null) {
                var filteredNotificationPairs = timeoutAndNotificationPairs.stream().filter(kvp -> kvp.getKey() < currentTime).collect(Collectors.toList());

                if (filteredNotificationPairs.size() < 1) {
                    usersToRemoveFromMap.add(userId);
                } else {
                    this.offlineUserIdNotificationMap.put(userId, filteredNotificationPairs);
                }

                continue;
            }

            System.out.println("sessionId is now present, transferring " + sessionId);
            //Transfer all notifications to the sessionNotificationMap
            for (Pair<Long, PushNotification> timeoutAndNotificationPair : timeoutAndNotificationPairs) {
                this.addNotificationToSessionNotificationMap(sessionId, timeoutAndNotificationPair.getValue());
            }

            usersToRemoveFromMap.add(userId);
        }

        this.offlineUserIdNotificationMap = this.offlineUserIdNotificationMap.entrySet().stream()
                .filter((k) -> !usersToRemoveFromMap.contains(k.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private synchronized void onAsyncMessageSend(ActionEvent event) {
        for (Map.Entry<String, List<PushNotification>> sessionIdNotificationsEntry : this.sessionNotificationMap.entrySet()) {
            String sessionId = sessionIdNotificationsEntry.getKey();
            if (!this.webSocketServer.getSockets().containsKey(sessionId)) {
                continue;
            }

            List<PushNotification> notifications = sessionIdNotificationsEntry.getValue();

            for (PushNotification notification : notifications) {
                System.out.println("sending to " + sessionId + " : " + notification.getId());
                webSocketServer.sendMessageToSpecificSubscriber(sessionId, stringifyPushNotification(notification));
            }

            this.sessionNotificationMap.put(sessionId, new ArrayList<>());
        }
    }

    //Static Utils
    private static String stringifyPushNotification(PushNotification pushNotification) {
        return GSON_INSTANCE.toJson(pushNotification);
    }
}

