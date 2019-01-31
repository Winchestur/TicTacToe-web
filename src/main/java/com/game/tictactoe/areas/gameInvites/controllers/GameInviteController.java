package com.game.tictactoe.areas.gameInvites.controllers;

import com.cyecize.http.HttpStatus;
import com.cyecize.summer.areas.security.annotations.PreAuthorize;
import com.cyecize.summer.areas.security.models.Principal;
import com.cyecize.summer.areas.validation.annotations.Valid;
import com.cyecize.summer.areas.validation.interfaces.BindingResult;
import com.cyecize.summer.common.annotations.Controller;
import com.cyecize.summer.common.annotations.routing.PostMapping;
import com.cyecize.summer.common.models.JsonResponse;
import com.game.tictactoe.areas.gameInvites.bindingModels.InviteBindingModel;
import com.game.tictactoe.areas.gameInvites.bindingModels.PlayerBindingModel;
import com.game.tictactoe.areas.gameInvites.exceptions.UserAlreadySentInviteException;
import com.game.tictactoe.areas.gameInvites.services.GameInviteService;
import com.game.tictactoe.areas.language.services.LocalLanguage;
import com.game.tictactoe.areas.users.entities.User;
import com.game.tictactoe.controllers.BaseController;

import java.util.ArrayList;

import static com.cyecize.summer.areas.security.enums.AuthorizationType.*;

@Controller
@PreAuthorize(LOGGED_IN)
public class GameInviteController extends BaseController {

    private static final String JSON_RESPONSE_ERRORS_KEY = "errors";

    private final LocalLanguage localLanguage;

    private final GameInviteService gameInviteService;

    public GameInviteController(LocalLanguage localLanguage, GameInviteService gameInviteService) {
        this.localLanguage = localLanguage;
        this.gameInviteService = gameInviteService;
    }

    @PostMapping("/invites/invite")
    public JsonResponse invitePlayer(@Valid PlayerBindingModel bindingModel, BindingResult bindingResult, Principal principal) {
        JsonResponse response = new JsonResponse();

        User playerInviter = (User) principal.getUser();
        User playerToBeInvited = bindingModel.getPlayer();

        if (bindingResult.hasErrors()) {
            response.addAttribute(JSON_RESPONSE_ERRORS_KEY, super.translateErrors(bindingResult, this.localLanguage));
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            return response;
        }

        if (playerInviter.getUsername().equals(playerToBeInvited.getUsername())) {
            response.addAttribute(JSON_RESPONSE_ERRORS_KEY, new ArrayList<String>() {{
                add(localLanguage.dictionary().cannotInviteYourself());
            }});
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response;
        }

        try {
            this.gameInviteService.invitePlayer(playerInviter, playerToBeInvited);
            response.setStatusCode(HttpStatus.CREATED);
        } catch (UserAlreadySentInviteException e) {
            response.addAttribute(JSON_RESPONSE_ERRORS_KEY, new ArrayList<String>() {{
                add(localLanguage.dictionary().youHaveAnotherInviteAwaiting());
            }});
            response.setStatusCode(HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    public JsonResponse declineInvite(Principal principal) {
        JsonResponse response = new JsonResponse();
        User inviter = (User) principal.getUser();

        if (this.gameInviteService.cancelInvite(this.gameInviteService.findSentInvite(inviter))) {
            response.addAttribute("info", "Success");
        } else {
            response.addAttribute("info", "Fail");
        }

        return response;
    }

}
