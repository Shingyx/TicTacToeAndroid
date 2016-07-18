package com.github.shingyx.tictactoe.ai;

import com.github.shingyx.tictactoe.game.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Node for the MiniMax algorithm.
 */
public class Node {
    private Player[][] board;
    private Node nextNode;
    private int value;
    private ArrayList<Node> children;

    public Node(Player[][] board, int value) {
        this.board = board;
        this.value = value;
        this.children = new ArrayList<>();
    }

    public Node(Player[][] board, Node... nodes) {
        this.board = board;
        this.value = 0;
        this.children = new ArrayList<>();
        Collections.addAll(children, nodes);
    }

    public Node(Player[][] board, List<Node> nodes) {
        this.board = board;
        this.value = 0;
        this.children = new ArrayList<>(nodes);
    }

    public Player[][] getNextBoard(Player player) {
        if (player == Player.X) {
            maxValue(Integer.MIN_VALUE, Integer.MAX_VALUE);
        } else if (player == Player.O) {
            minValue(Integer.MIN_VALUE, Integer.MAX_VALUE);
        } else {
            throw new IllegalArgumentException();
        }
        return nextNode.board;
    }

    private int maxValue(int alpha, int beta) {
        if (children.size() == 0) {
            return this.value;
        }
        int result = Integer.MIN_VALUE;
        for (Node node : children) {
            int value = node.minValue(alpha, beta);
            if (value > result) {
                result = value;
                this.nextNode = node;
            }
            if (result >= beta) {
                break;
            }
            alpha = Math.max(alpha, result);
        }
        return result;
    }

    private int minValue(int alpha, int beta) {
        if (children.size() == 0) {
            return this.value;
        }
        int result = Integer.MAX_VALUE;
        for (Node node : children) {
            int value = node.maxValue(alpha, beta);
            if (value < result) {
                result = value;
                this.nextNode = node;
            }
            if (result <= alpha) {
                break;
            }
            beta = Math.min(beta, result);
        }
        return result;
    }

    @Override
    public String toString() {
        if (children.size() == 0) {
            return String.valueOf(this.value);
        } else {
            return children.toString();
        }
    }
}
