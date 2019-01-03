package com.game.tictactoe.areas.language.interceptors;

import com.cyecize.http.HttpCookie;
import com.cyecize.http.HttpCookieImpl;
import com.cyecize.solet.HttpSoletRequest;
import com.cyecize.solet.HttpSoletResponse;
import com.cyecize.summer.areas.routing.models.ActionMethod;
import com.cyecize.summer.common.annotations.Component;
import com.cyecize.summer.common.extensions.InterceptorAdapter;
import com.cyecize.summer.common.models.Model;
import com.game.tictactoe.areas.language.Constants;
import com.game.tictactoe.areas.language.annotations.LocalLang;
import com.game.tictactoe.areas.language.enums.LanguageLocaleType;
import com.game.tictactoe.areas.language.services.LocalLanguage;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component
public class LocaleInterceptor implements InterceptorAdapter {

    private final LocalLanguage localLanguage;

    public LocaleInterceptor(LocalLanguage localLanguage) {
        this.localLanguage = localLanguage;
    }

    @Override
    public boolean preHandle(HttpSoletRequest request, HttpSoletResponse response, Object handler) throws Exception {

        if (!(handler instanceof ActionMethod))
            return true;

        if (request.getQueryParameters().containsKey(Constants.COOKIE_LANG_NAME)) {
            LanguageLocaleType localeType = Arrays.stream(LanguageLocaleType.values())
                    .filter(lt -> lt.name().equalsIgnoreCase(request.getQueryParameters().get(Constants.COOKIE_LANG_NAME)))
                    .findFirst().orElse(LanguageLocaleType.DEFAULT);
            this.localLanguage.updateLanguage(localeType);
            return true;
        }

        Method method = ((ActionMethod) handler).getMethod();
        if (method.isAnnotationPresent(LocalLang.class)) {
            this.localLanguage.updateLanguage(method.getAnnotation(LocalLang.class).langType());
            return true;
        }

        this.localLanguage.updateLanguage(this.extractLangFromCookie(request, response));
        return true;
    }

    @Override
    public void postHandle(HttpSoletRequest request, HttpSoletResponse response, Object handler, Model model) {
        HttpCookie cookie = new HttpCookieImpl(Constants.COOKIE_LANG_NAME, this.localLanguage.locale());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private LanguageLocaleType extractLangFromCookie(HttpSoletRequest request, HttpSoletResponse response) {
        if (request.getCookies() == null) {
            this.initCookie(response);
            return LanguageLocaleType.DEFAULT;
        }

        var cookieEntry = request.getCookies().entrySet().stream().filter((k) -> k.getKey().equals(Constants.COOKIE_LANG_NAME))
                .findFirst().orElse(null);

        if (cookieEntry == null) {
            this.initCookie(response);
            return LanguageLocaleType.DEFAULT;
        }

        HttpCookie cookie = cookieEntry.getValue();

        LanguageLocaleType localeType = Arrays.stream(LanguageLocaleType.values())
                .filter(lt -> lt.name().equals(cookie.getValue().toUpperCase()))
                .findFirst().orElse(null);

        if (localeType == null) {
            this.initCookie(response);
            return LanguageLocaleType.DEFAULT;
        }

        return localeType;
    }

    private void initCookie(HttpSoletResponse response) {
        HttpCookie cookie = new HttpCookieImpl(Constants.COOKIE_LANG_NAME, LanguageLocaleType.DEFAULT.getName());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
