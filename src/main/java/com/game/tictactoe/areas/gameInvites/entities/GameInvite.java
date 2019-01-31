package com.game.tictactoe.areas.gameInvites.entities;

import com.game.tictactoe.areas.gameInvites.enums.GameInviteState;
import com.game.tictactoe.areas.users.entities.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "game_invites")
public class GameInvite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = "time_requested", nullable = false)
    private Long timeRequested;

    @Column(name = "invite_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private GameInviteState state;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_inviter_id", nullable = false, unique = true, referencedColumnName = "id")
    private User userInviter;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_invited_id", nullable = false, referencedColumnName = "id")
    private User userInvited;

    public GameInvite() {
        this.timeRequested = new Date().getTime();
        this.state = GameInviteState.CREATED;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTimeRequested() {
        return timeRequested;
    }

    public void setTimeRequested(Long timeRequested) {
        this.timeRequested = timeRequested;
    }

    public GameInviteState getState() {
        return state;
    }

    public void setState(GameInviteState state) {
        this.state = state;
    }

    public User getUserInviter() {
        return userInviter;
    }

    public void setUserInviter(User userInviter) {
        this.userInviter = userInviter;
    }

    public User getUserInvited() {
        return userInvited;
    }

    public void setUserInvited(User userInvited) {
        this.userInvited = userInvited;
    }
}