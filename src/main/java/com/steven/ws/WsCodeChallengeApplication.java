package com.steven.ws;

import java.util.Scanner;

import com.steven.ws.logic.WsCodeChallengeLogic;

public class WsCodeChallengeApplication {

	/**
	 * Expected inputs are:
	 * 
	 * <pre>
	 * 1. A 5 digit zip code.
	 * 2. A multidimensional array containing zip codes in the following format:
	 * 	- [94133,94133] [94200,94299] [94600,94699]
	 *  - note that arrays are space delimited.
	
	 * Example input from command line: 
	 * java WsCodeChallengeApplication "94199" "[94133,94133] [94200,94299] [94600,94699]"
	 * </pre>
	 * 
	 * @see {@link com.steven.ws.logic.WsCodeChallengeLogic#retrieveMinimumRangeSetFromArray(String)} for further
	 *      logical rules
	 * @param args
	 *            the arguments to check
	 */
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		WsCodeChallengeLogic plainJava = new WsCodeChallengeLogic();

		System.out.println("Welcome to the WsCodeChallengeApplication, type 'exit' at any time to stop application.");
		System.out.println("Please input a list of zip codes in format: [94133,94133] [94200,94299] [94600,94699]");
		String line = scanner.nextLine();
		System.out.printf("User input was: %s%n", line);
		if (line.equalsIgnoreCase("exit")) {
			System.exit(0);
		}
		System.out.println("Resulting Range: " + plainJava.retrieveMinimumRangeSetFromArray(line));

	}

}
