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
    }
};

var webSocketUtils = {
    createWebSocket: function (port) {
        var baseUrl = BASE_URL;
        if (baseUrl.indexOf(':') !== -1) {
            baseUrl = baseUrl.substring(0, baseUrl.indexOf(':'));
        }
        return new WebSocket("ws:" + baseUrl + ":" + port);
    },

    onSocketError: function (error) {
        alert("Error with socket: " + error);
        //TODO change the error handling for default socket error handler.
    }
};
