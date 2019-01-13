package com.game.tictactoe.sockets.events;

import org.java_websocket.WebSocket;

public interface OnClose {

    void onClose(WebSocket conn, int code, String reason, boolean remote);
}
