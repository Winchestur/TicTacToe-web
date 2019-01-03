package com.game.tictactoe.config;

import com.cyecize.summer.areas.security.models.SecurityConfig;
import com.cyecize.summer.common.annotations.Bean;
import com.cyecize.summer.common.annotations.BeanConfig;
import org.modelmapper.ModelMapper;

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
}
