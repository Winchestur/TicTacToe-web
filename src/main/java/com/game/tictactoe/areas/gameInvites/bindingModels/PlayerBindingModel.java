package com.game.tictactoe.areas.gameInvites.bindingModels;

import com.cyecize.summer.areas.validation.annotations.ConvertedBy;
import com.cyecize.summer.areas.validation.constraints.NotNull;
import com.game.tictactoe.areas.users.dataAdapters.StringToUserAdapter;
import com.game.tictactoe.areas.users.entities.User;

public class PlayerBindingModel {

    @ConvertedBy(StringToUserAdapter.class)
    @NotNull(message = "playerNonExistent")
    private User player;

    public PlayerBindingModel() {

    }

    public User getPlayer() {
        return this.player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }
}
