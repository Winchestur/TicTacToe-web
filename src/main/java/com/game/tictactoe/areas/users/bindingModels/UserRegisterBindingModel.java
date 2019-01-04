package com.game.tictactoe.areas.users.bindingModels;

import com.cyecize.summer.areas.validation.annotations.ConvertedBy;
import com.cyecize.summer.areas.validation.constraints.FieldMatch;
import com.cyecize.summer.areas.validation.constraints.MaxLength;
import com.cyecize.summer.areas.validation.constraints.MinLength;
import com.cyecize.summer.areas.validation.constraints.NotNull;
import com.game.tictactoe.areas.language.dataAdapters.IdToLanguageConverter;
import com.game.tictactoe.areas.language.entities.Language;

public class UserRegisterBindingModel {

    @MinLength(length = 3)
    @NotNull(message = "fieldCannotBeEmpty")
    private String username;

    @NotNull(message = "fieldCannotBeEmpty")
    private String email;

    @MinLength(length = 6, message = "passwordLengthMsg")
    @MaxLength(length = 250)
    private String password;

    @FieldMatch(fieldToMatch = "password", message = "passwordsDoNotMatch")
    private String passwordConfirm;

    @ConvertedBy(IdToLanguageConverter.class)
    @NotNull(message = "fieldCannotBeEmpty")
    private Language language;

    public UserRegisterBindingModel() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
