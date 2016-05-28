package mwoo.amazingapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by Michael on 5/23/2016.
 * The class will draw the maze and will set it to scale based on size of maze and size of screen. The class also hold the recursive function that llets the app play itself.
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

    /**
     * Number of rows in the maze.
     */
    private int mazeRows;

    /**
     * Number of columns in the maze.
     */
    private int mazeCols;

    /**
     * Position of the player in the maze.
     */
    private int[] playerPos = {0, 0};

    /**
     * ScaleDetector is used to get the player's swipes on the screen
     */
    private ScaleGestureDetector scaleDetector;

    /**
     * Holds the last place touched's x coordinate. Used in determining where the player wants to go.
     */
    private float lastTouchedX;

    /**
     * Holds the last place touched's y coordinate. Used in determining where the player wants to go.
     */
    private float lastTouchedY;

    /**
     * Used in getting the player's swipes
     */
    private int ActivePointerId = -1;


    /**
     * Constructor of MazeView
     *
     * @param context      not sure right now
     * @param attributeSet Not sure right now
     */
    public MazeView(Context context, AttributeSet attributeSet) {
        super(context);
        paint = new Paint();
        paint.setAntiAlias(true);
        scaleDetector =  new ScaleGestureDetector(context,new ScaleGestureDetector.SimpleOnScaleGestureListener());
        idle = false;
    }

    public void setIdle(boolean idler){idle = idler;}

    /**
     * Gets the measurements of the screen. This method is repeatedly called while MazeView is active
     *
     * @param widthMeasureSpec  Not sure right now
     * @param heightMeasureSpec Not sure right now
     */
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec) / GetMaze.getRows();      //gets the height of the screen which the view is on
        width = MeasureSpec.getSize(widthMeasureSpec) / GetMaze.getCols();        //same as above but for width
    }

    /**
     * Draws the maze on the screen.
     * Blue denotes walls, Green denotes where the player/app has been, and Red denotes any backtracking the player/app did.
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
                } else if (maze[row][col] == '.') {
                    paint.setColor(Color.RED);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawRect(col * width, row * height, (width * (col + 1)), (height * (row + 1)), paint);
                }
            }
        }
        // (!idle) {
            if (maze[playerPos[0]][playerPos[1]] != '#' && playerPos[0] != 0) {
                paint.setColor(Color.CYAN);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRect(playerPos[1] * width, (playerPos[0] + 1) * height, (width * (playerPos[1] + 1)), (height * (playerPos[0] + 2)), paint);
           //
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
       /* if (idle) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
               //
                // labyrinth(0, 0);
                return true;
            }
            return false;
        }
        else {*/
            scaleDetector.onTouchEvent(motionEvent);
            final int action = MotionEventCompat.getActionMasked(motionEvent);

            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    final int ptrIndex = MotionEventCompat.getActionIndex(motionEvent);
                    final float x = MotionEventCompat.getX(motionEvent, ptrIndex);
                    final float y = MotionEventCompat.getY(motionEvent, ptrIndex);

                    // Remember where we started (for dragging)
                    lastTouchedX = x;
                    lastTouchedY = y;
                    // Save the ID of this pointer (for dragging)
                    ActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                    break;
                }

                case MotionEvent.ACTION_MOVE: {
                    // Find the index of the active pointer and fetch its position
                    final int pointerIndex = MotionEventCompat.findPointerIndex(motionEvent, ActivePointerId);

                    final float x = MotionEventCompat.getX(motionEvent, pointerIndex);
                    final float y = MotionEventCompat.getY(motionEvent, pointerIndex);

                    // Calculate the distance moved
                    final float dx = x - lastTouchedX;
                    final float dy = y - lastTouchedY;

                    playerMove(dx,dy);
                    invalidate();

                    // Remember this touch position for the next move event
                    lastTouchedX = x;
                    lastTouchedY = y;

                    break;
                }

            }
            return false;
        }
    //}

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
                if (labyrinth(x + 1, y) == true) {return true;}
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

    /**
     * The method that is called to make the player move throughout the maze.
     * @param x
     * @param y
     */
    private void playerMove(float x, float y){
        float hori = Math.abs(x);                                               //Gets the absolute value of x to determine which direction the player is going.
        float vert = Math.abs(y);                                               //Same thing as the x

        if(playerPos[0] == mazeRows && playerPos[1] == mazeCols){

        }

        if(hori > vert && x > 0){
            int rightRow;                                                       //Player moving right
            if(playerPos[1] != mazeCols){
            rightRow = playerPos[1]+ 1;}                                        //Get the players column and add 1
            else{return;}

            if(maze[playerPos[0]][rightRow] =='*'){                             //If to the right of player is a wall then return.
                return;
            }
            if(maze[playerPos[0]][rightRow] == ' '){                            //If to the right of the player is empty then
                maze[playerPos[0]][playerPos[1]] = '#';                         // leave mark to show player has been there.
                playerPos[1] = rightRow;                                        // Updates player's position
            }
            else{
                maze[playerPos[0]][playerPos[1]] = '.';                         //If to the right of the player is not a wall or an empty space then leave a mark denoting back tracking
                playerPos[1] = rightRow;                                        //Update player position
            }
        }
        else if(hori > vert && x < 0){
            int leftRow;                                                       //Player moving left
            if(playerPos[1] != 0){
                leftRow = playerPos[1]- 1;}                                        //Get the players column and subtract 1 if not at 0 already
            else{return;}

            if(maze[playerPos[0]][leftRow] =='*'){                             //If to the left of player is a wall then return.
                return;
            }
            if(maze[playerPos[0]][leftRow] == ' '){                            //If to the left of the player is empty then
                maze[playerPos[0]][playerPos[1]] = '#';                         // leave mark to show player has been there.
                playerPos[1] = leftRow;                                        // Updates player's position
            }
            else{
                maze[playerPos[0]][playerPos[1]] = '.';                         //If to the left of the player is not a wall or an empty space then leave a mark denoting back tracking
                playerPos[1] = leftRow;                                        //Update player position
            }
        }
        else if(hori < vert && y > 0){
            int upCol;                                                       //Player moving up
            if(playerPos[0] != 0){
                upCol = playerPos[0]- 1;}                                        //Get the players column and subtract 1 if not at 0 already
            else{return;}

            if(maze[upCol][playerPos[1]] =='*'){                             //If above the player is a wall then return.
                return;
            }
            if(maze[upCol][playerPos[1]] == ' '){                            //If above the player is empty then
                maze[playerPos[0]][playerPos[1]] = '#';                         // leave mark to show player has been there.
                playerPos[0] = upCol;                                        // Updates player's position
            }
            else{
                maze[playerPos[0]][playerPos[1]] = '.';                         //If above the player is not a wall or an empty space then leave a mark denoting back tracking
                playerPos[0] = upCol;                                        //Update player position
            }
        }
        else if(hori < vert && y > 0){
            int upCol;                                                       //Player moving up
            if(playerPos[0] != 0){
                upCol = playerPos[0]- 1;}                                        //Get the players column and subtract 1 if not at 0 already
            else{return;}

            if(maze[upCol][playerPos[1]] =='*'){                             //If above the player is a wall then return.
                return;
            }
            if(maze[upCol][playerPos[1]] == ' '){                            //If above the player is empty then
                maze[playerPos[0]][playerPos[1]] = '#';                         // leave mark to show player has been there.
                playerPos[0] = upCol;                                        // Updates player's position
            }
            else{
                maze[playerPos[0]][playerPos[1]] = '.';                         //If above the player is not a wall or an empty space then leave a mark denoting back tracking
                playerPos[0] = upCol;                                        //Update player position
            }
        }
        invalidate();
    }
}