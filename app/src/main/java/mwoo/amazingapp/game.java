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

import java.io.IOException;

public class game extends AppCompatActivity {
    public Maze maze;
    private MazeView mazeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String mazename = intent.getStringExtra(ChooseMaze.mazeName);
        maze = new Maze(this,mazename);
        TextView testtext = (TextView) findViewById(R.id.text);
        char mazedesign [][] =GetMaze.getMazeLayout();
        //testtext.setText("cols: "+GetMaze.getCols() +" rows: "+GetMaze.getRows());
        String mazeLayout = "";
        for(int row = 0;row < GetMaze.getRows(); row++){
            for(int col = 0;col <GetMaze.getCols();col++){
                mazeLayout += mazedesign[row][col];
            }
            mazeLayout += "\n";
        }
        testtext.setText(mazeLayout);

        /*try {
            testtext.setText(String.valueOf(this.getAssets().open("Mazes/"+mazename)));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        mazeView = (MazeView) findViewById(R.id.maze_view);
    }



}
