package com.game.tictactoe.sockets;

import com.game.tictactoe.sockets.events.OnClose;
import com.game.tictactoe.sockets.events.OnMessage;
import com.game.tictactoe.sockets.events.OnOpen;
import com.game.tictactoe.utils.SocketUtils;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class BaseWebSocketServer extends WebSocketServer {

    private Map<String, WebSocket> sockets;

    private OnMessage onMessage;

    private OnOpen onOpen;

    private OnClose onClose;

    public BaseWebSocketServer(InetSocketAddress address) {
        super(address);
        this.sockets = new HashMap<>();
    }

    /**
     * Check if event is present. If the event returns false, do not add
     * to the set of web sockets.
     */
    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        String sessionId = SocketUtils.extractSessionId(clientHandshake.getResourceDescriptor());
        if (this.onOpen != null) {
            if (!this.onOpen.onOpen(webSocket, clientHandshake, sessionId)) {
                return;
            }
        }
        this.sockets.put(sessionId, webSocket);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        if (this.onClose != null) {
            this.onClose.onClose(conn, code, reason, remote);
        }

        this.sockets.remove(SocketUtils.extractSessionId(conn.getResourceDescriptor()));
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println("Error in Socket");
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println(String.format("Web Socket Listening on port %d", super.getAddress().getPort()));
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        if (this.onMessage != null) {
            this.onMessage.onMessage(conn, message);
        }
    }

    public void sendMessage(String message) {
        for (WebSocket socket : this.sockets.values()) {
            socket.send(message);
        }
    }

    public void sendMessage(byte[] message) {
        for (WebSocket socket : this.sockets.values()) {
            socket.send(message);
        }
    }

    public Map<String, WebSocket> getSockets() {
        return this.sockets;
    }

    //Setters for events
    public void setOnMessage(OnMessage onMessageEvent) {
        this.onMessage = onMessageEvent;
    }

    public void setOnOpen(OnOpen onOpen) {
        this.onOpen = onOpen;
    }

    public void setOnClose(OnClose onClose) {
        this.onClose = onClose;
    }
}