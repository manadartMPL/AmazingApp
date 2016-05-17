package Maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class GetMaze {
	int sum;
	static char maze[][];
	private static int nRows;
	private static int nCols;

	// ***************************************************************************
	public static char[][] fromFile(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		Scanner scan;
		try {
			scan = new Scanner(file);
			nRows = scan.nextInt();
			nCols = scan.nextInt();
			// JOptionPane.showInputDialog(Integer.toString(x) + " " + y);
			//System.out.println(nRows + " " + nCols);

			maze = new char[nRows + 1][nCols + 1];
			scan.nextLine(); // Skip eol
			String s;

			while (scan.hasNextLine()) {
				for (int i = 0; i < nRows; ++i) {
					s = scan.nextLine();
					//System.out.println(s);
					for (int j = 0; j < nCols; ++j) {
						maze[i][j] = s.charAt(j);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("init - File Not Found");
			return null;
		}
		scan.close();
		//print();
		return maze; // entries;
	}

	public static int getRows(){
		return nRows;
	}
	public static int getCols(){
		return nCols;
	}
	// ***************************************************************************
	static void print() {
		for (int i = 0; i < nRows; ++i) {
			for (int j = 0; j < nCols; ++j) {
				System.out.print(maze[i][j]);
			}
			System.out.println("");
		}
		return;
	}

}