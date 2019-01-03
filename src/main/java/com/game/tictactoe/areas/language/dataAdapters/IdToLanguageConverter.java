package com.game.tictactoe.areas.language.dataAdapters;

import com.cyecize.solet.HttpSoletRequest;
import com.cyecize.summer.areas.validation.interfaces.DataAdapter;
import com.cyecize.summer.common.annotations.Component;
import com.game.tictactoe.areas.language.entities.Language;
import com.game.tictactoe.areas.language.services.LanguageService;

import java.lang.reflect.Field;

@Component
public class IdToLanguageConverter implements DataAdapter<Language> {

    private final LanguageService languageService;

    public IdToLanguageConverter(LanguageService languageService) {
        this.languageService = languageService;
    }

    @Override
    public Language resolveField(Field field, HttpSoletRequest httpSoletRequest) {
        if (!httpSoletRequest.getBodyParameters().containsKey(field.getName())) {
            return null;
        }

        try {
            Long id = Long.parseLong(httpSoletRequest.getBodyParameters().get(field.getName()));
            return this.languageService.findById(id);
        } catch (NumberFormatException ignored) {
        }
        return null;
    }
}
