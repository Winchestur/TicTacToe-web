package com.game.tictactoe.areas.pushNotifications.sockets;

import com.cyecize.summer.common.annotations.Service;
import com.game.tictactoe.constants.WebSocketConstants;
import com.game.tictactoe.sockets.BaseWebSocketServer;

import java.net.InetSocketAddress;

@Service
public class NotificationsWebSocketServer extends BaseWebSocketServer {
    public NotificationsWebSocketServer() {
        super(new InetSocketAddress(WebSocketConstants.NOTIFICATIONS_SOCKET_PORT));
        super.start();
    }
}
