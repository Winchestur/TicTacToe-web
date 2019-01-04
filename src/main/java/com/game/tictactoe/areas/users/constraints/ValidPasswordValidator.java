package com.game.tictactoe.areas.users.constraints;

import com.cyecize.summer.areas.validation.interfaces.ConstraintValidator;
import com.cyecize.summer.common.annotations.Component;
import com.game.tictactoe.areas.users.entities.User;
import org.mindrot.jbcrypt.BCrypt;

import java.lang.reflect.Field;
import java.util.Arrays;

@Component
public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private String userFieldName;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        this.userFieldName = constraintAnnotation.userField();
    }

    @Override
    public boolean isValid(String password, Object bindingModel) {
        Field userField = Arrays.stream(bindingModel.getClass().getDeclaredFields())
                .filter(f -> f.getName().equals(this.userFieldName))
                .findFirst().orElse(null);

        if (userField == null) {
            return true;
        }

        userField.setAccessible(true);

        User user = null;
        try {
            user = (User) userField.get(bindingModel);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (user == null) {
            return true;
        }

        return password != null && BCrypt.checkpw(password, user.getPassword());
    }
}
