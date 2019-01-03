package com.game.tictactoe.areas.users.services;

import com.cyecize.summer.common.annotations.PostConstruct;
import com.cyecize.summer.common.annotations.Service;
import com.game.tictactoe.areas.users.entities.Role;
import com.game.tictactoe.areas.users.enums.RoleType;
import com.game.tictactoe.areas.users.repositories.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @PostConstruct
    public void initRoles() {
        if (this.findAll().size() > 0)
            return;
        for (RoleType value : RoleType.values()) {
            Role role = new Role();
            role.setAuthority(value.name());
            this.roleRepository.createRole(role);
        }
    }

    @Override
    public Role findOneById(Long id) {
        return this.roleRepository.findOneById(id);
    }

    @Override
    public Role findOneByRoleType(String type) {
        return this.roleRepository.findOneByRoleType(type);
    }

    @Override
    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }
}
