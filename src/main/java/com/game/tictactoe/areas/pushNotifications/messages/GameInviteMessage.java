package com.game.tictactoe.areas.pushNotifications.messages;

import com.game.tictactoe.areas.gameInvites.enums.GameInviteState;

public class GameInviteMessage implements PushMessage {

    private final GameInviteState state;

    private final Long inviteId;

    private final String inviterUsername;

    private final String invitedUsername;

    private final Long gameId;

    public GameInviteMessage(GameInviteState state, Long inviteId, String inviterUsername, String invitedUsername) {
        this(state, inviteId, inviterUsername, invitedUsername, null);
    }

    public GameInviteMessage(GameInviteState state, Long inviteId, String inviterUsername, String invitedUsername, Long gameId) {
        this.state = state;
        this.inviteId = inviteId;
        this.inviterUsername = inviterUsername;
        this.invitedUsername = invitedUsername;
        this.gameId = gameId;
    }
}
