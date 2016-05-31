package mwoo.amazingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

/**
 * The screen that shows when launching the game. User will click on the button to their maze and select which mode to play.
 */
public class MainMenu extends AppCompatActivity {

    /**
     * Image button that moves the user to the chooseMaze activity.
     */
    private ImageButton chooseMazeButton;

    /**
     * Runs when activity is started.
     * @param savedInstanceState saved sate of the application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        chooseMazeButton = (ImageButton) findViewById(R.id.choose_maze);
        chooseMazeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                clickedChoose();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Moves user to maze selection screen
     */
    private void clickedChoose(){
        Intent intent = new Intent(this,ChooseMaze.class);
        startActivity(intent);
    }
}