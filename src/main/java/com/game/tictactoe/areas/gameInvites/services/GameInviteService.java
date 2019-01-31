package com.game.tictactoe.areas.gameInvites.services;

import com.game.tictactoe.areas.gameInvites.entities.GameInvite;
import com.game.tictactoe.areas.gameInvites.exceptions.UserAlreadySentInviteException;
import com.game.tictactoe.areas.users.entities.User;

import java.util.List;

public interface GameInviteService {

    void invitePlayer(User inviter, User otherPlayer) throws UserAlreadySentInviteException;

    boolean cancelInvite(GameInvite invite);

    boolean isInvitePresentWithParticipants(User invitedPlayer, User inviterPlayer);

    GameInvite findById(Long id);

    GameInvite findSentInvite(User user);

    List<String> findGameInvitingUserNamesForUser(User user);

    List<GameInvite> findGameInvitationsForUser(User user);
}