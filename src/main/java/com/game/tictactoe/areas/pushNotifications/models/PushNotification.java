package com.game.tictactoe.areas.pushNotifications.models;

import com.game.tictactoe.areas.pushNotifications.enums.NotificationSeverity;
import com.game.tictactoe.areas.pushNotifications.enums.NotificationType;


public class PushNotification {

    private NotificationSeverity severity;

    private NotificationType notificationType;

    private Object message;

    public PushNotification(NotificationSeverity severity, NotificationType notificationType, Object message) {
        this.severity = severity;
        this.notificationType = notificationType;
        this.message = message;
    }
}
