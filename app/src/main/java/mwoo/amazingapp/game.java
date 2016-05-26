package mwoo.amazingapp;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;

public class game extends AppCompatActivity {
    private MazeView mazeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String mazename = intent.getStringExtra(ChooseMaze.mazeName);
        try {
            GetMaze.fromFile(this,mazename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mazeView = (MazeView) findViewById(R.id.maze_view);
    }
}
