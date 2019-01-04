package com.game.tictactoe.areas.users.controllers;

import com.cyecize.summer.areas.security.annotations.PreAuthorize;
import com.cyecize.summer.areas.security.exceptions.UnauthorizedException;
import com.cyecize.summer.areas.security.models.Principal;
import com.cyecize.summer.areas.validation.annotations.Valid;
import com.cyecize.summer.areas.validation.interfaces.BindingResult;
import com.cyecize.summer.common.annotations.Controller;
import com.cyecize.summer.common.annotations.routing.GetMapping;
import com.cyecize.summer.common.annotations.routing.PostMapping;
import com.cyecize.summer.common.models.ModelAndView;
import com.cyecize.summer.common.models.RedirectAttributes;
import com.game.tictactoe.areas.language.services.LanguageService;
import com.game.tictactoe.areas.language.services.LocalLanguage;
import com.game.tictactoe.areas.users.bindingModels.ChangePasswordBindingModel;
import com.game.tictactoe.areas.users.bindingModels.LanguageBindingModel;
import com.game.tictactoe.areas.users.services.UserService;
import com.game.tictactoe.controllers.BaseController;

import static com.cyecize.summer.areas.security.enums.AuthorizationType.*;

@Controller
@PreAuthorize(LOGGED_IN)
public class UserProfileController extends BaseController {

    private static final String SUCCESS_MSG_PANEL_VAR_NAME = "successMsg";

    private final UserService userService;

    private final LanguageService languageService;

    private final LocalLanguage localLanguage;

    public UserProfileController(UserService userService, LanguageService languageService, LocalLanguage localLanguage) {
        this.userService = userService;
        this.languageService = languageService;
        this.localLanguage = localLanguage;
    }

    @GetMapping("/user/profile")
    public ModelAndView userPanelAction() {
        return super.view("users/panel.twig");
    }

    @GetMapping("/user/language/edit")
    public ModelAndView changeLanguageGetAction() {
        return super.view("users/language/change-language.twig", "languages", this.languageService.findAll());
    }

    @PostMapping("/user/language/edit")
    public ModelAndView changeLanguagePostAction(@Valid LanguageBindingModel bindingModel, BindingResult result, RedirectAttributes redirectAttributes, Principal principal) {
        if (result.hasErrors()) {
            return super.redirect("/user/language/edit");
        }

        this.userService.changeLanguage(this.userService.findOneByUsername(principal.getUser().getUsername()), bindingModel.getLanguage());
        this.localLanguage.updateLanguage(bindingModel.getLanguage().getLocaleType());

        this.addSuccessMessage(this.localLanguage.dictionary().changesWereSaved(), redirectAttributes);

        return super.redirect("/user/profile");
    }

    @GetMapping("/user/password/edit")
    public ModelAndView changePasswordGetAction() {
        return super.view("users/password/change-password.twig");
    }

    @PostMapping("/user/password/edit")
    public ModelAndView changePasswordPostAction(
            @Valid ChangePasswordBindingModel bindingModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Principal principal) throws UnauthorizedException {

        if (bindingResult.hasErrors()) {
            return super.redirect("/user/password/edit");
        }

        if (!bindingModel.getUser().getUsername().equals(principal.getUser().getUsername())) {
            throw new UnauthorizedException("This is not you!");
        }

        this.userService.changePassword(bindingModel.getUser(), bindingModel.getNewPassword());
        this.addSuccessMessage(this.localLanguage.dictionary().changesWereSaved(), redirectAttributes);

        return super.redirect("/user/profile");
    }

    private void addSuccessMessage(String message, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute(SUCCESS_MSG_PANEL_VAR_NAME, message);
    }
}
