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
    public static final String OLD_PASSWORD = "Old Password";
    public static final String NEW_PASSWORD = "New Password";
    public static final String PASSWORD_LENGTH_MSG = "Password should be at least 6 characters long";
    public static final String INVALID_PASSWORD = "Wrong password";
    public static final String PASSWORDS_DO_NOT_MATCH = "Passwords do not match";
    public static final String INVALID_FORMAT = "Invalid format";
    public static final String EMAIL_TAKEN = "Email taken";
    public static final String USERNAME_TAKEN = "Username taken";
    public static final String INVALID_VALUE = "Invalid value";
    public static final String USERNAME_OR_EMAIL_NOT_FOUND = "User with this username / Email was not found";
    public static final String ONLINE_USERS = "Online Users";
    public static final String INVITE_TO_PLAY = "Invite To Play";
    public static final String PLAYER_NON_EXISTENT = "Player non existent";
    public static final String CANNOT_INVITE_YOURSELF = "Cannot invite yourself";
    public static final String YOU_HAVE_ANOTHER_INVITE_AWAITING = "You have another invite awaiting";
    public static final String INVITE_HAS_BEEN_SENT_TO = "Invite has been sent to ";
    public static final String INVITE_HAS_BEEN_DECLINED = "Invite has been declined";
    public static final String CLICK_TO_ACCEPT = "Click to accept.";
    public static final String CHALLENGES_YOU = " wants to play with you. ";

    public String wantsToPlayWithYou() {
        return CHALLENGES_YOU;
    }

    public String clickToAccept() {
        return CLICK_TO_ACCEPT;
    }

    public String inviteHasBeenDeclined() {
        return INVITE_HAS_BEEN_DECLINED;
    }

    public String inviteHasBeenSentTo() {
        return INVITE_HAS_BEEN_SENT_TO;
    }

    public String youHaveAnotherInviteAwaiting() {
        return YOU_HAVE_ANOTHER_INVITE_AWAITING;
    }

    public String cannotInviteYourself() {
        return CANNOT_INVITE_YOURSELF;
    }

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
        return LanguageLocaleType.EN;
    }

    public String home() {
        return HOME;
    }

    public String login() {
        return LOGIN;
    }
}
