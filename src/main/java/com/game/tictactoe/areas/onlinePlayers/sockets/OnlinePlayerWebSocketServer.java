package com.game.tictactoe.areas.onlinePlayers.sockets;

import com.cyecize.summer.common.annotations.Service;
import com.game.tictactoe.sockets.BaseWebSocketServer;

import java.net.InetSocketAddress;

@Service
public class OnlinePlayerWebSocketServer extends BaseWebSocketServer {

    public OnlinePlayerWebSocketServer(InetSocketAddress address) {
        super(address);
    }
}