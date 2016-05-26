package mwoo.amazingapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Michael on 5/23/2016.
 */
public class MazeView extends View {
    /**
     * Decides whether app plays itself or user plays maze.
     */
    private boolean idle;

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

    private int mazeRows;

    private int mazeCols;
    /**
     * Constructor of MazeView
     * @param context not sure right now
     * @param attributeSet Not sure right now
     */
    public MazeView(Context context, AttributeSet attributeSet) {
        super(context);
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
        getMazeSettings();
        for (int col = 0; col < mazeCols; col++) {
            for (int row = 0; row < mazeRows; row++) {
                if (maze[row][col] == '*') {                                        //If the space in maze has the * character then draw a blue rectangle to depict wall
                    paint.setColor(Color.BLUE);                                     //Set color of rectangle to blue
                    paint.setStyle(Paint.Style.FILL);                               //Fill in rectangle
                    canvas.drawRect(col * width, row * height, (width * (col + 1)), (height * (row + 1)), paint);   //Draw rectangle at the coordinates
                }                                                                   //Format is (left point, top point, right point, bottom point, how to draw it)
                else if (maze[row][col] == '#') {
                    paint.setColor(Color.GREEN);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawRect(col * width, row * height, (width * (col + 1)), (height * (row + 1)), paint);
                }
                else if (maze[row][col] == '.') {
                    paint.setColor(Color.RED);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawRect(col * width, row * height, (width * (col + 1)), (height * (row + 1)), paint);
                }
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent){
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            labyrinth(0,0);
            return true;
        }
        return false;
    }

    private void getMazeSettings(){
        maze = GetMaze.getMazeLayout();
        mazeCols = GetMaze.getCols();
        mazeRows = GetMaze.getRows();
    }

    private boolean labyrinth(int x, int y) {
        // Once x & y are at the solution ( base case ) return true.
        if (y == mazeRows - 1 && x == mazeCols - 1) {
            invalidate();
            return true;}
        else {
            try {
                invalidate();
                Thread.sleep(64);}
            catch (InterruptedException e) {}

            if (maze[y][x] == ' ') // May need to perform Recursive calls
            {
                maze[y][x] = '#'; // Provide Path at this point
                // Recursive calls -> Pass base case through multiple recursions
                if (labyrinth(x + 1, y) == true) {
                    invalidate();
                    return true;}
                if (labyrinth(x, y + 1) == true) {return true;}
                if (labyrinth(x - 1, y) == true) {return true;}
                if (labyrinth(x, y - 1) == true) {return true;}
                if (y == mazeRows && x == mazeCols)
                    return true;
                maze[y][x] = '.'; // Denotes backtracking
            }
            return false;
        }
    }
}
