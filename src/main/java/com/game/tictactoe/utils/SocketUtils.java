package com.game.tictactoe.utils;

import com.cyecize.http.HttpSession;
import com.cyecize.http.HttpSessionStorage;
import com.cyecize.solet.SoletConfig;
import com.cyecize.summer.SummerBootApplication;
import com.cyecize.summer.constants.SecurityConstants;
import com.game.tictactoe.areas.users.entities.User;
import com.game.tictactoe.constants.WebSocketConstants;

public class SocketUtils {

    private static SoletConfig soletConfig = null;

    private static HttpSessionStorage sessionStorage = null;

    //PRIVATE METHODS
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

    //PUBLIC METHODS

    /**
     * Looks for session with a given sessionId.
     * If session is present, return the Logged in user if any.
     */
    public static User getUserFromSession(String sessionId) {
        HttpSession session = getSessionStorage().getSession(sessionId);

        if (session == null) {
            return null;
        }

        return (User) session.getAttribute(SecurityConstants.SESSION_USER_DETAILS_KEY);
    }

    /**
     * Parse raw query parameter string and look for session id.
     * Example input - "/?token=randomUUID"
     */
    public static String extractSessionId(String webSocketQueryParams) {
        if (webSocketQueryParams == null || !webSocketQueryParams.contains("?")) {
            return "";
        }

        String queryString = webSocketQueryParams.split("\\?")[1];
        String[] queryParameters = queryString.split("&");

        for (String queryParameter : queryParameters) {
            String[] keyValuePair = queryParameter.split("=");

            if (keyValuePair[0].equals(WebSocketConstants.SOCKET_INIT_SESSION_ID_PARAM_NAME)) {
                return keyValuePair.length > 1 ? keyValuePair[1] : "";
            }
        }

        return "";
    }
}
