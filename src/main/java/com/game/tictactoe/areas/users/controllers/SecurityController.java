package com.game.tictactoe.areas.users.controllers;

import com.cyecize.summer.areas.security.annotations.PreAuthorize;
import com.cyecize.summer.areas.security.enums.AuthorizationType;
import com.cyecize.summer.common.annotations.Controller;
import com.cyecize.summer.common.annotations.routing.GetMapping;
import com.cyecize.summer.common.models.Model;
import com.cyecize.summer.common.models.ModelAndView;
import com.game.tictactoe.areas.language.services.LanguageService;
import com.game.tictactoe.controllers.BaseController;

@Controller
@PreAuthorize(AuthorizationType.ANONYMOUS)
public class SecurityController extends BaseController {

    private final LanguageService languageService;

    public SecurityController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping("/login")
    public ModelAndView loginGetAction() {
        return super.view("security/login.twig");
    }

    @GetMapping("/register")
    public ModelAndView registerGetAction(Model model) {

        model.addAttribute("languages", this.languageService.findALl());
        return super.view("security/register.twig");
    }
}
