var OnlinePlayerManager = function (port, onUsersUpdateCallback) {
    this.onUsersUpdateCallback = onUsersUpdateCallback;

    var connection = webSocketUtils.createWebSocket(port);

    connection.onmessage = function (msg) {
        if (this.onUsersUpdateCallback) {
            this.onUsersUpdateCallback(JSON.parse(msg.data));
        }
    }.bind(this);
    
    connection.onerror = webSocketUtils.onSocketError;
};


var OnlinePlayerViewManager = function (onlinePlayersContainer) {
    onlinePlayersContainer = $(onlinePlayersContainer);

    function removeAll() {
        onlinePlayersContainer.html('');
    }

    function addOnlinePlayers(newUserNames, allUsers) {
        //removeAll();

        getDisplayedNamesElements().forEach(function (usernameHtmlElementPair) {
            if (allUsers.indexOf(usernameHtmlElementPair.username) === -1) {
                $(usernameHtmlElementPair.htmlElement).remove();
                console.log(usernameHtmlElementPair.username + ' went offline');
            }
        });

        newUserNames.forEach(function (username) {
            var onlinePlayerTemplate = $('<a href="#" class="list-group-item online-player">');
            onlinePlayerTemplate.text(username);
            onlinePlayersContainer.append(onlinePlayerTemplate);
        });
    }

    function getDisplayedNamesElements() {
        return onlinePlayersContainer.find('.online-player').toArray().map(function (htmlElement) {
            return {
                username: htmlElement.textContent,
                htmlElement: htmlElement,
            };
        });
    }

    return {
        addOnlinePlayers: addOnlinePlayers,
    };
};

var OnlinePlayerFilterManager = function (currentUsername) {

    var usersFromPreviousMessage = [];

    function filterNamesToDisplay(userNames) {
        userNames.splice(userNames.indexOf(currentUsername), 1);

        var usersToDisplay = userNames.filter(function (username) {
            return usersFromPreviousMessage.indexOf(username) === -1;
        });

        usersFromPreviousMessage = userNames;
        return usersToDisplay;
    }

    return {
        filterNamesToDisplay: filterNamesToDisplay,
    };
};


$(function () {
    var onlinePlayerViewManager = new OnlinePlayerViewManager($('#onlinePlayersList'));
    var onlinePlayerManager = new OnlinePlayerManager(constants.ONLINE_PLAYER_SOCKET_PORT);

    var onlinePlayerFilterManager = new OnlinePlayerFilterManager(LOGGED_USER_USERNAME);

    onlinePlayerManager.onUsersUpdateCallback = function (usernames) {
        onlinePlayerViewManager.addOnlinePlayers(onlinePlayerFilterManager.filterNamesToDisplay(usernames), usernames);
    }
});