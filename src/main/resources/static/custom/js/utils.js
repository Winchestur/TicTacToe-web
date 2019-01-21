var utils = {
    getCookie: function (cname) {
        var name = cname + "=";
        var decodedCookie = decodeURIComponent(document.cookie);
        var ca = decodedCookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') {
                c = c.substring(1);
            }
            if (c.indexOf(name) == 0) {
                return c.substring(name.length, c.length);
            }
        }
        return "";
    },

    randomDomId: function () {
        var id = null;

        do {
            id = Math.random().toString(36).replace(/[^a-z]+/g, '');
        } while (document.getElementById(id) != null);

        return id;
    }
};

var webSocketUtils = {
    createWebSocket: function (port) {
        var baseUrl = BASE_URL;

        //If the base url has port, remove it.
        if (baseUrl.indexOf(':') !== -1) {
            baseUrl = baseUrl.substring(0, baseUrl.indexOf(':'));
        }

        return new WebSocket(
            "ws:" + baseUrl + ":" + port +
            "?" + constants.SOCKET_INIT_SESSION_ID_PARAM_NAME + "=" + utils.getCookie(constants.JAVACHE_SESSION_ID)
        );
    },

    onSocketError: function (error) {
        alert("Error with socket: " + error);
        console.log(error);
        //TODO change the error handling for default socket error handler.
    }
};
