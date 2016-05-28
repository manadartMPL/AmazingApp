package mwoo.amazingapp;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Sam on 5/19/2016. Modified by Michael
 */
public class GetMaze {

    private static char maze[][];
    private static int nRows;
    private static int nCols;

    public static char[][] fromFile(Context context, String fileName) throws FileNotFoundException {
        AssetManager mazeLoader = context.getAssets();
        Scanner scan = null;
        try {
            scan = new Scanner(mazeLoader.open("Mazes/"+fileName+".txt"));
            nRows = scan.nextInt();
            nCols = scan.nextInt();

            maze = new char[nRows+1][nCols+1];
            scan.nextLine(); // Skip eol
            String s;

            while (scan.hasNextLine()) {
                for (int i = 0; i < nRows; ++i) {
                    s = scan.nextLine();
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
        scan.close();
        return maze; // entries;
    }

    /**
     * Allows other classes to get the number of rows of the maze
     * @return the number of rows in the maze
     */
    public static int getRows(){
        return nRows;
    }
    public static int getCols(){
        return nCols;
    }
    public static char[][] getMazeLayout(){return maze;}
}
