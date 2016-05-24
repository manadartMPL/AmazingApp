package mwoo.amazingapp;


import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;

public class Maze{
    private static char maze[][];
    private static int nRows;
    private static int nCols;

    public Maze(Context context,String mazeFile){
        try {
            maze = GetMaze.fromFile(context,mazeFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        nRows = GetMaze.getRows();
        nCols = GetMaze.getCols();
    }
}
