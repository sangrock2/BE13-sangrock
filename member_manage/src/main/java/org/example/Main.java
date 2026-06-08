package org.example;

import java.util.Scanner;

public class Main {
    static int totalCnt = 0;
    static int memberCnt = 0;
    static Scanner sc = new Scanner(System.in);

    public static int printPricePlan() {
        System.out.println("===============================================================");
        System.out.println("[Select pricing plan]");
        System.out.println("[1]Lite : 10 people [2]Basic : 20 people [3]Premium : 30 people");
        System.out.println("===============================================================");
        System.out.println();

        System.out.print("Enter your choice: ");
        int num = sc.nextInt();
        sc.nextLine();
        System.out.println();

        return num;
    }

    public static int printMenu(int memberCnt) {
        System.out.println();
        System.out.println("==============================================");
        System.out.printf("[Select the task - Current membership : %d/%d]\n", memberCnt, totalCnt);
        System.out.println("[1]AddMember [2]selectEmail [3]selectName");
        System.out.println("[4]selectAll [5]updateMember [6]deleteMember");
        System.out.println("[7]EXIT");
        System.out.println("==============================================");
        System.out.println();

        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        System.out.println();

        return choice;
    }

    public static void addMember(String[][] members) {
        if (memberCnt == totalCnt) {
            System.out.println("The member are full\n");
            return;
        }

        String email = "";

        while (true) {
            System.out.print("Please enter your email : ");
            email = sc.nextLine().trim();
            System.out.println();

            if (!checkEmail(members, email)) {
                break;
            } else {
                System.out.println("Please enter valid email");
                System.out.println();
            }
        }

        System.out.print("Please enter your name : ");
        String name = sc.nextLine().trim();
        System.out.println();

        System.out.print("Please enter your phone number : ");
        String phone = sc.nextLine().trim();
        System.out.println();

        members[memberCnt][0] = name;
        members[memberCnt][1] = email;
        members[memberCnt][2] = phone;

        memberCnt++;
        System.out.println("Your membership has been successfully added");
    }

    public static void selectEmail(String[][] members) {
        System.out.print("Please enter select email : ");
        String email = sc.nextLine().trim();
        System.out.println();

        for (int i = 0; i < members.length; i++) {
            if (email.equals(members[i][1])) {
                System.out.println("[Name] " + members[i][0] +
                        ", [Email] " + members[i][1] +
                        ", [Phone] " + members[i][2]);

                return;
            }
        }

        System.out.println("Data not found\n");
    }

    public static void selectName(String[][] members) {
        System.out.println("Please enter select name : ");
        String name = sc.nextLine().trim();
        System.out.println();

        for (int i = 0; i < members.length; i++) {
            if (name.equals(members[i][0])) {
                System.out.println("[Name] " + members[i][0] +
                        ", [Email] " + members[i][1] +
                        ", [Phone] " + members[i][2]);

                return;
            }
        }

        System.out.println("Data not found\n");
    }

    public static boolean checkEmail(String[][] members, String email) {
        for (int i = 0; i < members.length; i++) {
            if (email.equals(members[i][1])) return true; // 이미 있음
        }

        return false;
    }

    public static void selectAll(String[][] members) {
        for (int i = 0; i < memberCnt; i++) {
            System.out.println("[Name] " + members[i][0] +
                    ", [Email] " + members[i][1] +
                    ", [Phone] " + members[i][2]);
        }
    }

    public static void updateMember(String[][] members) {
        System.out.print("Please enter update email : ");
        String email = sc.nextLine().trim();
        System.out.println();

        int idx = -1;

        for (int i = 0; i < members.length; i++) {
            if (email.equals(members[i][1])) { idx = i; break; }
        }

        if (idx == -1) { System.out.println("Data not found\n"); return; }

        while (true) {
            System.out.print("Please enter new email : ");
            email = sc.nextLine().trim();
            System.out.println();

            if (!checkEmail(members, email)) {
                break;
            } else {
                System.out.println("Please enter valid email");
                System.out.println();
            }
        }

        System.out.print("Please enter new name : ");
        String name = sc.nextLine().trim();
        System.out.println();

        System.out.print("Please enter new phone number : ");
        String phone = sc.nextLine().trim();
        System.out.println();

        members[idx][0] = name;
        members[idx][1] = email;
        members[idx][2] = phone;

        System.out.println("Your membership has been successfully updated");
    }

    public static void deleteMember(String[][] members) {
        System.out.print("Please enter delete email : ");
        String email = sc.nextLine().trim();
        System.out.println();

        int idx = -1;

        for (int i = 0; i < members.length; i++) {
            if (email.equals(members[i][1])) { idx = i; break; }
        }

        if (idx == -1) { System.out.println("Data not found\n"); return; }

        memberCnt--;

        for (int i = idx; i < members.length - 1; i++) {
            members[i][0] = members[i + 1][0];
            members[i][1] = members[i + 1][1];
            members[i][2] = members[i + 1][2];
        }

        members[memberCnt][0] = null;
        members[memberCnt][1] = null;
        members[memberCnt][2] = null;
    }

    public static void main(String[] args) {
        int num = printPricePlan();
        String[][] members = new String[num * 10][3];
        totalCnt = num * 10;

        while (true) {
            int choice = printMenu(memberCnt);

            switch (choice) {
                case 1: addMember(members); break;
                case 2: selectEmail(members); break;
                case 3: selectName(members); break;
                case 4: selectAll(members); break;
                case 5: updateMember(members); break;
                case 6: deleteMember(members); break;
                case 7: System.out.println("thank you"); return;
                default: System.out.println("Enter valid choice");
            }
        }
    }
}