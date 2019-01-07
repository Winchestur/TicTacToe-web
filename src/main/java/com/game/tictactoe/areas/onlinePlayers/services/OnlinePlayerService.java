package com.game.tictactoe.areas.onlinePlayers.services;

import com.game.tictactoe.areas.users.entities.User;

import java.util.List;

public interface OnlinePlayerService {

    void putOffline(User user);

    void addOnlineUser(User user);

    List<String> findOnlinePlayerNames();

    List<User> findOnlinePlayers();
}
