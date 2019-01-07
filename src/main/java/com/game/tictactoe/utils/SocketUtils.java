package com.game.tictactoe.utils;

import com.cyecize.http.HttpSession;
import com.cyecize.http.HttpSessionStorage;
import com.cyecize.solet.SoletConfig;
import com.cyecize.summer.SummerBootApplication;
import com.cyecize.summer.constants.SecurityConstants;
import com.game.tictactoe.areas.users.entities.User;

public class SocketUtils {

    private static SoletConfig soletConfig = null;

    private static HttpSessionStorage sessionStorage = null;

    private static SoletConfig getSoletConfig() {
        if (soletConfig == null) {
            soletConfig = SummerBootApplication.dependencyContainer.getObject(SoletConfig.class);
        }

        return soletConfig;
    }

    private static HttpSessionStorage getSessionStorage() {
        if (sessionStorage == null) {
            sessionStorage = (HttpSessionStorage) getSoletConfig().getAttribute("SESSION_STORAGE");
        }

        return sessionStorage;
    }

    public static User getUserFromSession(String sessionId) {
        HttpSession session = getSessionStorage().getSession(sessionId);

        if (session == null) {
            return null;
        }

        return (User) session.getAttribute(SecurityConstants.SESSION_USER_DETAILS_KEY);
    }
}
