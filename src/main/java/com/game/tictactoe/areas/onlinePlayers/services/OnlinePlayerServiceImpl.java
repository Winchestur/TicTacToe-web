package com.game.tictactoe.areas.onlinePlayers.services;

import com.cyecize.http.HttpSessionStorage;
import com.cyecize.summer.common.annotations.PostConstruct;
import com.cyecize.summer.common.annotations.Service;
import com.game.tictactoe.areas.onlinePlayers.entities.OnlinePlayer;
import com.game.tictactoe.areas.onlinePlayers.repositories.OnlinePlayerRepository;
import com.game.tictactoe.areas.onlinePlayers.sockets.OnlinePlayerWebSocketServer;
import com.game.tictactoe.areas.users.entities.User;
import com.game.tictactoe.services.SocketSender;
import com.game.tictactoe.utils.SocketUtils;
import com.google.gson.Gson;

import javax.swing.*;
import java.util.Date;
import java.util.List;

@Service
public class OnlinePlayerServiceImpl implements OnlinePlayerService {

    private final OnlinePlayerRepository repository;

    private final SocketSender onlinePlayersSocket;

    private HttpSessionStorage sessionStorage;

    public OnlinePlayerServiceImpl(OnlinePlayerRepository repository, OnlinePlayerWebSocketServer onlinePlayersSocket) {
        this.repository = repository;
        this.onlinePlayersSocket = onlinePlayersSocket;
    }

    @PostConstruct
    public void startOnlinePlayersRefreshTimer() {
        this.onlinePlayersSocket.setOnMessageEvent(this::onSocketMessage);
        Gson gson = new Gson();

        Timer refreshTimer = new Timer(1000, e -> {
            this.repository.filterOnlinePlayers(new Date().getTime() - 30000); // 1 min
            this.onlinePlayersSocket.sendMessage(gson.toJson(this.findOnlinePlayerNames()));
        });

        refreshTimer.setRepeats(true);
        refreshTimer.start();
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

    private void onSocketMessage(String msg) {
        User user = SocketUtils.getUserFromSession(msg);

        if (user == null) {
            return;
        }

        this.addOnlineUser(user);
    }
}
