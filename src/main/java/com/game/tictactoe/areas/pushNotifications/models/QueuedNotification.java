package com.game.tictactoe.areas.pushNotifications.models;

public class QueuedNotification {

    private String targetSessionId;

    private PushNotification notification;

    public QueuedNotification(String targetSessionId, PushNotification notification) {
        this.targetSessionId = targetSessionId;
        this.notification = notification;
    }

    public PushNotification getNotification() {
        return this.notification;
    }

    public String getTargetSessionId() {
        return this.targetSessionId;
    }
}
