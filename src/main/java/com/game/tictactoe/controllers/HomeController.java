package com.game.tictactoe.controllers;

import com.cyecize.summer.common.annotations.Controller;
import com.cyecize.summer.common.annotations.routing.GetMapping;
import com.cyecize.summer.common.models.Model;
import com.cyecize.summer.common.models.ModelAndView;
import com.game.tictactoe.areas.language.services.LocalLanguage;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController extends BaseController{

    private final LocalLanguage localLanguage;

    public HomeController(LocalLanguage localLanguage) {
        this.localLanguage = localLanguage;
    }

    @GetMapping("/")
    public ModelAndView indexAction(Model model) {

        List<String> people = new ArrayList<>();
        people.add("Ivan");
        people.add("Goran");
        people.add("Genadi");
        people.add("register");

        model.addAttribute("hora", people);

        return super.view("default/index.twig");
    }
}
