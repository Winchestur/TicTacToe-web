package com.game.tictactoe.repositories.utils;

public class ActionResultImpl implements ActionResult {
    private Object result;

    public ActionResultImpl(){ }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
