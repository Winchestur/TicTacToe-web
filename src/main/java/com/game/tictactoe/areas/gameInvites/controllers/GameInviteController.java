package com.game.tictactoe.areas.gameInvites.controllers;

import com.cyecize.summer.areas.security.annotations.PreAuthorize;
import com.cyecize.summer.areas.validation.annotations.Valid;
import com.cyecize.summer.areas.validation.interfaces.BindingResult;
import com.cyecize.summer.common.annotations.Controller;
import com.cyecize.summer.common.models.JsonResponse;
import com.game.tictactoe.areas.gameInvites.bindingModels.PlayerBindingModel;
import com.game.tictactoe.controllers.BaseController;

import static com.cyecize.summer.areas.security.enums.AuthorizationType.*;

@Controller
@PreAuthorize(LOGGED_IN)
public class GameInviteController extends BaseController {

    public JsonResponse invitePlayer(@Valid PlayerBindingModel bindingModel, BindingResult bindingResult) {
        JsonResponse response = new JsonResponse();


        return response;
    }

}
