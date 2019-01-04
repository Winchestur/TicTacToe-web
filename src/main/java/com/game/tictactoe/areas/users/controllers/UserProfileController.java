package com.game.tictactoe.areas.users.controllers;

import com.cyecize.summer.areas.security.annotations.PreAuthorize;
import com.cyecize.summer.common.annotations.Controller;
import com.cyecize.summer.common.annotations.routing.GetMapping;
import com.cyecize.summer.common.models.ModelAndView;
import com.game.tictactoe.areas.language.services.LanguageService;
import com.game.tictactoe.controllers.BaseController;

import static com.cyecize.summer.areas.security.enums.AuthorizationType.*;

@Controller
@PreAuthorize(LOGGED_IN)
public class UserProfileController extends BaseController {

    private final LanguageService languageService;

    public UserProfileController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping("/user/profile")
    public ModelAndView userPanelAction() {
        return super.view("users/panel.twig");
    }

    @GetMapping("/user/language/edit")
    public ModelAndView changeLanguageGetAction() {
        return super.view("users/language/change-language.twig", "languages", this.languageService.findAll());
    }

}
