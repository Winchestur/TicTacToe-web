package com.game.tictactoe.areas.users.services;

import com.game.tictactoe.areas.users.entities.Role;

import java.util.List;

public interface RoleService {

    void initRoles();

    Role findOneById(Long id);

    Role findOneByRoleType(String type);

    List<Role> findAll();
}
