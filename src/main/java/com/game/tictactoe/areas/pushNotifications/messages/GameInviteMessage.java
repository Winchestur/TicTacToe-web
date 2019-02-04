package com.game.tictactoe.areas.pushNotifications.messages;

import com.game.tictactoe.areas.gameInvites.enums.GameInviteState;

public class GameInviteMessage implements PushMessage {

    private final GameInviteState state;

    private final Long inviteId;

    private final String message;

    private final Long gameId;

    public GameInviteMessage(GameInviteState state, Long inviteId, String message) {
        this(state, inviteId, message, null);
    }

    public GameInviteMessage(GameInviteState state, Long inviteId, String message, Long gameId) {
        this.state = state;
        this.inviteId = inviteId;
        this.message = message;
        this.gameId = gameId;
    }
}
