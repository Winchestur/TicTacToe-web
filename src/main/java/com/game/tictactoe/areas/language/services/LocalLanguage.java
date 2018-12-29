package com.game.tictactoe.areas.language.services;

import com.game.tictactoe.areas.language.enums.LanguageLocaleType;
import com.game.tictactoe.areas.language.languagePacks.Dictionary;

public interface LocalLanguage {

    void updateLanguage(LanguageLocaleType localeType);

    String locale();

    String forName(String phraseName);

    LanguageLocaleType getLocaleType();

    Dictionary dictionary();
}
