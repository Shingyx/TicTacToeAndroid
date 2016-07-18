package com.github.shingyx.tictactoe;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.github.shingyx.tictactoe.game.Game;
import com.github.shingyx.tictactoe.game.GameState;
import com.github.shingyx.tictactoe.game.Player;

public class TicTacToeAdapter extends BaseAdapter {

    private Context context;
    private Game game;
    private String[] values;

    public TicTacToeAdapter(Context context) {
        super();
        this.context = context;
        game = new Game();
        values = new String[9];
        refreshValues();
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public Object getItem(int position) {
        return values[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(parent.getContext());
        textView.setTextSize(80);
        textView.setGravity(Gravity.CENTER);
        textView.setText(values[position]);
        return textView;
    }

    private void refreshValues() {
        Player[][] board = game.getBoard();
        for (int i = 0; i < values.length; i++) {
            values[i] = board[i / 3][i % 3].toString();
        }
    }

    public void update(int position) {
        int row = position / 3;
        int col = position % 3;
        boolean move = game.makeMove(row, col);
        if (!move) {
            Toast.makeText(context, "Invalid move", Toast.LENGTH_LONG).show();
            return;
        }
        GameState state = game.calculateState();
        if (state == GameState.IN_PROGRESS) {
            game.makeAiMove();
        } else {
            Toast.makeText(context, state.toString(), Toast.LENGTH_LONG).show();
        }
        refreshValues();
        notifyDataSetChanged();
    }
}
