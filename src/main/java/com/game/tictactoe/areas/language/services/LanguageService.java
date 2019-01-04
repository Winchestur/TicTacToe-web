package com.game.tictactoe.areas.language.services;

import com.game.tictactoe.areas.language.entities.Language;
import com.game.tictactoe.areas.language.enums.LanguageLocaleType;

import java.util.List;

public interface LanguageService {

    Language findById(Long id);

    Language findByLocaleType(String localeType);

    Language findByLocaleType(LanguageLocaleType localeType);

    List<Language> findAll();
}
