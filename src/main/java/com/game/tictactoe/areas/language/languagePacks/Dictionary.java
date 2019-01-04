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
}
