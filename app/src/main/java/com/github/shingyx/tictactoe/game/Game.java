package com.github.shingyx.tictactoe.game;


import com.github.shingyx.tictactoe.ai.MiniMaxTree;

/**
 * Manages the game state and handles the game logic. Note X always goes first.
 */
public class Game {

    private Player[][] board;
    private Player turn;

    /**
     * Constructor for the game.
     */
    public Game() {
        board = new Player[3][3];
        newGame();
    }

    /**
     * Get the board of the game in the form of a 2D array.
     *
     * @return The board of the game as a 2D array.
     */
    public Player[][] getBoard() {
        return board;
    }

    /**
     * Get the symbol of the player in the current turn.
     *
     * @return The player's symbol
     */
    public Player getTurn() {
        return turn;
    }

    /**
     * Reset the board for a new game.
     */
    public void newGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = Player.NONE;
            }
        }
        turn = Player.X;
    }

    /**
     * Make a turn given a row and column.
     *
     * @param row Row of target cell.
     * @param col Column of target cell.
     * @return True if the move was successful.
     */
    public boolean makeMove(int row, int col) {
        boolean result = false;

        if (board[row][col] == Player.NONE) {
            board[row][col] = turn;
            if (turn == Player.X) {
                turn = Player.O;
            } else {
                turn = Player.X;
            }
            result = true;
        }
        return result;
    }

    /**
     * Make an AI move.
     */
    public void makeAiMove() {
        MiniMaxTree miniMaxTree = new MiniMaxTree(board, turn);
        board = miniMaxTree.getNextBoard();
        if (turn == Player.X) {
            turn = Player.O;
        } else {
            turn = Player.X;
        }
    }

    /**
     * Get the state of the current game based on the points on the baord.
     *
     * @return Whether the game is in progress, x has won or o has won.
     */
    public GameState calculateState() {
        GameState result = GameState.IN_PROGRESS;
        if (checkWin(Player.X)) {
            result = GameState.X_WIN;
        } else if (checkWin(Player.O)) {
            result = GameState.O_WIN;
        } else if (checkTie()) {
            result = GameState.TIE;
        }
        return result;
    }

    /**
     * Check if the input player has won the game.
     *
     * @param player The character representation of the player.
     * @return True if the input player has won.
     */
    private boolean checkWin(Player player) {
        boolean result = false;
        for (int i = 0; i < 3; i++) {
            // Check the rows
            if (player == board[i][0] && player == board[i][1] && player == board[i][2]) {
                result = true;
                break;
            }
            // Check the columns
            if (player == board[0][i] && player == board[1][i] && player == board[2][i]) {
                result = true;
                break;
            }
        }
        if (!result) {
            // check the diagonals
            if ((player == board[0][0] && player == board[1][1] && player == board[2][2]) ||
                    (player == board[0][2] && player == board[1][1] && player == board[2][0])) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Check if the game is a tie.
     *
     * @return True if the game is a tie.
     */
    private boolean checkTie() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == Player.NONE) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Get the board of the game in the form of a string.
     *
     * @return The board of the game as a string.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < 3; row++) {
            builder.append("[ ");
            for (int col = 0; col < 3; col++) {
                builder.append(board[row][col]);
                builder.append(" ");
            }
            builder.append("]\n");
        }
        return builder.toString().trim();
    }
}
