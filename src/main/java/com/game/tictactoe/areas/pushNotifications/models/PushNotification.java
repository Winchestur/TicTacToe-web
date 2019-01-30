package com.game.tictactoe.areas.pushNotifications.models;

import com.game.tictactoe.areas.pushNotifications.enums.NotificationSeverity;
import com.game.tictactoe.areas.pushNotifications.enums.NotificationType;


public class PushNotification {

    private static long idAutoIncrement = 0;

    private long id;

    private NotificationSeverity severity;

    private NotificationType notificationType;

    private Object message;

    public PushNotification(NotificationSeverity severity, NotificationType notificationType, Object message) {
        this.severity = severity;
        this.notificationType = notificationType;
        this.message = message;
        this.id = ++idAutoIncrement;
    }

    public long getId() {
        return this.id;
    }
}
