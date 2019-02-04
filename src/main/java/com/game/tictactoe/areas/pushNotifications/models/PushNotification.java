package com.game.tictactoe.areas.pushNotifications.models;

import com.game.tictactoe.areas.pushNotifications.enums.NotificationSeverity;
import com.game.tictactoe.areas.pushNotifications.enums.NotificationType;
import com.game.tictactoe.areas.pushNotifications.messages.PushMessage;

public class PushNotification {

    private static long idAutoIncrement = 0;

    private long id;

    private NotificationSeverity severity;

    private NotificationType notificationType;

    private PushMessage message;

    public PushNotification(PushMessage message) {
        this(NotificationType.NOTIFICATION, message);
    }

    public PushNotification(NotificationType notificationType, PushMessage message) {
        this(NotificationSeverity.INFO, notificationType, message);
    }

    public PushNotification(NotificationSeverity severity, NotificationType notificationType, PushMessage message) {
        this.severity = severity;
        this.notificationType = notificationType;
        this.message = message;
        this.id = ++idAutoIncrement;
    }

    public long getId() {
        return this.id;
    }
}
