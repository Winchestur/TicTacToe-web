package com.game.tictactoe.areas.gameInvites.repositories;

import com.cyecize.summer.common.annotations.Service;
import com.game.tictactoe.areas.gameInvites.entities.GameInvite;
import com.game.tictactoe.areas.users.entities.User;
import com.game.tictactoe.repositories.BaseRepository;

import java.util.List;

@Service
public class GameInviteRepository extends BaseRepository {

    public void filterInvitesByTime(long minMillis) {
        super.execute((repositoryActionResult -> {
            List<GameInvite> invites = super.entityManager.createQuery("SELECT gi FROM GameInvite gi WHERE gi.timeRequested < :millis", GameInvite.class)
                    .setParameter("millis", minMillis)
                    .getResultList();

            for (GameInvite invite : invites) {
                super.entityManager.remove(invite);
            }
        }));
    }

    public GameInvite findSentInviteByUser(User user) {
        return super.execute((repositoryActionResult -> {
            repositoryActionResult.setResult(
                    super.entityManager.createQuery("SELECT gi FROM GameInvite gi JOIN gi.userInviter AS ui WHERE ui.id = :uid", GameInvite.class)
                            .setParameter("uid", user.getId())
                            .getResultStream().findFirst().orElse(null)
            );
        })).getResult();
    }

    public List<String> findGameInvitingUserNames(User user) {
        return super.execute((repositoryActionResult -> {
            repositoryActionResult.setResult(
                    super.entityManager.createQuery("SELECT inviter.username FROM GameInvite gi JOIN gi.userInvited AS invited JOIN gi.userInviter AS inviter WHERE invited.id = :uid", String.class)
                            .setParameter("uid", user.getId())
                            .getResultList()
            );
        })).getResult();
    }

    public List<GameInvite> findGameInvitesByUser(User user) {
        return super.execute((repositoryActionResult -> {
            repositoryActionResult.setResult(
                    super.entityManager.createQuery("SELECT gi FROM GameInvite gi JOIN gi.userInvited AS uinvited WHERE uinvited.id = :uic", GameInvite.class)
                            .setParameter("uic", user.getId())
                            .getSingleResult()
            );
        })).getResult();
    }
}
