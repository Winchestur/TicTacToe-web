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
    public static final String FIELD_CANNOT_BE_EMPTY = "Полето не може да бъде празно";
    public static final String EDIT = "Редакция";
    public static final String SAVE = "Запазване";
    public static final String CHANGES_WERE_SAVED = "Промените са запазени";

    public String changesWereSaved() {
        return CHANGES_WERE_SAVED;
    }

    public String save() {
        return SAVE;
    }

    public String edit() {
        return EDIT;
    }

    public String fieldCannotBeEmpty() {
        return FIELD_CANNOT_BE_EMPTY;
    }

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
