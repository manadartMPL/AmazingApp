package mwoo.amazingapp;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Sam on 5/19/2016. Modified by Michael
 */
public class GetMaze {
    private static char maze[][];
    private static int nRows;
    private static int nCols;

    public static char[][] fromFile(Context context, String fileName) throws FileNotFoundException {
        AssetManager mazeLoader = context.getAssets();
        BufferedReader scan = null;
        try {
            scan = new BufferedReader(new InputStreamReader(mazeLoader.open("Mazes/"+fileName)));
            nRows = Integer.parseInt(scan.readLine());
            nCols = Integer.parseInt(scan.readLine());

            maze = new char[nRows][nCols];
            //scan.readLine(); // Skip eol
            String s;

            while (scan.readLine() != null) {
                for (int i = 0; i < nRows; ++i) {
                    s = scan.readLine();
                    for (int j = 0; j < nCols; ++j) {
                        maze[i][j] = s.charAt(j);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("init - File Not Found");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException err){
            System.out.println("null found");
            err.printStackTrace();
        }
        try {
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maze; // entries;
    }

    public static int getRows(){
        return nRows;
    }
    public static int getCols(){
        return nCols;
    }
    public static char[][] getMazeLayout(){return maze;}
}
