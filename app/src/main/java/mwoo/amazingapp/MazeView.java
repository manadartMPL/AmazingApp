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
public class MazeView extends View{
    private static final float wallsize = 5;
    private Paint paint;
    private int width;
    private int height;
    private char[][] maze;

    public MazeView(Context context, AttributeSet attset){
        super(context,attset);

        paint = new Paint();
        paint.setAntiAlias(true);
        int width = getWidth();
        int height = getHeight();
        maze = GetMaze.getMazeLayout();
    }

    @Override
    public void onDraw(Canvas canvas){
        for(int col = 0;col < GetMaze.getCols();col++){
            for(int row = 0; row < GetMaze.getRows();row++){
                if(maze[row][col] == '*'){
                    paint.setColor(Color.BLUE);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawRect(col*width,row*height, width,height,paint);
                }
            }
        }
    }
}
