package com.game.tictactoe.areas.onlinePlayers.entities;

import com.game.tictactoe.areas.users.entities.User;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "online_players")
public class OnlinePlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = "last_refresh_date", nullable = false)
    private Long lastRefreshDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    public OnlinePlayer() {
        this.lastRefreshDate = new Date().getTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLastRefreshDate() {
        return lastRefreshDate;
    }

    public void setLastRefreshDate(Long lastRefreshDate) {
        this.lastRefreshDate = lastRefreshDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
