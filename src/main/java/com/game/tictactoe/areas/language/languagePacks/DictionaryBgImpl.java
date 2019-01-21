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
    public static final String OLD_PASSWORD = "Стара Парола";
    public static final String NEW_PASSWORD = "Нова Парола";
    public static final String PASSWORD_LENGTH_MSG = "Паролата трябва да е поне 6 знака";
    public static final String INVALID_PASSWORD = "Грешна парола";
    public static final String PASSWORDS_DO_NOT_MATCH = "Паролите не съвпадат";
    public static final String INVALID_FORMAT = "Невалиден формат";
    public static final String EMAIL_TAKEN = "Email адресът е зает";
    public static final String USERNAME_TAKEN = "Потр. име е заето";
    public static final String INVALID_VALUE = "Невалидна стойност";
    public static final String USERNAME_OR_EMAIL_NOT_FOUND = "Не е открит потребител с това потр. име / Email";
    public static final String ONLINE_USERS = "Потребители на линия";
    public static final String INVITE_TO_PLAY = "Покана За Игра";
    public static final String PLAYER_NON_EXISTENT = "Несъществуващ играч";

    public String playerNonExistent() {
        return PLAYER_NON_EXISTENT;
    }

    public String onlinePlayers() {
        return ONLINE_USERS;
    }

    public String inviteToPlay() {
        return INVITE_TO_PLAY;
    }

    public String usernameOrEmailNotFound() {
        return USERNAME_OR_EMAIL_NOT_FOUND;
    }

    public String invalidValue() {
        return INVALID_VALUE;
    }

    public String usernameTaken() {
        return USERNAME_TAKEN;
    }

    public String emailTaken() {
        return EMAIL_TAKEN;
    }

    public String invalidFormat() {
        return INVALID_FORMAT;
    }

    public String passwordsDoNotMatch() {
        return PASSWORDS_DO_NOT_MATCH;
    }

    public String invalidPassword() {
        return INVALID_PASSWORD;
    }

    public String passwordLengthMsg() {
        return PASSWORD_LENGTH_MSG;
    }

    public String oldPassword() {
        return OLD_PASSWORD;
    }

    public String newPassword() {
        return NEW_PASSWORD;
    }

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
