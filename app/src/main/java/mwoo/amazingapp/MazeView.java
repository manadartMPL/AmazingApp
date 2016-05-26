package mwoo.amazingapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Michael on 5/23/2016.
 */
public class MazeView extends View {
    /**
     * Got this from code online when searching on how build maze. Not sure yet what to do with it. May delete later on.
     * But its supposed to help when drawing the walls of the maze.
     */
    private static final float wallsize = 5;

    /**
     * paint is needed so that app knows how to draw the walls, trails, and characters.
     */
    private Paint paint;

    /**
     * Width of the screen that the view is on
     */
    private int width;

    /**
     * Height of the screen that the view is on
     */
    private int height;

    /**
     * Holds the characters that dictate where the player and their trail is in the maze and the walls.
     */
    private char[][] maze;

    /**
     * Constructor of MazeView
     * @param context not sure right now
     * @param attset Not sure right now
     */
    public MazeView(Context context, AttributeSet attset) {
        super(context, attset);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    /**
     * Gets the measurements of the screen. This method is repeatedly called while MazeView is active
     * @param widthMeasureSpec Not sure right now
     * @param heightMeasureSpec Not sure right now
     */
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec)/GetMaze.getRows();      //gets the height of the screen which the view is on
        width = MeasureSpec.getSize(widthMeasureSpec)/GetMaze.getCols();        //same as above but for width
    }

    /**
     * Draws the maze on the screen and will hopefully draw the game.
     * But need to adjust measurements
     * @param canvas Where the maze is being drawn on.
     */
    @Override
    public void onDraw(Canvas canvas) {
        maze = GetMaze.getMazeLayout();                                             //Gets the chars in the maze to create it
        int mazeRow = GetMaze.getRows();
        int mazeCol = GetMaze.getCols();
        for (int col = 0; col < mazeCol; col++) {
            for (int row = 0; row < mazeRow; row++) {
                if (maze[row][col] == '*') {                                        //If the space in maze has the * character then draw a blue rectangle to depict wall
                    paint.setColor(Color.BLUE);                                     //Set color of rectangle to blue
                    paint.setStyle(Paint.Style.FILL);                               //Fill in rectangle
                    canvas.drawRect(col * width, row * height, (width * (col + 1)), (height * (row + 1)), paint);   //Draw rectangle at the coordinates
                }
            }                                                                   //Format is (left point, top point, right point, bottom point, how to draw it)
        }
    }
}
