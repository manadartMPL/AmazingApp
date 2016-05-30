package mwoo.amazingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.io.FileNotFoundException;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mazeView = (MazeView) findViewById(R.id.maze_view);
        Intent intent = getIntent();
        String mazename = intent.getStringExtra(ChooseMaze.mazeName);
        idle = intent.getBooleanExtra(ChooseMaze.player,true);
        try {
            GetMaze.fromFile(this,mazename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Allows mazeView to switch between user playing and app playing it self.
     * @return Whether or not the user choose to go on idle mode
     */
    public boolean getIdle(){return idle;}
}
