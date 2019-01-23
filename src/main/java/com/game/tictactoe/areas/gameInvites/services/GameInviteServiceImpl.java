package com.game.tictactoe.areas.gameInvites.services;

import com.cyecize.summer.common.annotations.Service;
import com.game.tictactoe.areas.gameInvites.entities.GameInvite;
import com.game.tictactoe.areas.gameInvites.exceptions.UserAlreadySentInviteException;
import com.game.tictactoe.areas.gameInvites.repositories.GameInviteRepository;
import com.game.tictactoe.areas.users.entities.User;

import java.util.List;

@Service
public class GameInviteServiceImpl implements GameInviteService{

    private final GameInviteRepository repository;

    public GameInviteServiceImpl(GameInviteRepository repository) {
        this.repository = repository;
    }

    @Override
    public void invitePlayer(User inviter, User otherPlayer) throws UserAlreadySentInviteException {

    }

    @Override
    public boolean cancelInvite(GameInvite invite) {
        return false;
    }

    @Override
    public boolean isInvitePresent(User invitedPlayer, User inviterPlayer) {
        return false;
    }

    @Override
    public GameInvite findSentInvite(User user) {
        return null;
    }

    @Override
    public List<String> findGameInviteUsernames(User user) {
        return null;
    }

    @Override
    public List<GameInvite> findGameInvitations(User user) {
        return null;
    }
}
