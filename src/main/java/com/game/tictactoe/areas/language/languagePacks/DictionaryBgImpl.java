package com.game.tictactoe.areas.language.languagePacks;

import com.game.tictactoe.areas.language.enums.LanguageLocaleType;

public class DictionaryBgImpl implements Dictionary {

    public static final String HOME = "Начало";
    public static final String LOGIN = "Вход";
    public static final String REGISTER = "Регистрация";
    public static final String LOGOUT = "Изход";
    public static final String USERNAME = "Потр. Име";
    public static final String PASSWORD = "Парола";
    public static final String EMAIL = "Email";
    public static final String PASSWORD_AGAIN = "Парола отново";
    public static final String LANGUAGE = "Език";
    public static final String CHOOSE = "Избери";

    public String choose() {
        return CHOOSE;
    }

    public String language() {
        return LANGUAGE;
    }

    public String username() {
        return USERNAME;
    }

    public String password() {
        return PASSWORD;
    }

    public String email() {
        return EMAIL;
    }

    public String passwordAgain() {
        return PASSWORD_AGAIN;
    }

    public String logout() {
        return LOGOUT;
    }

    public String register() {
        return REGISTER;
    }

    public LanguageLocaleType locale() {
        return LanguageLocaleType.BG;
    }

    public String home() {
        return HOME;
    }

    public String login() {
        return LOGIN;
    }
}
