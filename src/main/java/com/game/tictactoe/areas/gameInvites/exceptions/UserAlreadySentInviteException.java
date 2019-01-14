package com.game.tictactoe.areas.gameInvites.exceptions;

public class UserAlreadySentInviteException extends Exception {

    public UserAlreadySentInviteException(String message) {
        super(message);
    }

    public UserAlreadySentInviteException(String message, Throwable cause) {
        super(message, cause);
    }
}
