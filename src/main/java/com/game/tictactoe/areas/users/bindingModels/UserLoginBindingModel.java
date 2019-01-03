package com.game.tictactoe.areas.users.bindingModels;

import com.cyecize.summer.areas.validation.annotations.ConvertedBy;
import com.cyecize.summer.areas.validation.constraints.NotNull;
import com.game.tictactoe.areas.users.constraints.ValidPassword;
import com.game.tictactoe.areas.users.dataAdapters.StringToUserAdapter;
import com.game.tictactoe.areas.users.entities.User;

public class UserLoginBindingModel {

    @ConvertedBy(StringToUserAdapter.class)
    @NotNull
    private User username;

    @ValidPassword
    private String password;

    private String referrer;

    public UserLoginBindingModel() {

    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }
}
