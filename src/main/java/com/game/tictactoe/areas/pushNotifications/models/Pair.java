package com.game.tictactoe.areas.pushNotifications.models;

public class Pair<R, L> {

    private R key;

    private L value;

    public Pair(R key, L value) {
        this.key = key;
        this.value = value;
    }

    public L getValue() {
        return value;
    }

    public R getKey() {
        return key;
    }
}