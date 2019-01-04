package com.game.tictactoe.areas.language.languagePacks;

import com.game.tictactoe.areas.language.enums.LanguageLocaleType;

public class DictionaryEnImpl implements Dictionary {
    public static final String HOME = "Home";
    public static final String LOGIN = "Login";
    public static final String REGISTER = "Register";
    public static final String LOGOUT = "Logout";
    public static final String USERNAME = "Username";
    public static final String PASSWORD = "Password";
    public static final String EMAIL = "Email";
    public static final String PASSWORD_AGAIN = "Password Again";
    public static final String LANGUAGE = "Language";
    public static final String CHOOSE = "Choose";
    public static final String FIELD_CANNOT_BE_EMPTY = "Field cannot be empty";
    public static final String EDIT = "Edit";
    public static final String SAVE = "Save";
    public static final String CHANGES_WERE_SAVED = "Changes were saved";

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
        return LanguageLocaleType.EN;
    }

    public String home() {
        return HOME;
    }

    public String login() {
        return LOGIN;
    }
}
