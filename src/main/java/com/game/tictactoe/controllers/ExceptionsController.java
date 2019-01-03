package com.game.tictactoe.controllers;

import com.cyecize.summer.areas.routing.exceptions.HttpNotFoundException;
import com.cyecize.summer.common.annotations.Controller;
import com.cyecize.summer.common.annotations.routing.ExceptionListener;
import com.cyecize.summer.common.models.ModelAndView;

@Controller
public class ExceptionsController extends BaseController {

    @ExceptionListener(HttpNotFoundException.class)
    public ModelAndView notFoundExceptionAction(HttpNotFoundException ex) {
        return super.view("errors/404.twig", "exception", ex);
    }
}
