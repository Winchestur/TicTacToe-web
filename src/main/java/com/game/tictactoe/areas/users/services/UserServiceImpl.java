package com.game.tictactoe.areas.users.services;

import com.cyecize.summer.common.annotations.Service;
import com.game.tictactoe.areas.language.entities.Language;
import com.game.tictactoe.areas.users.bindingModels.UserRegisterBindingModel;
import com.game.tictactoe.areas.users.entities.User;
import com.game.tictactoe.areas.users.enums.RoleType;
import com.game.tictactoe.areas.users.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void promote(User user) {
        user.addRole(this.roleService.findOneByRoleType(RoleType.ROLE_ADMIN.name()));
    }

    @Override
    public void createUser(UserRegisterBindingModel bindingModel) {
        User user = this.modelMapper.map(bindingModel, User.class);
        user.addRole(this.roleService.findOneByRoleType(RoleType.ROLE_USER.name()));
        if (this.userRepository.countOfUsers() < 1) {
            user.addRole(this.roleService.findOneByRoleType(RoleType.ROLE_ADMIN.name()));
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        this.userRepository.createUser(user);
    }

    @Override
    public void changeLanguage(User user, Language language) {
        user.setLanguage(language);
        this.userRepository.merge(user);
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        this.userRepository.merge(user);
    }

    @Override
    public User findOneById(Long id) {
        return this.userRepository.findOneById(id);
    }

    @Override
    public User findOneByUsername(String username) {
        return this.userRepository.findOneByUsername(username);
    }

    @Override
    public User findOneByUsernameOrEmail(String handle) {
        return this.userRepository.findOneByUsernameOrEmail(handle);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }
}
