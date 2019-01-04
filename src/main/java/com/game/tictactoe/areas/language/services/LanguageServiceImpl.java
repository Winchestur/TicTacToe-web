package com.game.tictactoe.areas.language.services;

import com.cyecize.summer.common.annotations.PostConstruct;
import com.cyecize.summer.common.annotations.Service;
import com.game.tictactoe.areas.language.entities.Language;
import com.game.tictactoe.areas.language.enums.LanguageLocaleType;
import com.game.tictactoe.areas.language.repositories.LanguageRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository repository;

    public LanguageServiceImpl(LanguageRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void initLangs() {
        if (this.repository.findAll().size() > 0) {
            return;
        }

        for (LanguageLocaleType value : LanguageLocaleType.values()) {
            Language language = new Language();
            language.setLocaleType(value);
            this.repository.persist(language);
        }
    }

    @Override
    public Language findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public Language findByLocaleType(String localeType) {
        LanguageLocaleType locale = Arrays.stream(LanguageLocaleType.values()).filter(e -> e.getName().equalsIgnoreCase(localeType))
                .findFirst().orElse(null);

        if (locale == null) {
            return null;
        }

        return this.repository.findOneByLocaleType(locale);
    }

    @Override
    public Language findByLocaleType(LanguageLocaleType localeType) {
        return this.repository.findOneByLocaleType(localeType);
    }

    @Override
    public List<Language> findAll() {
        return this.repository.findAll();
    }
}
