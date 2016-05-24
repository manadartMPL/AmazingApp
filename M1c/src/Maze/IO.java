package Maze;

import java.util.Scanner;

public class IO {

	
	static char getChar(String inputMsg, char lower, char upper) {

		/**
		 * scan is a scanner instance.
		 */
		Scanner scan = new Scanner(System.in);
		/**
		 * c is a user input that is validated within a range
		 */
		char c;
		do {
			System.out.print("> ");

			/**
			 * s is a user input string.
			 */
			String s = scan.nextLine();
			if (s.isEmpty() || s.length() > 1) {
				System.out.println(inputMsg);
				continue;
			}
			c = s.charAt(0);
			if (c >= lower && c <= upper)
				return c;

			System.out.println(inputMsg);
		} while (true);
	}

	static String getString(String inputMsg) {

		/**
		 * in is a scanner instance.
		 */
		Scanner in = new Scanner(System.in);

		do {
			System.out.print(inputMsg);

			/**
			 * s is the string received by the Scanner instance in.
			 */
			String s = in.nextLine();
			if (!s.isEmpty())
				return s;
			// System.out.println(s);
		} while (true);
	}

	static String getString(String inputMsg, int lower, int upper) {

		/**
		 * in is a scanner instance.
		 */
		Scanner in = new Scanner(System.in);
		do {
			System.out.print(inputMsg);

			/**
			 * s is the string received by the Scanner instance in.
			 */
			// String s = in.nextLine();
			// if (s.isEmpty())
			// System.out.println(inputMsg + " [" + lower + ", " + upper + "]");

			String s = "";
			if (in.hasNext()) {
				if (in.hasNextInt()) {
					s = in.nextLine();
					/**
					 * i is a user input that is validated within a range
					 */
					int i = Integer.parseInt(s);
					if (i >= lower && i <= upper)
						return s;
				} else
					in.nextLine();
			}

			System.out.println(inputMsg + " [" + lower + ", " + upper + "]");
		} while (true);
	}

	static int getInt(String inputMsg, int lower, int upper) {

		/**
		 * in is a scanner instance.
		 */
		Scanner in = new Scanner(System.in);

		/**
		 * i is the validated integer within a given range
		 */
		int i;
		do {
			System.out.print("> ");
			if (in.hasNextInt()) {
				i = in.nextInt();

				if (i >= lower && i <= upper)
					return i;
			} else
				in.nextLine();
			System.out.println(inputMsg);
		} while (true);
	}

	
	
	
	
	
	
	
	
	
	
	
	
}
