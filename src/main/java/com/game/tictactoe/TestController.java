package com.game.tictactoe;

import com.cyecize.summer.areas.security.annotations.PreAuthorize;
import com.cyecize.summer.areas.security.enums.AuthorizationType;
import com.cyecize.summer.areas.security.models.Principal;
import com.cyecize.summer.common.annotations.Controller;
import com.cyecize.summer.common.annotations.routing.GetMapping;
import com.game.tictactoe.areas.pushNotifications.enums.NotificationSeverity;
import com.game.tictactoe.areas.pushNotifications.enums.NotificationType;
import com.game.tictactoe.areas.pushNotifications.models.PushNotification;
import com.game.tictactoe.areas.pushNotifications.services.NotificationService;
import com.game.tictactoe.areas.users.entities.User;

@Controller
@PreAuthorize(AuthorizationType.LOGGED_IN)
public class TestController {

    private final NotificationService notificationService;

    public TestController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @GetMapping("/test")
    public String test(Principal principal) {
        User user = (User) principal.getUser();

        PushNotification notification = new PushNotification(NotificationSeverity.SUCCESS, NotificationType.NOTIFICATION, "Hi, mate");

        this.notificationService.sendAsync(user, notification);

        return "redirect:/";
    }
}