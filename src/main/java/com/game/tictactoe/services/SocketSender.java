package com.game.tictactoe.services;

import com.game.tictactoe.sockets.events.OnMessage;

public interface SocketSender {

    void sendMessage(String message);

    void sendMessage(byte[] message);

    void setOnMessageEvent(OnMessage onMessageEvent);
}
