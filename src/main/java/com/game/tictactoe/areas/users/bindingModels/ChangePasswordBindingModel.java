package com.game.tictactoe.areas.users.bindingModels;

import com.cyecize.summer.areas.validation.annotations.ConvertedBy;
import com.cyecize.summer.areas.validation.constraints.FieldMatch;
import com.cyecize.summer.areas.validation.constraints.MaxLength;
import com.cyecize.summer.areas.validation.constraints.MinLength;
import com.cyecize.summer.areas.validation.constraints.NotNull;
import com.game.tictactoe.areas.users.constraints.ValidPassword;
import com.game.tictactoe.areas.users.dataAdapters.StringToUserAdapter;
import com.game.tictactoe.areas.users.entities.User;

public class ChangePasswordBindingModel {

    @ConvertedBy(StringToUserAdapter.class)
    @NotNull(message = "This should never happen!")
    private User user;

    @NotNull(message = "fieldCannotBeEmpty")
    @ValidPassword(userField = "user", message = "invalidPassword")
    private String oldPassword;

    @NotNull(message = "fieldCannotBeEmpty")
    @MinLength(length = 6, message = "passwordLengthMsg")
    @MaxLength(length = 250)
    private String newPassword;

    @FieldMatch(fieldToMatch = "newPassword", message = "passwordsDoNotMatch")
    private String passwordAgain;

    public ChangePasswordBindingModel() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }
}
