package com.game.tictactoe.areas.users.repositories;

import com.cyecize.summer.common.annotations.Service;
import com.game.tictactoe.areas.users.entities.Role;
import com.game.tictactoe.repositories.BaseRepository;

import java.util.List;

@Service
public class RoleRepository extends BaseRepository {

    public void createRole(Role role) {
        super.execute((actionResult) -> {
            super.entityManager.persist(role);
            super.entityManager.flush();
        });
    }

    public Role findOneById(Long id) {
        return super.execute((actionResult) -> actionResult.setResult(super.entityManager.createQuery(
                "SELECT r FROM Role r WHERE r.id = :roleId", Role.class
        ).setParameter("roleId", id))).getResult();
    }

    public Role findOneByRoleType(String type) {
        return super.execute((actionResult) -> actionResult.setResult(
                super.entityManager.createQuery("SELECT r FROM Role r WHERE r.authority = :type", Role.class)
                        .setParameter("type", type)
                        .getSingleResult()
        )).getResult();
    }

    public List<Role> findAll() {
        return super.execute(actionResult -> actionResult.setResult(
                this.entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList()
        )).getResult();
    }
}
