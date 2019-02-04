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

    var notificationTypeHandler = {
        GAME_INVITE: function (notification) {
            console.log(notification);
            var notificationElement = NotificationViewManager.createNotification(notification.message + ' wants to play with you!',
                notificationSeverity[notification.severity],
                function (accept) {
                    alert(accept)
                },
                function (decline) {
                    alert(decline + ' declined');
                });

            NotificationViewManager.showNotification(notificationElement, 10000)
        },
        NOTIFICATION: function (notification) {
            var notificationElement = NotificationViewManager.createNotification(notification.message.message, notificationSeverity[notification.severity]);

            NotificationViewManager.showNotification(notificationElement, 10000);
        }
    };

    notificationManager.onNotificationCallback = function (notification) {
        notificationTypeHandler[notification.notificationType](notification);

    }
});