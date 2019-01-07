package com.game.tictactoe.sockets;

import com.game.tictactoe.services.SocketSender;
import com.game.tictactoe.sockets.events.OnMessage;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseWebSocketServer extends WebSocketServer implements SocketSender {

    protected Set<WebSocket> sockets;

    protected OnMessage onMessage;

    public BaseWebSocketServer(InetSocketAddress address) {
        super(address);
        this.sockets = new HashSet<>();
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        this.sockets.add(webSocket);
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        this.sockets.remove(webSocket);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        System.out.println("Error in Socket");
        e.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println(String.format("Web Socket Listening on port %d", super.getAddress().getPort()));
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        if (this.onMessage != null) {
            this.onMessage.onMessage(s);
        }
    }

    @Override
    public void setOnMessageEvent(OnMessage onMessageEvent) {
        this.onMessage = onMessageEvent;
    }

    @Override
    public void sendMessage(String message) {
        for (WebSocket socket : this.sockets) {
            socket.send(message);
        }
    }

    @Override
    public void sendMessage(byte[] message) {
        for (WebSocket socket : this.sockets) {
            socket.send(message);
        }
    }
}
