package com.game.tictactoe.areas.gameInvites.services;

import com.cyecize.summer.common.annotations.PostConstruct;
import com.cyecize.summer.common.annotations.Service;
import com.game.tictactoe.areas.gameInvites.entities.GameInvite;
import com.game.tictactoe.areas.gameInvites.enums.GameInviteState;
import com.game.tictactoe.areas.gameInvites.exceptions.UserAlreadySentInviteException;
import com.game.tictactoe.areas.gameInvites.repositories.GameInviteRepository;
import com.game.tictactoe.areas.pushNotifications.enums.NotificationSeverity;
import com.game.tictactoe.areas.pushNotifications.enums.NotificationType;
import com.game.tictactoe.areas.pushNotifications.messages.GameInviteMessage;
import com.game.tictactoe.areas.pushNotifications.models.PushNotification;
import com.game.tictactoe.areas.pushNotifications.services.NotificationService;
import com.game.tictactoe.areas.users.entities.User;
import com.game.tictactoe.constants.WebConstants;

import javax.swing.*;
import java.util.Date;
import java.util.List;

@Service
public class GameInviteServiceImpl implements GameInviteService {

    private final GameInviteRepository repository;

    private final NotificationService notificationService;

    public GameInviteServiceImpl(GameInviteRepository repository, NotificationService notificationService) {
        this.repository = repository;
        this.notificationService = notificationService;
    }

    @PostConstruct
    public void initTimers() {
        Timer oldInvitesFilterTimer = new Timer(10000, e -> this.filterOldInvites());
        Timer invalidateExpiredInvitesTimer = new Timer(2000, e -> this.invalidateExpiredInvites());
        Timer notificationSenderTimer = new Timer(1000, e -> this.sendNotificationForNewInvites());

        this.startTimers(oldInvitesFilterTimer, invalidateExpiredInvitesTimer, notificationSenderTimer);
    }

    @Override
    public void acceptInvite(GameInvite gameInvite) {
        //TODO
    }

    @Override
    public synchronized void invalidateExpiredInvites() {
        List<GameInvite> expiredInvites = this.repository.findByInviteStateAndTimeLessThan(
                new Date().getTime() - WebConstants.GAME_INVITE_MAX_ACCEPT_TIME_MILLIS,
                GameInviteState.CREATED, GameInviteState.AWAITING
        );
    }

    @Override
    public synchronized void filterOldInvites() {
        this.repository.removeInvitesByTimeAndState(new Date().getTime() - WebConstants.GAME_INVITE_MAX_LIFETIME_MILLIS);
    }

    @Override
    public synchronized void sendNotificationForNewInvites() {
        List<GameInvite> invites = this.repository.findByStates(GameInviteState.CREATED);

        for (GameInvite invite : invites) {
            this.notificationService.sendAsync(invite.getUserInvited(), new PushNotification(NotificationType.GAME_INVITE, this.createInvitePushMessage(invite, GameInviteState.AWAITING)));
            invite.setState(GameInviteState.AWAITING);
            this.repository.merge(invite);
        }
    }

    @Override
    public void invitePlayer(User inviter, User otherPlayer) throws UserAlreadySentInviteException {
        if (this.findSentInvite(inviter) != null) {
            throw new UserAlreadySentInviteException("youHaveAnotherInviteAwaiting");
        }

        GameInvite oppositeGameInvite = this.repository.findByParticipants(otherPlayer, inviter);

        //If the other player has invite, accept it instead of sending new one.
        if (oppositeGameInvite != null) {
            this.acceptInvite(oppositeGameInvite);
            return;
        }

        GameInvite invite = new GameInvite();
        invite.setUserInvited(otherPlayer);
        invite.setUserInviter(inviter);

        this.repository.persist(invite);
    }

    @Override
    public boolean cancelInvite(GameInvite invite) {
        if (invite.getState() == GameInviteState.ACCEPTED || invite.getState() == GameInviteState.DECLINED) {
            return false;
        }

        invite.setState(GameInviteState.DECLINED);
        //TODO notification
        return true;
    }

    @Override
    public boolean isInvitePresentWithParticipants(User invitedPlayer, User inviterPlayer) {
        return this.repository.findByParticipants(inviterPlayer, invitedPlayer) != null;
    }

    @Override
    public GameInvite findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public GameInvite findSentInvite(User user) {
        return this.repository.findSentInviteByUser(user, GameInviteState.CREATED, GameInviteState.AWAITING);
    }

    @Override
    public List<String> findGameInvitingUserNamesForUser(User user) {
        return this.repository.findGameInvitingUserNames(user);
    }

    @Override
    public List<GameInvite> findGameInvitationsForUser(User user) {
        return this.repository.findGameInvitesByUser(user);
    }

    private void startTimers(Timer... timers) {
        for (Timer timer : timers) {
            timer.setRepeats(true);
            timer.start();
        }
    }

    private GameInviteMessage createInvitePushMessage(GameInvite gameInvite, GameInviteState state) {
        return new GameInviteMessage(state, gameInvite.getId(), gameInvite.getUserInviter().getUsername(), gameInvite.getUserInvited().getUsername());
    }

    private GameInviteMessage createInvitePushMessage(GameInvite gameInvite, GameInviteState state, Long gameId) {
        return new GameInviteMessage(state, gameInvite.getId(), gameInvite.getUserInviter().getUsername(), gameInvite.getUserInvited().getUsername(), gameId);
    }
}
