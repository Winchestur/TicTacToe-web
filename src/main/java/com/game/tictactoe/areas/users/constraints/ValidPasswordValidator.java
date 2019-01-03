package com.game.tictactoe.areas.users.constraints;

import com.cyecize.summer.areas.validation.interfaces.ConstraintValidator;
import com.cyecize.summer.common.annotations.Component;
import com.game.tictactoe.areas.users.bindingModels.UserLoginBindingModel;
import org.mindrot.jbcrypt.BCrypt;

@Component
public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, Object bindingModel) {
        UserLoginBindingModel loginBindingModel = (UserLoginBindingModel) bindingModel;

        if (loginBindingModel.getUsername() == null) {
            return true;
        }

        return password != null && BCrypt.checkpw(password, loginBindingModel.getUsername().getPassword());
    }
}
