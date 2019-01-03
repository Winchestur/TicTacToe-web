package com.game.tictactoe.areas.users.services;

import com.game.tictactoe.areas.users.bindingModels.UserRegisterBindingModel;
import com.game.tictactoe.areas.users.entities.User;

import java.util.List;

public interface UserService {

    void promote(User user);

    void createUser(UserRegisterBindingModel bindingModel);

    User findOneById(Long id);

    User findOneByUsername(String username);

    User findOneByUsernameOrEmail(String handle);

    List<User> findAll();
}
