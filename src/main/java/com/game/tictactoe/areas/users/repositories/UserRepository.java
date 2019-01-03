package com.game.tictactoe.areas.users.repositories;

import com.cyecize.summer.common.annotations.Service;
import com.game.tictactoe.areas.users.entities.User;
import com.game.tictactoe.repositories.BaseRepository;

import java.util.List;

@Service
public class UserRepository extends BaseRepository {

    public void createUser(User user) {
        super.execute((actionResult) -> {
            super.entityManager.persist(user);
            super.entityManager.flush();
        });
    }

    public Long countOfUsers() {
        return super.execute((actionResult) -> actionResult.setResult(
                super.entityManager.createQuery("SELECT count (u) FROM User u")
                        .getSingleResult()
        )).getResult();
    }

    public User findOneById(Long id) {
        return super.execute((actionResult) -> actionResult.setResult(
                super.entityManager.createQuery("SELECT u FROM User u WHERE u.id = :uid", User.class)
                        .setParameter("uid", id)
                        .getResultStream().findFirst().orElse(null)
        )).getResult();
    }

    public User findOneByUsername(String username) {
        return super.execute((actionResult) -> actionResult.setResult(
                super.entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                        .setParameter("username", username)
                        .getResultStream().findFirst().orElse(null)
        )).getResult();
    }

    public User findOneByUsernameOrEmail(String handle) {
        return super.execute((actionResult) -> actionResult.setResult(
                super.entityManager.createQuery("SELECT u FROM User u WHERE u.username = :handle OR u.email = :handle", User.class)
                        .setParameter("handle", handle)
                        .getResultStream().findFirst().orElse(null)
        )).getResult();
    }

    public List<User> findAll() {
        return super.execute((actionResult) -> actionResult.setResult(
                super.entityManager.createQuery("SELECT u FROM User u", User.class)
                        .getResultList()
        )).getResult();
    }
}