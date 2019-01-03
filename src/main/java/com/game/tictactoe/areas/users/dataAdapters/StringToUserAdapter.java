package com.game.tictactoe.areas.users.dataAdapters;

import com.cyecize.solet.HttpSoletRequest;
import com.cyecize.summer.areas.validation.interfaces.DataAdapter;
import com.cyecize.summer.common.annotations.Component;
import com.game.tictactoe.areas.users.entities.User;
import com.game.tictactoe.areas.users.services.UserService;

import java.lang.reflect.Field;

@Component
public class StringToUserAdapter implements DataAdapter<User> {

    private final UserService userService;

    public StringToUserAdapter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User resolveField(Field field, HttpSoletRequest httpSoletRequest) {
        String handle = httpSoletRequest.getBodyParameters().get(field.getName());

        if (handle == null) {
            return null;
        }

        return this.userService.findOneByUsernameOrEmail(handle);
    }
}
