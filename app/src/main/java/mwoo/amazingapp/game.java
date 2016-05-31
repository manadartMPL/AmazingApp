/*package mwoo.amazingapp;

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
*/