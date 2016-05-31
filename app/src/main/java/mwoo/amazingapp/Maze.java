package mwoo.amazingapp;
/**
 * Created by Sam on 5/27/2016.
 * Modified by Michael
 */
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import java.io.FileNotFoundException;

/**
 * Activity that is used when going through the maze to show the progress in either mode.
 */
public class Maze extends Activity {

    /**
     * The object where that allows the user to see the maze and play with it.
     */
    MazeView mazeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mazeView = new MazeView(this);
        setContentView(mazeView);
    }

    class MazeView extends SurfaceView implements Runnable {
        private char[][] maze;
        private int mazeRows;
        private int mazeCols;
        int width;
        int height;
        Context context;
        Thread gameThread = null;
        SurfaceHolder ourHolder;
        volatile boolean playing;
        Canvas canvas;
        Paint paint;
        long fps;        // frame per second
        private long timeThisFrame;
        boolean isMoving = true;
        boolean mazeRunning = false;
        /**
         * Holds the user choice to set idle mode on.
         */
        private boolean idle;
        /**
         * Position of the player in the maze.
         * Index 0 is what row the player is on. Index 1 is the column the player is on.
         */
        private int[] playerPos = {0, 0};
        /**
         * Tells if the game is finished.
         */
        private boolean completed = false;

        /**
         * Holds the last place touched's x coordinate. Used in determining where the player wants to go.
         */
        private float lastTouchedX;

        /**
         * Holds the last place touched's y coordinate. Used in determining where the player wants to go.
         */
        private float lastTouchedY;

        /**
         * Used in getting the player's swipes and them dragging their fingers. Allows for multiple fingers on the screen.
         */
        private int ActivePointerId = -1;

        private boolean confirmexit = false;

        public MazeView(Context context) {
            super(context);
            this.context = context;
            ourHolder = getHolder();
            paint = new Paint();
            playing = true;
            Log.d("Sam", "113 About to initialize");
            initialize();
        }

        private void initialize() {
            Bundle choice = getIntent().getExtras();
            if (choice == null) {
                return;
            }
            String mazeChoice = choice.getString("Maze");
            try {
                Log.d("Sam", "128");
                maze = GetMaze.fromFile(context, mazeChoice);
                Log.d("Sam", "130");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            mazeRows = GetMaze.getRows();
            mazeCols = GetMaze.getCols();
            Log.d("Sam", "138");
            idle = choice.getBoolean("player");
        }

        protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            height = View.MeasureSpec.getSize(heightMeasureSpec) / GetMaze.getRows();      //gets the height of the screen which the view is on
            width = View.MeasureSpec.getSize(widthMeasureSpec) / GetMaze.getCols();        //same as above but for width
        }

        @Override
        public void run() {
            Log.d("Sam", "168 run()" + playing);
            if(idle) {
                while (playing) {
                    Log.d("Sam", "170 playing = " + playing);
                    long startFrameTime = System.currentTimeMillis();
                    if (!mazeRunning) {
                        mazeRunning = true;
                        if (labyrinth(0, 0) == true)
                            Log.d("Sam", "Maze Solved:");
                        else
                            Log.d("Sam", "Maze Not Solved");
                    }

                    // Update the frame
                    update();

                    // Draw the frame
                    draw();

                    timeThisFrame = System.currentTimeMillis() - startFrameTime;
                    if (timeThisFrame > 0) {
                        fps = 1000 / timeThisFrame;
                    }
                }
            }
            else{
                long startFrameTime = System.currentTimeMillis();
                draw();
                // Update the frame
                update();

                // Draw the frame
                draw();
                timeThisFrame = System.currentTimeMillis() - startFrameTime;
                if (timeThisFrame > 0) {
                    fps = 1000 / timeThisFrame;
                }
            }
        }

        public void update() {
            Log.d("Sam", "196 update()");
            if (isMoving) {
//                draw();
                Log.d("Sam", "196 moving()");
            }
        }


        private boolean labyrinth(int x, int y) {
            draw();

            Log.d("Sam", "213 labyrinth");
            // Once x & y are at the solution ( base case ) return true.
            if (y == mazeRows - 1 && x == mazeCols - 1) {
                completed = true;
                playing = false;
                return true;
            } else {
                try {
                    //Thread.sleep(64);
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }

                if (maze[y][x] == ' ') // May need to perform Recursive calls
                {
                    maze[y][x] = '#'; // Provide Path at this point
                    // Recursive calls -> Pass base case through multiple recursions
                    if (labyrinth(x + 1, y) == true) {
                        return true;
                    }
                    if (labyrinth(x, y + 1) == true) {
                        return true;
                    }
                    if (labyrinth(x - 1, y) == true) {
                        return true;
                    }
                    if (labyrinth(x, y - 1) == true) {
                        return true;
                    }
                    if (y == mazeRows && x == mazeCols)
                        return true;
                    maze[y][x] = '.'; // Denotes backtracking
                }
                return false;
            }
        }


        public void draw() {
            Log.d("Sam", "334 draw()");
            if (ourHolder.getSurface().isValid()) {
                canvas = ourHolder.lockCanvas();
                paint.setTextSize(45);
                canvas.drawText("FPS:" + fps, 20, 40, paint);

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
                if (!idle) {
                    paint.setColor(Color.YELLOW);                                                                                                                   //Show player position.
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawRect(playerPos[1] * width, playerPos[0] * height, (width * (playerPos[1] + 1)), (height * (playerPos[0] + 1)), paint);
                    paint.setColor(Color.LTGRAY);
                    if (playerPos[0] != 0) {
                        if (maze[playerPos[0] - 1][playerPos[1]] != '*') {
                            canvas.drawRect(playerPos[1] * width, (playerPos[0] - 1) * height, (width * (playerPos[1] + 1)), (height * (playerPos[0] - 0)), paint); //Show that above the player is available.
                        }
                    }
                    if (playerPos[0] != mazeRows - 1) {
                        if (maze[playerPos[0] + 1][playerPos[1]] != '*') {
                            canvas.drawRect(playerPos[1] * width, (playerPos[0] + 1) * height, (width * (playerPos[1] + 1)), (height * (playerPos[0] + 2)), paint); //Show that below the player is available.
                        }
                    }
                    if (playerPos[1] != 0) {
                        if (maze[playerPos[0]][playerPos[1] - 1] != '*') {
                            canvas.drawRect((playerPos[1] - 1) * width, playerPos[0] * height, (width * (playerPos[1] - 0)), (height * (playerPos[0] + 1)), paint);      //Show that to the left of the player is available
                        }
                    }
                    if (playerPos[1] != mazeCols - 1) {
                        if (maze[playerPos[0]][playerPos[1] + 1] != '*') {
                            canvas.drawRect((playerPos[1] + 1) * width, playerPos[0] * height, (width * (playerPos[1] + 2)), (height * (playerPos[0] + 1)), paint);     //Show that to the right of the player is available.
                        }
                    }
                }
                ourHolder.unlockCanvasAndPost(canvas);
            }
        }


        /**
         * The method that is called to make the player move throughout the maze.
         *
         * @param x is movement on the x axis.
         * @param y is movement on the y axis.
         */
        public void playerMove(float x, float y) {
            float hori = Math.abs(x);                                               //Gets the absolute value of x to determine which direction the player is going.
            float vert = Math.abs(y);                                               //Same thing as the x

            if (hori > vert && x > 0) {
                int rightRow;                                                       //Player moving right
                if (playerPos[1] != mazeCols - 1) {
                    rightRow = playerPos[1] + 1;
                }                                        //Get the players column and add 1
                else {
                    return;
                }

                if (maze[playerPos[0]][rightRow] == '*') {                             //If to the right of player is a wall then return.
                    return;
                }
                if (maze[playerPos[0]][rightRow] == ' ') {                            //If to the right of the player is empty then
                    maze[playerPos[0]][playerPos[1]] = '#';                         // leave mark to show player has been there.
                    playerPos[1] = rightRow;                                        // Updates player's position
                } else {
                    maze[playerPos[0]][playerPos[1]] = '.';                         //If to the right of the player is not a wall or an empty space then leave a mark denoting back tracking
                    playerPos[1] = rightRow;                                        //Update player position
                }
            } else if (hori > vert && x < 0) {
                int leftRow;                                                       //Player moving left
                if (playerPos[1] != 0) {
                    leftRow = playerPos[1] - 1;
                }                                        //Get the players column and subtract 1 if not at 0 already
                else {
                    return;
                }

                if (maze[playerPos[0]][leftRow] == '*') {                             //If to the left of player is a wall then return.
                    return;
                }
                if (maze[playerPos[0]][leftRow] == ' ') {                            //If to the left of the player is empty then
                    maze[playerPos[0]][playerPos[1]] = '#';                         // leave mark to show player has been there.
                    playerPos[1] = leftRow;                                        // Updates player's position
                } else {
                    maze[playerPos[0]][playerPos[1]] = '.';                         //If to the left of the player is not a wall or an empty space then leave a mark denoting back tracking
                    playerPos[1] = leftRow;                                        //Update player position
                }
            } else if (hori < vert && y < 0) {
                int upCol;                                                       //Player moving up
                if (playerPos[0] != 0) {
                    upCol = playerPos[0] - 1;
                }                                        //Get the players column and subtract 1 if not at 0 already
                else {
                    return;
                }

                if (maze[upCol][playerPos[1]] == '*') {                             //If above the player is a wall then return.
                    return;
                }
                if (maze[upCol][playerPos[1]] == ' ') {                            //If above the player is empty then
                    maze[playerPos[0]][playerPos[1]] = '#';                         // leave mark to show player has been there.
                    playerPos[0] = upCol;                                        // Updates player's position
                } else {
                    maze[playerPos[0]][playerPos[1]] = '.';                         //If above the player is not a wall or an empty space then leave a mark denoting back tracking
                    playerPos[0] = upCol;                                        //Update player position
                }
            } else if (hori < vert && y > 0) {
                int downCol;                                                       //Player moving down
                if (playerPos[0] != mazeRows - 1) {
                    downCol = playerPos[0] + 1;
                }                                        //Get the players column and add 1 if not at 0 already
                else {
                    return;
                }

                if (maze[downCol][playerPos[1]] == '*') {                             //If below the player is a wall then return.
                    return;
                }
                if (maze[downCol][playerPos[1]] == ' ') {                            //If below the player is empty then
                    maze[playerPos[0]][playerPos[1]] = '#';                         // leave mark to show player has been there.
                    playerPos[0] = downCol;                                        // Updates player's position
                } else {
                    maze[playerPos[0]][playerPos[1]] = '.';                         //If below the player is not a wall or an empty space then leave a mark denoting back tracking
                    playerPos[0] = downCol;                                        //Update player position
                }
            }
            if (playerPos[0] == mazeRows && playerPos[1] == mazeCols) {
                completed = true;
            }
            draw();
        }

        /**
         * When maze goal is reached user may return maze selection screen.
         */
       private void gameEnd() {
            /*AlertDialog.Builder popUp = new AlertDialog.Builder(getContext());
            popUp.setTitle("Maze Completed");
            popUp.setNeutralButton("Return to maze selection.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            popUp.show();*/
           Intent intent = new Intent(getContext(), ChooseMaze.class);
           getContext().startActivity(intent);

        }

        /**
         * App is now paused.
         */
        public void pause() {
            Log.d("Sam", "397 pause()");
            playing = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }
        }

        /**
         * App was paused and now resumes.
         */
        public void resume() {
            Log.d("Sam", "410 resume()");
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }


        /**
         * Gets touchevent and acts according to state the app is in.
         * @param motionEvent the touch event provided by user.
         * @return whether or not the touchevent occurred
         */
        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            Log.d("Sam", "420 onTouchEvent()");
            if (idle) {
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:

                        isMoving = true;

                        break;

                    case MotionEvent.ACTION_UP:

                        isMoving = false;

                        break;
                }
            } else {
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

                        playerMove(dx, dy);

                        // Remember this touch position for the next move event
                        lastTouchedX = x;
                        lastTouchedY = y;

                        break;
                    }

                }
            }
            if(completed){
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        gameEnd();
                }
            }
            return true;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mazeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mazeView.pause();
    }
}
