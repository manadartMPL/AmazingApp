package Maze;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Maze extends JFrame {

	int sum;
	static char maze[][];
	static int nRows;
	static int nCols;
	static int width;
	static int height;
	//String pathFile = "Maze8-12.txt";
	//String pathFile = "Maze8-14.txt";
	//String pathFile = "Maze21-79a.txt";		// Error
	String pathFile = "Maze31-28.txt";

	/***************************************************************************/
	public static void main(String[] args) {
		new Maze();
	}

	
	public Maze() {
		initialize();
	}

	private void initialize() {
		try {
			maze = GetMaze.fromFile(pathFile);
			nRows = GetMaze.getRows();
			nCols = GetMaze.getCols();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		width = 900 / nRows;			// Auto size squares
		height	 = 900 / nCols;
		
		
		this.setSize(950, 950);
		this.setTitle("Drawing Shapes");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new DrawStuff(), BorderLayout.CENTER);
		this.setVisible(true);

		if (labyrinth(0, 0) == true)
			System.out.println("Maze Solved:");
		else
			System.out.println("Maze Not Solved:");
	}

	/***************************************************************************/
	// Recursive function for backtracking from deadends
	boolean labyrinth(int x, int y) {
		// Once x & y are at the solution ( base case ) return true.
		if (y == nRows - 1 && x == nCols - 1) {		
			repaint();
			return true;
		} else {
			// print();
			try {
				repaint();
				Thread.sleep(64);
			} catch (InterruptedException e) {
			}

			if (maze[y][x] == ' ') // May need to perform Recursive calls
			{
				maze[y][x] = '#'; // Provide Path at this point
	
				// Recursive calls -> Pass base case through multiple recursions

				
//				if (labyrinth(x - 1, y) == true) {
//					return true;
//				}
//				if (labyrinth(x, y - 1) == true) {
//					return true;
//				}
		
				
				
				
				
				
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
				if (y == nRows && x == nCols)
					return true;
				
				maze[y][x] = '.'; // Denotes backtracking
			}
			return false;
		}
	}

	/***************************************************************************/

	public class DrawStuff extends JComponent {
		public void paint(Graphics g) {
			// super.paint(g);
			Graphics2D graph2 = (Graphics2D) g;
			graph2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			// Shape drawLine = new Line2D.Float(20, 20, 55, 250);
			// graph2.setPaint(Color.BLACK);
			// Rectangle rec = new Rectangle(50,50, 50, 50);
			// g.fillRect(50,50,100,200);
			graph2.setColor(Color.BLUE);
			graph2.fillRect(nRows, nCols, 10, 10);

			// g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
			// g.drawString(Integer.toString(sum), 0, 100);
			
			//System.out.println("Width = " + w + ", Heigth = " + HEIGHT2);
			print();
			
			for (int i = 0; i < nRows; ++i) {
				for (int j = 0; j < nCols; ++j) {
					// System.out.print(maze[i][j]);


					if (maze[i][j] == '*')
					{
						graph2.setColor(Color.BLUE);
						graph2.fillRect(i * width, j * height, width, height);
					}
					
					if (maze[i][j] == '.') {
						graph2.setColor(Color.RED);
						graph2.fillRect(i * width, j * height, width, height);
					}
					
					if (maze[i][j] == '#') {
						graph2.setColor(Color.GREEN);
						graph2.fillRect(i * width, j * height, width, height);
					}
				}
			}

		}
		
		
		
		
		
		public void print() {
			for (int i = 0; i < nRows; ++i) {
				for (int j = 0; j < nCols; ++j) {
					System.out.print(maze[i][j]);
				}
				System.out.println("");
			}
			return;
		}

	} // DrawStuff

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