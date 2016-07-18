package com.github.shingyx.tictactoe.game;

/**
 * Contains players.
 */
public enum Player {
    NONE,
    X,
    O;

    public Player otherPlayer() {
        Player result;
        switch (this) {
            case X:
                result = O;
                break;
            case O:
                result = X;
                break;
            default:
                result = NONE;
        }
        return result;
    }

    @Override
    public String toString() {
        if (this == NONE) {
            return ".";
        }
        return this.name();
    }
}
