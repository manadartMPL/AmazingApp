package mwoo.amazingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.io.FileNotFoundException;

public class game extends AppCompatActivity {
    private MazeView mazeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mazeView = (MazeView) findViewById(R.id.maze_view);
        Intent intent = getIntent();
        String mazename = intent.getStringExtra(ChooseMaze.mazeName);
        boolean idle = intent.getBooleanExtra(ChooseMaze.player,false);
        try {
            GetMaze.fromFile(this,mazename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mazeView = new MazeView(this,null);
        mazeView.setIdle(idle);
    }
}
