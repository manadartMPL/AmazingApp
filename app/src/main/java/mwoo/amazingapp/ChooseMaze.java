package mwoo.amazingapp;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ChooseMaze extends AppCompatActivity {

    /**
     * Spinner that holds the maze names.
     */
    private Spinner MazeSpinner;

    /**
     * Button that lets the user play the maze.
     */
    private Button Submitbtn;

    /**
     * The button that allows the user to go into idle mode.
     */
    private Button IdleButton;

    /**
     * Name of the variable so that the game class can get the maze name through intent.
     */
    public final static String mazeName = "mwoo.amazingapp.MAZENAME";

    /**
     * Name of the variable so that the game class can get the boolean variable through intent.
     */
    public final static String player = "mwoo.amazingapp.PLAYER";

    /**
     * Creates what is seen on the screen when the activity is started.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_maze);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSpinnerItems();
        setButton();
        setIdleButton();

    }

    /**
     * Sets the maze names on to a spinner so that the user maze choose from there.
     */
    private void setSpinnerItems(){
        MazeSpinner = (Spinner) findViewById(R.id.MazeSpinner);
        AssetManager assets = getAssets();
        List<String> mazeList = new ArrayList<String>();
        try{
            String [] mazes = assets.list("Mazes");
            for(int loop = 0;loop < mazes.length;loop++){
                String name = mazes[loop].replace(".txt","");
                mazeList.add(name);
            }
        }catch (IOException io){
            io.printStackTrace();
        }
        ArrayAdapter <String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,mazeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MazeSpinner.setAdapter(dataAdapter);
    }

    /**
     * Sets the start maze button to go to the game activity with the user playing.
     */
    private void setButton(){
        Submitbtn = (Button) findViewById(R.id.startMaze);

        Submitbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                clickedChosedMaze(false);
            }
    });
    }

    /**
     * Sets the start maze button to go to the game activity with the app playing.
     */
    private void setIdleButton(){
        IdleButton = (Button) findViewById(R.id.IdleButton);

        IdleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedChosedMaze(true);
            }
        });
    }

    /**
     * Goes to the game activity with the maze name and the decision if the user wants idle mode on.
     * @param idle The choice that user user i playing or the app is.
     */
    private void clickedChosedMaze(boolean idle){
        Intent intent = new Intent(this,game.class);
        String chosenMaze = String.valueOf(MazeSpinner.getSelectedItem());
        intent.putExtra(mazeName,chosenMaze);
        intent.putExtra(player,idle);
        startActivity(intent);
    }
}
