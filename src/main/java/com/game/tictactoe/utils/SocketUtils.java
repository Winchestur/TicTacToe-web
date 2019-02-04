package com.game.tictactoe.utils;

import com.cyecize.http.HttpSession;
import com.cyecize.http.HttpSessionStorage;
import com.cyecize.solet.SoletConfig;
import com.cyecize.summer.SummerBootApplication;
import com.cyecize.summer.constants.SecurityConstants;
import com.game.tictactoe.areas.users.entities.User;
import com.game.tictactoe.constants.WebSocketConstants;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

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
     * Looks for token key in the query parameters map.
     */
    public static String extractSessionId(String webSocketQueryParams) {
        Map<String, String> parsedQueryParams = extractQueryParameters(webSocketQueryParams);

        return parsedQueryParams.get(WebSocketConstants.SOCKET_INIT_SESSION_ID_PARAM_NAME);
    }

    /**
     * Parse raw query parameter string by decoding it first with URLDecoder.
     * Example input - "/?token=randomUUID&Key=Value"
     */
    public static Map<String, String> extractQueryParameters(String webSocketQueryParams) {
        webSocketQueryParams = URLDecoder.decode(webSocketQueryParams, StandardCharsets.UTF_8);

        Map<String, String> queryParams = new HashMap<>();

        if (webSocketQueryParams == null || !webSocketQueryParams.contains("?")) {
            return queryParams;
        }

        String queryString = webSocketQueryParams.split("\\?")[1];
        String[] queryParameters = queryString.split("&");

        for (String queryParameter : queryParameters) {
            String[] keyValuePair = queryParameter.split("=");

            if (keyValuePair.length == 1 || (keyValuePair.length == 2 && keyValuePair[1].trim().equals(""))) {
                queryParams.put(keyValuePair[0], null);
            } else if (keyValuePair.length == 2) {
                queryParams.put(keyValuePair[0], keyValuePair[1]);
            }
        }

        return queryParams;
    }
}
