package com.game.tictactoe.areas.language.annotations;

import com.game.tictactoe.areas.language.enums.LanguageLocaleType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LocalLang {

    LanguageLocaleType langType();
}
