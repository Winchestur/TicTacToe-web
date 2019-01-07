package com.game.tictactoe.config;

import com.cyecize.summer.areas.security.models.SecurityConfig;
import com.cyecize.summer.common.annotations.Bean;
import com.cyecize.summer.common.annotations.BeanConfig;
import com.game.tictactoe.areas.onlinePlayers.sockets.OnlinePlayerWebSocketServer;
import com.game.tictactoe.constants.WebSocketConstants;
import org.modelmapper.ModelMapper;

import java.net.InetSocketAddress;

@BeanConfig
public class BeanConfiguration {

    @Bean
    public SecurityConfig getSecurityConfig() {
        return new SecurityConfig()
                .setLogoutURL("/logout")
                .setLogoutRedirectURL("/")
                .setLoginURL("/login")
                .setUnauthorizedURL("/unauthorized");
    }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public OnlinePlayerWebSocketServer getOnlinePlayerWebSocketServer() {
        OnlinePlayerWebSocketServer socketServer = new OnlinePlayerWebSocketServer(new InetSocketAddress(WebSocketConstants.ONLINE_PLAYERS_SOCKET_PORT));
        socketServer.start();
        return socketServer;
    }
}
