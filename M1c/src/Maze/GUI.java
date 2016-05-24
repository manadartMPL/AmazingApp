//
//package Maze;
//
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.Font;
//import java.awt.Graphics;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//import javax.swing.JApplet;
//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.SwingUtilities;
//
//public class GUI extends JPanel {
//	int sum;
//	static char maze[][];
//	static char maze2[][];
//	ArrayList[][] caze = new ArrayList[4][4];
//	static int nRows;
//	static int nCols;
//	static final int WIDTH = 70;
//	static final int HEIGHT = 70;
//	static String pathFile = "C:\\java\\in.txt";
//	static boolean started = false;
//
//	/***************************************************************************/
//	boolean labyrinth(int x, int y) throws IllegalMonitorStateException {
//
//		if (y == nRows - 1 && x == nCols - 1) {
//			return true;
//		} else {
//			// print();
//			copyMaze();
//			// revalidate();
//			// validate();
//			repaint();
//			// validate();
//			print2();
//
//			try {
//				// this.wait(); // Causes IllegalMonitorStateException
//				Thread.sleep(10);
//
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//
//			}
//
//			// System.out.println(">");
//			// Scanner scan = new Scanner(System.in);
//			// scan.next();
//			//
//			if (maze[y][x] == ' ') // May need to perform Recursive calls
//			{
//				maze[y][x] = '#'; // Provide Path at this point
//				// Recursive calls -> Pass base case through multiple recursions
//				if (labyrinth(x + 1, y) == true) {
//					return true;
//				}
//				if (labyrinth(x, y + 1) == true) {
//					return true;
//				}
//				if (labyrinth(x - 1, y) == true) {
//					return true;
//				}
//				if (labyrinth(x, y - 1) == true) {
//					return true;
//				}
//				maze[y][x] = '.'; // Denotes backtracking
//			}
//			return false;
//		}
//	}
//
//
//	
//	
//	
//	
//	
//	
//	public void start() {
//	}
//
//	// public void goPaint(){
//	// if (labyrinth(0, 0) == true)
//	// System.out.println("Maze Solved:");
//	// else
//	// System.out.println("Maze Not Solved:");
//	//
//	// }
//
//	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		System.out.println("paintComponent");
//		if (!started) {
//			started = true;
//			if (labyrinth(0, 0) == true)
//				System.out.println("Maze Solved:");
//			else
//				System.out.println("Maze Not Solved:");
//		}
//
//		System.out.println("I'm in PAINT");
//		// g.drawImage( backbuffer, 0, 0, this );
//
//		g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
//		// g.drawString(Integer.toString(sum), 0, 100);
//
//		for (int i = 0; i < nRows; ++i) {
//			for (int j = 0; j < nCols; ++j) {
//				// System.out.print(maze[i][j]);
//
//				g.setColor(Color.BLUE);
//				if (maze2[i][j] == '*')
//					g.fillRect(i * WIDTH, j * HEIGHT, WIDTH, HEIGHT);
//				// g.drawRect(i*WIDTH, j*HEIGHT, WIDTH, HEIGHT);
//
//				if (maze2[i][j] == '#') {
//					g.setColor(Color.RED);
//					g.fillRect(i * WIDTH, j * HEIGHT, WIDTH, HEIGHT);
//				}
//			}
//		}
//	}
//
//	public void paintMaze(Graphics g) {
//		if (!started) {
//			started = true;
//			if (labyrinth(0, 0) == true)
//				System.out.println("Maze Solved:");
//			else
//				System.out.println("Maze Not Solved:");
//		}
//
//		System.out.println("I'm in PAINT");
//		super.paint(g);
//		// g.drawImage( backbuffer, 0, 0, this );
//
//		g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
//		// g.drawString(Integer.toString(sum), 0, 100);
//
//		for (int i = 0; i < nRows; ++i) {
//			for (int j = 0; j < nCols; ++j) {
//				// System.out.print(maze[i][j]);
//
//				g.setColor(Color.BLUE);
//				if (maze2[i][j] == '*')
//					g.fillRect(i * WIDTH, j * HEIGHT, WIDTH, HEIGHT);
//				// g.drawRect(i*WIDTH, j*HEIGHT, WIDTH, HEIGHT);
//
//				if (maze2[i][j] == '#') {
//					g.setColor(Color.RED);
//					g.fillRect(i * WIDTH, j * HEIGHT, WIDTH, HEIGHT);
//				}
//			}
//		}
//	}
//
//	/// ***************************************************************************/
//	static void copyMaze() {
//		for (int i = 0; i < nRows; ++i) {
//			for (int j = 0; j < nCols; ++j) {
//				maze2[i][j] = maze[i][j];
//				// System.out.print(maze[i][j]);
//			}
//			// System.out.println("");
//		}
//		return;
//	}
//
//	/// ***************************************************************************/
//	static void print() {
//		for (int i = 0; i < nRows; ++i) {
//			for (int j = 0; j < nCols; ++j) {
//				System.out.print(maze[i][j]);
//			}
//			System.out.println("");
//		}
//		return;
//	}
//
//	static void print2() {
//		for (int i = 0; i < nRows; ++i) {
//			for (int j = 0; j < nCols; ++j) {
//				System.out.print(maze2[i][j]);
//			}
//			System.out.println("");
//		}
//		return;
//	}
//
//}