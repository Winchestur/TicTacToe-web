package com.game.tictactoe.areas.onlinePlayers.repositories;

import com.cyecize.summer.common.annotations.Service;
import com.game.tictactoe.areas.onlinePlayers.entities.OnlinePlayer;
import com.game.tictactoe.areas.users.entities.User;
import com.game.tictactoe.repositories.BaseRepository;

import java.util.List;

@Service
public class OnlinePlayerRepository extends BaseRepository {

    public void filterOnlinePlayers(long minMillis) {
        super.execute((repositoryActionResult -> {
            List<OnlinePlayer> onlinePlayers = super.entityManager
                    .createQuery("SELECT op FROM OnlinePlayer op WHERE op.lastRefreshDate <:time", OnlinePlayer.class)
                    .setParameter("time", minMillis)
                    .getResultList();

            for (OnlinePlayer onlinePlayer : onlinePlayers) {
                super.entityManager.remove(onlinePlayer);
            }
        }));
    }

    public OnlinePlayer findByUser(User user) {
        return super.execute((repositoryActionResult -> {
            repositoryActionResult.setResult(super.entityManager.createQuery("SELECT op FROM OnlinePlayer op JOIN op.user AS u WHERE u.id =:uid", OnlinePlayer.class)
                    .setParameter("uid", user.getId())
                    .getResultStream().findFirst().orElse(null));
        })).getResult();
    }

    public List<String> findOnlinePlayersNames() {
        return super.execute((repositoryActionResult -> {
            repositoryActionResult.setResult(super.entityManager.createQuery("SELECT u.username FROM OnlinePlayer op JOIN op.user AS u", String.class)
                    .getResultList()
            );
        })).getResult();
    }

    public List<User> findOnlinePlayers() {
        return super.execute((repositoryActionResult -> {
            repositoryActionResult.setResult(super.entityManager.createQuery("SELECT u FROM OnlinePlayer op JOIN op.user AS u", User.class)
                    .getResultList()
            );
        })).getResult();
    }
}
