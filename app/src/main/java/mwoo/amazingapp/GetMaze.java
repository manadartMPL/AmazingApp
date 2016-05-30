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

    /**
     * Holds the characters that depict the maze.
     */
    private static char maze[][];

    /**
     * Holds the total number of rows in the maze.
     */
    private static int nRows;

    /**
     * Holds the total number of columns in the maze.
     */
    private static int nCols;

    /**
     * Gets the maze name from a spinner in choosemaze class and reads in the file to a double character array.
     * @param context Used so that the method can get the file from the assets folder.
     * @param fileName the name of the file that holds the maze.
     * @return the characters that depict the maze.
     * @throws FileNotFoundException In case the file is not in the app.
     */
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
     * @return the number of rows in the maze.
     */
    public static int getRows(){
        return nRows;
    }

    /**
     * Allows other classes to get the number of columns of the maze.
     * @return the number of columns in the maze.
     */
    public static int getCols(){
        return nCols;
    }

    /**
     * Allows other classes to get the characters that depict the maze.
     * @return the characters that depict the maze.
     */
    public static char[][] getMazeLayout(){return maze;}
}
