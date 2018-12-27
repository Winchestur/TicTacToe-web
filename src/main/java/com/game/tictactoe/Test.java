package com.game.tictactoe;

import com.cyecize.summer.common.annotations.Controller;
import com.cyecize.summer.common.annotations.routing.GetMapping;

@Controller
public class Test {

    @GetMapping("/")
    public String test() {
        return "template:home.twig";
    }
}
