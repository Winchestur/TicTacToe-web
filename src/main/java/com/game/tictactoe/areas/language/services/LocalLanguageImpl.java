package com.game.tictactoe.areas.language.services;

import com.cyecize.summer.areas.template.annotations.TemplateService;
import com.cyecize.summer.common.annotations.Service;
import com.game.tictactoe.areas.language.enums.LanguageLocaleType;
import com.game.tictactoe.areas.language.languagePacks.Dictionary;
import com.game.tictactoe.areas.language.languagePacks.DictionaryBgImpl;
import com.game.tictactoe.areas.language.languagePacks.DictionaryEnImpl;

@Service
@TemplateService(serviceNameInTemplate = "lang")
public class LocalLanguageImpl implements LocalLanguage {

    private Dictionary dictionary;

    public LocalLanguageImpl() {
        this.updateLanguage(LanguageLocaleType.DEFAULT);
    }

    @Override
    public void updateLanguage(LanguageLocaleType localeType) {

        if (this.dictionary != null && localeType == this.dictionary.locale())
            return;

        switch (localeType) {
            case BG:
                this.dictionary = new DictionaryBgImpl();
                break;
            case EN:
                this.dictionary = new DictionaryEnImpl();
                break;
        }
    }

    @Override
    public String locale() {
        return this.dictionary.locale().name().toLowerCase();
    }

    @Override
    public String forName(String phraseName) {
        try {
            return (String) this.dictionary.getClass().getDeclaredMethod(phraseName).invoke(this.dictionary);
        } catch (Exception ignored) {
        }
        return phraseName;
    }

    @Override
    public LanguageLocaleType getLocaleType() {
        return this.dictionary.locale();
    }

    @Override
    public Dictionary dictionary() {
        return this.dictionary;
    }
}
