package com.github.shingyx.tictactoe.ai;

import com.github.shingyx.tictactoe.game.Player;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Generate MiniMax tree for the AI.
 */
public class MiniMaxTree {

    private Player[][] nextBoard;

    public MiniMaxTree(Player[][] currentBoard, Player currentTurn) {
        Node tree = getAllChildren(currentBoard, currentTurn);
        nextBoard = tree.getNextBoard(currentTurn);
    }

    public Player[][] getNextBoard() {
        return nextBoard;
    }

    private Node getAllChildren(Player[][] board, Player turn) {
        if (endOfGame(board)) {
            return new Node(board, computeScore(board, turn));
        }
        List<Node> nodes = new ArrayList<>();
        for (Player[][] nextBoard : getChildren(board, turn)) {
            nodes.add(getAllChildren(nextBoard, turn.otherPlayer()));
        }
        return new Node(board, nodes);
    }

    private Iterable<Player[][]> getChildren(Player[][] board, Player turn) {
        return () -> new Iterator<Player[][]>() {
            int current = 0;

            @Override
            public boolean hasNext() {
                for (int i = current; i < 9; i++) {
                    if (board[i / 3][i % 3] == Player.NONE) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public Player[][] next() {
                for (int i = current; i < 9; i++) {
                    if (board[i / 3][i % 3] == Player.NONE) {
                        current = i + 1;
                        Player[][] temp = new Player[3][];
                        for (int j = 0; j < 3; j++) {
                            temp[j] = board[j].clone();
                        }
                        temp[i / 3][i % 3] = turn;
                        return temp;
                    }
                }
                throw new ArrayIndexOutOfBoundsException();
            }

            @Override
            public void remove() {}
        };
    }

    private boolean endOfGame(Player[][] board) {
        int[][] lines = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };
        for (int[] line : lines) {
            Player pos0 = board[line[0] / 3][line[0] % 3];
            Player pos1 = board[line[1] / 3][line[1] % 3];
            Player pos2 = board[line[2] / 3][line[2] % 3];
            if (pos0 != Player.NONE && pos0 == pos1 && pos0 == pos2) {
                // A player won
                return true;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (board[i / 3][i % 3] == Player.NONE) {
                // Game still in progress
                return false;
            }
        }
        // Tie
        return true;
    }

    private int getScoreForOneLine(Player[] values, Player currentTurn) {
        int countX = 0;
        int countO = 0;
        for (Player value : values) {
            if (value == Player.X) {
                countX++;
            } else if (value == Player.O) {
                countO++;
            }
        }
        //The player who has turn should have more advantage.
        int advantage = 1;
        int result = 0;
        if (countO == 0) {
            if (currentTurn == Player.X) {
                advantage = 3;
            }
            result = (int) (Math.pow(10, countX) * advantage);
        } else if (countX == 0) {
            if (currentTurn == Player.O) {
                advantage = 3;
            }
            result = (int) (-Math.pow(10, countO) * advantage);
        }
        return result;
    }

    private int computeScore(Player[][] board, Player currentTurn) {
        int result = 0;
        int[][] lines = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };
        for (int[] line : lines) {
            Player pos0 = board[line[0] / 3][line[0] % 3];
            Player pos1 = board[line[1] / 3][line[1] % 3];
            Player pos2 = board[line[2] / 3][line[2] % 3];
            Player[] values = {pos0, pos1, pos2};
            result += getScoreForOneLine(values, currentTurn);
        }
        return result;
    }
}
