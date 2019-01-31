package com.game.tictactoe.areas.language.languagePacks;

import com.game.tictactoe.areas.language.enums.LanguageLocaleType;

public interface Dictionary {

    LanguageLocaleType locale();

    String home();

    String login();

    String register();

    String logout();

    String username();

    String password();

    String email();

    String passwordAgain();

    String language();

    String choose();

    String fieldCannotBeEmpty();

    String edit();

    String save();

    String changesWereSaved();

    String oldPassword();

    String newPassword();

    String passwordsDoNotMatch();

    String invalidPassword();

    String passwordLengthMsg();

    String invalidValue();

    String usernameTaken();

    String emailTaken();

    String invalidFormat();

    String usernameOrEmailNotFound();

    String onlinePlayers();

    String inviteToPlay();

    String playerNonExistent();

    String cannotInviteYourself();

    String youHaveAnotherInviteAwaiting();

    String inviteHasBeenSentTo();

    String inviteHasBeenDeclined();
}
