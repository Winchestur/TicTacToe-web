package com.game.tictactoe.sockets.events;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

public interface OnOpen {

    boolean onOpen(WebSocket webSocket, ClientHandshake clientHandshake, String sessionId);
}
