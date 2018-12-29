package com.game.tictactoe.repositories;

import com.game.tictactoe.repositories.utils.ActionInvoker;
import com.game.tictactoe.repositories.utils.RepositoryActionResult;
import com.game.tictactoe.repositories.utils.RepositoryActionResultImpl;

import javax.persistence.*;

public abstract class BaseRepository {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("tictactoe");

    protected EntityManager entityManager;

    protected BaseRepository() {
        this.entityManager = emf.createEntityManager();
    }

    public void persist(Object entity) {
        if (!entity.getClass().isAnnotationPresent(Entity.class))
            return;
        this.execute((actionResult -> {
            this.entityManager.persist(entity);
            this.entityManager.flush();
        }));
    }

    public void merge(Object entity) {
        if (!entity.getClass().isAnnotationPresent(Entity.class))
            return;
        this.execute(repositoryActionResult -> {
            this.entityManager.merge(entity);
            this.entityManager.flush();
        });
    }

    public void remove(Object entity) {
        if (!entity.getClass().isAnnotationPresent(Entity.class))
            return;
        this.execute((actionResult -> {
            this.entityManager.remove(entity);
            this.entityManager.flush();
        }));
    }

    protected synchronized RepositoryActionResult execute(ActionInvoker invoker) {
        RepositoryActionResult actionResult = new RepositoryActionResultImpl();

        EntityTransaction transaction = this.entityManager.getTransaction();

        transaction.begin();
        try {
            invoker.invoke(actionResult);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw new RuntimeException(ex);
        }

        return actionResult;
    }
}
