var OnlinePlayerManager = function (port, onUsersUpdateCallback) {
    this.onUsersUpdateCallback = onUsersUpdateCallback;

    var connection = webSocketUtils.createWebSocket(port);

    connection.onmessage = function (msg) {
        if (this.onUsersUpdateCallback) {
            this.onUsersUpdateCallback(msg.data);
        }
    }.bind(this);

    //TODO add a way to keep the user alive
    connection.onerror = webSocketUtils.onSocketError;
};

var OnlinePlayerViewManager = function (onlinePlayersContainer) {
    onlinePlayersContainer = $(onlinePlayersContainer);

    function removeAll() {
        onlinePlayersContainer.html('');
    }

    function addOnlinePlayers(usernameArray) {
        removeAll();

        usernameArray.forEach(function (username) {
            var onlinePlayerTemplate = $('<a href="#" class="list-group-item online-player">');
            onlinePlayerTemplate.text(username);
            onlinePlayersContainer.append(onlinePlayerTemplate);
        });
    }

    return {
        addOnlinePlayers: addOnlinePlayers,
    };
};

$(function () {
    var onlinePlayerManager = new OnlinePlayerManager(constants.ONLINE_PLAYER_SOCKET_PORT);
    var onlinePlayerViewManager = new OnlinePlayerViewManager($('#onlinePlayersList'));

    onlinePlayerManager.onUsersUpdateCallback = function (usernames) {
        onlinePlayerViewManager.addOnlinePlayers(JSON.parse(usernames));
    };
});