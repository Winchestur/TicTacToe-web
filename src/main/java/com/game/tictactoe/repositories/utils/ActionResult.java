package com.game.tictactoe.repositories.utils;

public interface ActionResult {

    <T> T getResult();

    void setResult(Object result);
}
