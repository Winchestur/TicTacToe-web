var NotificationSocketManager = function (port, onMessageCallback) {
    this.onNotificationCallback = onMessageCallback;

    var connection = webSocketUtils.createWebSocket(port);

    connection.onmessage = function (msg) {
        if (this.onNotificationCallback) {
            this.onNotificationCallback(JSON.parse(msg.data));
        }
    }.bind(this);

    connection.onerror = webSocketUtils.onSocketError;
};

$(function () {
    var notificationManager = new NotificationSocketManager(constants.NOTIFICATIONS_SOCKET_PORT);

    notificationManager.onNotificationCallback = function (data) {
        console.log(data);

        var notification = NotificationViewManager.createNotification(data.message, notificationSeverity[data.severity], function () {
            alert('accepred');
        }, function () {
            alert('declined');
        });

        NotificationViewManager.showNotification(notification, 5000);
    }
});