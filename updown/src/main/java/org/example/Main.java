package org.example;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final Random rand = new Random();

    private static int MIN_VALUE = 1;
    private static int MAX_VALUE = 100;

    public static void main(String[] args) {
        while (true) {
            while (true) {
                try {
                    System.out.println("Difficulty level selection: [1] Easy (1-100), [2] Normal (1-300), [3] Difficulty (1-500)");
                    int difficulty = Integer.parseInt(sc.nextLine());

                    switch (difficulty) {
                        case 1:
                            MAX_VALUE = 100;
                            break;
                        case 2:
                            MAX_VALUE = 300;
                            break;
                        case 3:
                            MAX_VALUE = 500;
                            break;
                    }

                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Please enter number");
                }
            }

            int answer = rand.nextInt(MAX_VALUE - MIN_VALUE + 1) + MIN_VALUE;
            int count = 0;
            int guess = 0;

            System.out.printf("\nThe answer is between %d and %d!\n", MIN_VALUE, MAX_VALUE);

            while (true) {
                while (true) {
                    try {
                        System.out.print("Enter number > ");
                        guess = Integer.parseInt(sc.nextLine());

                        if (MIN_VALUE < guess && guess <= MAX_VALUE) {
                            break;
                        }

                        System.out.printf("Please enter a number between %d and %d.\n", MIN_VALUE, MAX_VALUE);
                    } catch (NumberFormatException e) {
                        System.out.printf("Please enter a number between %d and %d.\n", MIN_VALUE, MAX_VALUE);
                    }
                }

                count ++;

                if (guess < answer) {
                    System.out.println("UP!");
                } else if (guess > answer) {
                    System.out.println("DOWN!");
                } else {
                    System.out.println("Correct!");
                    System.out.println("You got " + count + " times.");
                    break;
                }

                if (count == 7) {
                    System.out.println("GAME OVER!");
                    break;
                }
            }

            System.out.print("One more round? (Y/N) > ");

            if (sc.nextLine().equalsIgnoreCase("n")) {
                break;
            }
        }
    }
}