package com.game.tictactoe.sockets.events;

import org.java_websocket.WebSocket;

public interface OnMessage {

    void onMessage(WebSocket webSocket, String message);
}
