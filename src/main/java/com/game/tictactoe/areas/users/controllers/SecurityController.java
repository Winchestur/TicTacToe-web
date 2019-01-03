package com.game.tictactoe.areas.users.controllers;

import com.cyecize.summer.areas.security.annotations.PreAuthorize;
import com.cyecize.summer.areas.security.enums.AuthorizationType;
import com.cyecize.summer.common.annotations.Controller;
import com.cyecize.summer.common.annotations.routing.GetMapping;
import com.cyecize.summer.common.models.ModelAndView;
import com.game.tictactoe.controllers.BaseController;

@Controller
@PreAuthorize(AuthorizationType.ANONYMOUS)
public class SecurityController extends BaseController {

    @GetMapping("/login")
    public ModelAndView loginGetAction() {
        return super.view("security/login.twig");
    }

    @GetMapping("/register")
    public ModelAndView registerGetAction() {
        return super.view("security/register.twig");
    }
}
