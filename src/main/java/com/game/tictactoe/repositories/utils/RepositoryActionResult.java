package com.game.tictactoe.repositories.utils;

public interface RepositoryActionResult {

    <T> T getResult();

    void setResult(Object result);
}
