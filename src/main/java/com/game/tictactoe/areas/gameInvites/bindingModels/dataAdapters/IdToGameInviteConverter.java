package com.game.tictactoe.areas.gameInvites.bindingModels.dataAdapters;

import com.cyecize.solet.HttpSoletRequest;
import com.cyecize.summer.areas.validation.interfaces.DataAdapter;
import com.cyecize.summer.common.annotations.Component;
import com.game.tictactoe.areas.gameInvites.entities.GameInvite;
import com.game.tictactoe.areas.gameInvites.services.GameInviteService;

import java.lang.reflect.Field;

@Component
public class IdToGameInviteConverter implements DataAdapter<GameInvite> {

    private final GameInviteService gameInviteService;

    public IdToGameInviteConverter(GameInviteService gameInviteService) {
        this.gameInviteService = gameInviteService;
    }

    @Override
    public GameInvite resolveField(Field field, HttpSoletRequest httpSoletRequest) {
        String inviteId = httpSoletRequest.getBodyParameters().get(field.getName());

        if (inviteId != null) {
            try {
                return this.gameInviteService.findById(Long.parseLong(inviteId));
            } catch (NumberFormatException ignored) {
            }
        }

        return null;
    }
}
