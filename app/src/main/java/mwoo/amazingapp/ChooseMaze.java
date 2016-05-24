package mwoo.amazingapp;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ChooseMaze extends AppCompatActivity {
    private Spinner MazeSpinner;
    private Button Submitbtn;
    public final static String mazeName = "mwoo.amazingapp.MAZENAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_maze);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSpinnerItems();
        setButton();
    }

    private void setSpinnerItems(){
        MazeSpinner = (Spinner) findViewById(R.id.MazeSpinner);
        AssetManager assets = getAssets();
        List<String> mazeList = new ArrayList<String>();
        try{
            String [] mazes = assets.list("Mazes");
            for(int loop = 0;loop < mazes.length;loop++){
                mazeList.add(mazes[loop]);
            }
        }catch (IOException io){
            io.printStackTrace();
        }
        ArrayAdapter <String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,mazeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MazeSpinner.setAdapter(dataAdapter);
    }

    private void setButton(){
        Submitbtn = (Button) findViewById(R.id.startMaze);
        MazeSpinner = (Spinner) findViewById(R.id.MazeSpinner);

        Submitbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                clickedChosedMaze();
            }
    });
    }

    private void clickedChosedMaze(){
        Intent intent = new Intent(this,game.class);
        String chosenMaze = String.valueOf(MazeSpinner.getSelectedItem());
        intent.putExtra(mazeName,chosenMaze);
        startActivity(intent);
    }
}
