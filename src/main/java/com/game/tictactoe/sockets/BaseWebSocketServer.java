package com.game.tictactoe.sockets;

import com.game.tictactoe.sockets.events.OnClose;
import com.game.tictactoe.sockets.events.OnMessage;
import com.game.tictactoe.sockets.events.OnOpen;
import com.game.tictactoe.utils.SocketUtils;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.*;

public abstract class BaseWebSocketServer extends WebSocketServer {

    private Map<String, List<WebSocket>> sockets;

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
                //return;
            }
        }

        this.addSocketConnection(sessionId, webSocket);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        if (this.onClose != null) {
            this.onClose.onClose(conn, code, reason, remote);
        }

        this.removeSocketConnection(SocketUtils.extractSessionId(conn.getResourceDescriptor()), conn);
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
        for (List<WebSocket> sockets : this.getSockets().values()) {
            for (WebSocket webSocket : sockets) {
                webSocket.send(message);
            }
        }
    }

    public void sendMessage(byte[] message) {
        for (List<WebSocket> sockets : this.getSockets().values()) {
            for (WebSocket webSocket : sockets) {
                webSocket.send(message);
            }
        }
    }

    public boolean sendMessageToSpecificSubscriber(String sessionId, String message) {
        if (this.getSockets().containsKey(sessionId)) {
            this.getSockets().get(sessionId).forEach(s -> {
                s.send(message);
            });
            return true;
        }

        return false;
    }

    public Map<String, List<WebSocket>> getSockets() {
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

    private void addSocketConnection(String sessionId, WebSocket socket) {
        if (!this.getSockets().containsKey(sessionId)) {
            this.getSockets().put(sessionId, new ArrayList<>());
        }

        this.getSockets().get(sessionId).add(socket);
        System.out.println(this.getSockets().get(sessionId).size());
    }

    private void removeSocketConnection(String sessionId, WebSocket webSocket) {
        if (!this.getSockets().containsKey(sessionId)) {
            return;
        }

        List<WebSocket> sockets = this.getSockets().get(sessionId);
        sockets.remove(webSocket);

        if (sockets.size() < 1) {
            this.getSockets().remove(sessionId);
        }
    }
}
