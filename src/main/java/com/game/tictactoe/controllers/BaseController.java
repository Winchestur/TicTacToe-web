package com.game.tictactoe.controllers;

import com.cyecize.summer.areas.validation.interfaces.BindingResult;
import com.cyecize.summer.areas.validation.models.FieldError;
import com.cyecize.summer.common.models.ModelAndView;
import com.game.tictactoe.areas.language.services.LocalLanguage;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseController {

    private ModelAndView finalizeView(ModelAndView modelAndView) {
        //add additional logic here like globals for example
        //modelAndView.addObject("dict", this.localLanguage.dictionary());
        return modelAndView;
    }

    protected ModelAndView redirect(String url) {
        return new ModelAndView("redirect:" + url);
    }

    protected ModelAndView view(String viewName) {
        return this.view(viewName, new ModelAndView());
    }

    protected ModelAndView view(String viewName, String modelName, Object model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(modelName, model);
        return this.view(viewName, modelAndView);
    }

    protected ModelAndView view(String viewName, ModelAndView modelAndView) {
        modelAndView.setView(viewName);
        return this.finalizeView(modelAndView);
    }

    protected List<String> translateErrors(BindingResult bindingResult, LocalLanguage localLanguage) {
        List<String> errors = new ArrayList<>();

        for (FieldError error : bindingResult.getErrors()) {
            errors.add(localLanguage.forName(error.getMessage()));
        }

        return errors;
    }
}
