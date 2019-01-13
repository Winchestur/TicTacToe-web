package com.game.tictactoe.areas.onlinePlayers.services;

import com.cyecize.summer.common.annotations.PostConstruct;
import com.cyecize.summer.common.annotations.Service;
import com.game.tictactoe.areas.onlinePlayers.entities.OnlinePlayer;
import com.game.tictactoe.areas.onlinePlayers.repositories.OnlinePlayerRepository;
import com.game.tictactoe.areas.onlinePlayers.sockets.OnlinePlayerWebSocketServer;
import com.game.tictactoe.areas.users.entities.User;
import com.game.tictactoe.utils.SocketUtils;
import com.google.gson.Gson;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import javax.swing.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OnlinePlayerServiceImpl implements OnlinePlayerService {

    private final OnlinePlayerRepository repository;

    private final OnlinePlayerWebSocketServer onlinePlayersSocket;

    private Map<String, User> sessionUserMap;

    public OnlinePlayerServiceImpl(OnlinePlayerRepository repository, OnlinePlayerWebSocketServer onlinePlayersSocket) {
        this.repository = repository;
        this.onlinePlayersSocket = onlinePlayersSocket;
        this.sessionUserMap = new HashMap<>();

        this.onlinePlayersSocket.setOnOpen(this::onSocketOpen);
        this.onlinePlayersSocket.setOnClose(this::onSocketClose);
    }

    @PostConstruct
    public void startOnlinePlayersRefreshTimer() {
        Gson gson = new Gson();

        Timer refreshTimer = new Timer(1000, e -> {
            this.repository.filterOnlinePlayers(new Date().getTime() - 30000); // 1 min
            this.onlinePlayersSocket.sendMessage(gson.toJson(this.findOnlinePlayerNames()));

           synchronized (this.onlinePlayersSocket) {
               for (User user : this.sessionUserMap.values()) {
                   this.addOnlineUser(user);//TODO put this code in a bigger timer.
               }
           }
        });

        refreshTimer.setRepeats(true);
        refreshTimer.start();
    }

    @Override
    public void putOffline(User user) {
        OnlinePlayer onlinePlayer = this.repository.findByUser(user);
        if (onlinePlayer != null) {
            this.repository.remove(onlinePlayer);
        }
    }

    @Override
    public void addOnlineUser(User user) {
        OnlinePlayer onlinePlayer = this.repository.findByUser(user);
        if (onlinePlayer != null) {
            onlinePlayer.setLastRefreshDate(new Date().getTime());
            this.repository.merge(onlinePlayer);
        } else {
            onlinePlayer = new OnlinePlayer();
            onlinePlayer.setUser(user);
            this.repository.persist(onlinePlayer);
        }
    }

    @Override
    public List<String> findOnlinePlayerNames() {
        return this.repository.findOnlinePlayersNames();
    }

    @Override
    public List<User> findOnlinePlayers() {
        return this.repository.findOnlinePlayers();
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
}
