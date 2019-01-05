package com.game.tictactoe.areas.users.constraints;

import com.cyecize.summer.areas.validation.interfaces.ConstraintValidator;
import com.cyecize.summer.common.annotations.Component;
import com.game.tictactoe.areas.users.services.UserService;

@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserService userService;

    public UniqueUsernameValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String usernameOrEmail, Object bindingModel) {
        if (usernameOrEmail == null) {
            return false;
        }

        return this.userService.findOneByUsernameOrEmail(usernameOrEmail) == null;
    }
}
