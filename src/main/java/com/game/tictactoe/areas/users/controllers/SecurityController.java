package com.game.tictactoe.areas.users.controllers;

import com.cyecize.solet.HttpSoletRequest;
import com.cyecize.summer.areas.security.annotations.PreAuthorize;
import com.cyecize.summer.areas.security.models.Principal;
import com.cyecize.summer.areas.validation.annotations.Valid;
import com.cyecize.summer.areas.validation.interfaces.BindingResult;
import com.cyecize.summer.common.annotations.Controller;
import com.cyecize.summer.common.annotations.routing.GetMapping;
import com.cyecize.summer.common.annotations.routing.PostMapping;
import com.cyecize.summer.common.models.Model;
import com.cyecize.summer.common.models.ModelAndView;
import com.cyecize.summer.common.models.RedirectAttributes;
import com.game.tictactoe.areas.language.services.LanguageService;
import com.game.tictactoe.areas.language.services.LocalLanguage;
import com.game.tictactoe.areas.users.bindingModels.UserLoginBindingModel;
import com.game.tictactoe.areas.users.bindingModels.UserRegisterBindingModel;
import com.game.tictactoe.areas.users.entities.User;
import com.game.tictactoe.areas.users.services.UserService;
import com.game.tictactoe.controllers.BaseController;

import static com.cyecize.summer.areas.security.enums.AuthorizationType.*;

@Controller
@PreAuthorize(ANONYMOUS)
public class SecurityController extends BaseController {

    private final LanguageService languageService;

    private final UserService userService;

    private final LocalLanguage localLanguage;

    public SecurityController(LanguageService languageService, UserService userService, LocalLanguage localLanguage) {
        this.languageService = languageService;
        this.userService = userService;
        this.localLanguage = localLanguage;
    }

    @GetMapping("/login")
    public ModelAndView loginGetAction(Model model, HttpSoletRequest request) {
        String referer = "/";
        if (request.getQueryParameters().containsKey("callback"))
            referer = request.getQueryParameters().get("callback");

        if (!model.hasAttribute("model")) {
            UserLoginBindingModel bindingModel = new UserLoginBindingModel();
            bindingModel.setReferrer(referer);
            model.addAttribute("model", bindingModel);
        }

        return super.view("security/login.twig");
    }

    @PostMapping("/login")
    public ModelAndView loginPost(@Valid UserLoginBindingModel bindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {
        redirectAttributes.addAttribute("model", bindingModel);

        if (bindingResult.hasErrors())
            return super.redirect("/login");

        User user = bindingModel.getUsername();
        //TODO put player online

        principal.setUser(user);

        this.localLanguage.updateLanguage(user.getLanguage().getLocaleType());

        return super.redirect(bindingModel.getReferrer() != null ? bindingModel.getReferrer() : "/");
    }

    @GetMapping("/register")
    public ModelAndView registerGetAction() {
        return super.view("security/register.twig", "languages", this.languageService.findALl());
    }

    @PostMapping("/register")
    public ModelAndView registerPostAction(@Valid UserRegisterBindingModel bindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("model", bindingModel);
        if (bindingResult.hasErrors()) {
            return super.redirect("/register");
        }
        this.userService.createUser(bindingModel);
        return super.redirect("/login");
    }
}
