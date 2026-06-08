package org.example;

import java.util.*;

public class BingoGame {
    static final int SIZE = 5;        // 판 크기 5x5
    static final int MAX = SIZE * SIZE;        // 숫자 1~25
    static final boolean[] called = new boolean[MAX+1];
    static final Scanner sc = new Scanner(System.in);
    static final Random rand = new Random();

    static int TARGET = 3;

    public static void play() {
        System.out.println("============================= BINGO GAME =============================");
        System.out.println("Complete the bingo by alternately calling numbers with your computer!\n");

        while (true) {
            System.out.print("Enter target bingo lines (3, 4, 5) > ");

            try {
                TARGET = Integer.parseInt(sc.nextLine().trim());
                if (TARGET > 0) break;
                System.out.println("Please enter a number greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a NUMBER.");
            }
        }

        int[][] myBoard = new int[SIZE][SIZE];
        int[][] computerBoard = new int[SIZE][SIZE];

        boolean[][] myMarked = new boolean[SIZE][SIZE];
        boolean[][] computerMarked =  new boolean[SIZE][SIZE];

        makeBoard(myBoard);
        makeBoard(computerBoard);

        System.out.println("Winning condition : " + TARGET + "lines complete first\n");

        int myCount = 0;
        int computerCount = 0;

        while (true) {
            printBoard(myBoard, myMarked);

            // 내 차례
            int myNum = playerPick();
            mark(myBoard, myMarked, myNum);
            mark(computerBoard, computerMarked, myNum);

            // 컴퓨터 차례
            int computerNum = computerPick();
            mark(computerBoard, computerMarked, computerNum);
            mark(myBoard, myMarked, computerNum);

            int newMyCount = countBingo(myMarked);
            int newComputerCount = countBingo(computerMarked);

            if (newMyCount > myCount) {
                for (int i = 0; i < newMyCount-myCount; i++) {
                    System.out.println("\nBINGO!\n");
                }

                myCount = newMyCount;
            }

            if (newComputerCount > computerCount) {
                for (int i = 0; i < newComputerCount-computerCount; i++) {
                    System.out.println("\nCOMPUTER BINGO!\n");
                }

                computerCount = newComputerCount;
            }

            System.out.printf("computerNum : %d\n", computerNum);
            System.out.println("Current My Count : " + myCount);
            System.out.println("Current Computer's Count : " + computerCount);

            if (myCount >= TARGET) {
                System.out.println("\nMY WIN");
                break;
            } else if (computerCount >= TARGET) {
                System.out.println("\nCOMPUTER WIN");
                break;
            }

            System.out.println();
        }
    }

    public static void makeBoard(int[][] board) {
        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= MAX; i++) nums.add(i);
        Collections.shuffle(nums);    // 무작위로 섞기

        int idx = 0;
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                board[r][c] = nums.get(idx++);
    }

    public static void printBoard(int[][] board, boolean[][] marked) {
        System.out.println("======= MY BOARD =======");

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (marked[r][c]) System.out.print("[ X] ");
                else              System.out.printf("[%2d] ", board[r][c]);
            }
            System.out.println();   // 한 줄 끝나면 줄바꿈
        }

        System.out.println();
    }

    public static void mark(int[][] board, boolean[][] marked, int num) {
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                if (board[r][c] == num)
                    marked[r][c] = true;
    }

    public static int countBingo(boolean[][] marked) {
        int count = 0;

        // 가로
        for (int r = 0; r < SIZE; r++) {
            boolean all = true;
            for (int c = 0; c < SIZE; c++) if (!marked[r][c]) all = false;
            if (all) count++;
        }

        // 세로
        for (int c = 0; c < SIZE; c++) {
            boolean all = true;
            for (int r = 0; r < SIZE; r++) if (!marked[r][c]) all = false;
            if (all) count++;
        }

        // 대각선 ＼
        boolean d1 = true;
        for (int i = 0; i < SIZE; i++) if (!marked[i][i]) d1 = false;
        if (d1) count++;

        // 대각선 ／
        boolean d2 = true;
        for (int i = 0; i < SIZE; i++) if (!marked[i][SIZE - 1 - i]) d2 = false;
        if (d2) count++;

        return count;
    }

    public static int playerPick(){
        while (true) {
            System.out.print("Enter Number (1~" + MAX + ") > ");

            int num;

            try {
                num = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please Enter Number");
                continue;
            }

            if (num < 1 || num > MAX)
                System.out.println("Enter between 1 and " + MAX + ".");
            else if (called[num])
                System.out.println("It's already marked");
            else {
                called[num] = true;
                return num;
            }
        }
    }

    public static int computerPick(){
            int num;

            do {
                num = rand.nextInt(MAX) + 1;
            } while (called[num]);

            called[num] = true;

            return num;
    }

}
