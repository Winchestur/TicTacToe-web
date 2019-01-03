package com.game.tictactoe.areas.language.entities;

import com.game.tictactoe.areas.language.enums.LanguageLocaleType;

import javax.persistence.*;

@Entity
@Table(name = "languages")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private Long id;

    @Column(name = "locale_type")
    @Enumerated(EnumType.STRING)
    private LanguageLocaleType localeType;

    public Language() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LanguageLocaleType getLocaleType() {
        return localeType;
    }

    public void setLocaleType(LanguageLocaleType localeType) {
        this.localeType = localeType;
    }
}
