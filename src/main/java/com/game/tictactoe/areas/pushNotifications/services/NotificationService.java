package com.game.tictactoe.areas.pushNotifications.services;

import com.game.tictactoe.areas.pushNotifications.models.PushNotification;
import com.game.tictactoe.areas.users.entities.User;

public interface NotificationService {

    void sendToAll(PushNotification message);

    boolean sendToUser(User user, PushNotification message);

    boolean sendToUser(String sessionId, PushNotification message);
}
