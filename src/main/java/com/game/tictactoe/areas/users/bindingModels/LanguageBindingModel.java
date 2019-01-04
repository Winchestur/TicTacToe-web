package com.game.tictactoe.areas.users.bindingModels;

import com.cyecize.summer.areas.validation.annotations.ConvertedBy;
import com.cyecize.summer.areas.validation.constraints.NotNull;
import com.game.tictactoe.areas.language.dataAdapters.IdToLanguageConverter;
import com.game.tictactoe.areas.language.entities.Language;

public class LanguageBindingModel {

    @ConvertedBy(IdToLanguageConverter.class)
    @NotNull(message = "fieldCannotBeEmpty")
    private Language language;

    public LanguageBindingModel() {

    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
