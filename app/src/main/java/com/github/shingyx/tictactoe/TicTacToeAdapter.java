package com.github.shingyx.tictactoe;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Objects;

public class TicTacToeAdapter extends BaseAdapter {

    private String[] values = new String[9];
    boolean xTurn = true;

    public TicTacToeAdapter() {
        super();
        Arrays.fill(values, "_");
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

    public void update(int position) {
        if (values[position].equals("_")) {
            if (xTurn) {
                values[position] = "X";
            } else {
                values[position] = "O";
            }
            xTurn = !xTurn;
            notifyDataSetChanged();
        }
    }
}
