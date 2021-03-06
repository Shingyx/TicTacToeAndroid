package com.github.shingyx.tictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {
    private TicTacToeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView gridView = (GridView) findViewById(R.id.boardView);
        if (gridView != null) {
            adapter = new TicTacToeAdapter(this);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener((parent, view, position, id) ->
                    adapter.update(position));
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btnNewGame) {
            adapter.newGame();
        }
    }
}
