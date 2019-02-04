package com.game.tictactoe.areas.pushNotifications.services;

import com.game.tictactoe.areas.pushNotifications.models.PushNotification;
import com.game.tictactoe.areas.users.entities.User;

public interface NotificationService {

    void sendToAll(PushNotification message);

    void sendAsync(User user, PushNotification message);

    void sendAsync(String sessionId, PushNotification message);

    boolean sendToUser(User user, PushNotification message);

    boolean sendToUser(String sessionId, PushNotification message);
}
