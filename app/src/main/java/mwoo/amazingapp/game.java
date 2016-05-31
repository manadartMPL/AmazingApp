package mwoo.amazingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.io.FileNotFoundException;

/**
 * Activity that is used when going through the maze to show the progress in either mode.
 */
public class game extends AppCompatActivity {
    /**
     * The object where that allows the user to see the maze and play with it.
     */
    private MazeView mazeView;

    /**
     * Holds the user choice to set idle mode on.
     */
    private boolean idle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        String mazename = intent.getStringExtra(ChooseMaze.mazeName);
        toolbar.setTitle(mazename);
        setSupportActionBar(toolbar);
        idle = intent.getBooleanExtra(ChooseMaze.player,true);
        try {
            GetMaze.fromFile(this,mazename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mazeView = (MazeView) findViewById(R.id.maze_view);
        mazeView.setIdle(idle);
    }
}
