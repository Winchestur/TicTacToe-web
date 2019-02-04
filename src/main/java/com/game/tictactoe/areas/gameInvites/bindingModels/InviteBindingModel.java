package com.game.tictactoe.areas.gameInvites.bindingModels;

import com.cyecize.summer.areas.validation.annotations.ConvertedBy;
import com.cyecize.summer.areas.validation.constraints.NotNull;
import com.game.tictactoe.areas.gameInvites.bindingModels.dataAdapters.IdToGameInviteConverter;
import com.game.tictactoe.areas.gameInvites.entities.GameInvite;

public class InviteBindingModel {

    @ConvertedBy(IdToGameInviteConverter.class)
    @NotNull(message = "Game Invite Non Existent")
    private GameInvite invite;

    public InviteBindingModel() {

    }

    public GameInvite getInvite() {
        return invite;
    }

    public void setInvite(GameInvite invite) {
        this.invite = invite;
    }
}
