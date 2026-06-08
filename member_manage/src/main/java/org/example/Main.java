package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static int printMenu(int memberCnt) {
        System.out.println();
        System.out.println("==============================================");
        System.out.printf("[Select the task - Current membership : %d]\n", memberCnt);
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

    public static void addMember(ArrayList<Member> members) {
        String email = "";

        while (true) {
            System.out.print("Please enter your email : ");
            email = sc.nextLine().trim();
            System.out.println();

            if (!email.matches(emailRegex)) {
                System.out.println("Invalid email format. \n");
                continue;
            }

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

        members.add(new Member(name, email, phone));
        System.out.println("Your membership has been successfully added");
    }

    public static void selectEmail(ArrayList<Member> members) {
        System.out.print("Please enter select email : ");
        String email = sc.nextLine().trim();
        System.out.println();

        for (Member member : members) {
            if (member.getEmail().equals(email)) {
                System.out.println(member.toString());
                System.out.println();
                return;
            }
        }

        System.out.println("Data not found\n");
    }

    public static void selectName(ArrayList<Member> members) {
        System.out.print("Please enter select name : ");
        String name = sc.nextLine().trim();
        System.out.println();

        boolean isFound = false;

        for (Member member : members) {
            if (member.getName().equals(name)) {
                System.out.println(member.toString());
                isFound = true;
            }
        }

        if (!isFound) {
            System.out.println("Data not found\n");
        }
    }

    public static boolean checkEmail(ArrayList<Member> members, String email) {
        for (Member member : members) {
            if (member.getEmail().equals(email)) {
                return true;
            }
        }

        return false;
    }

    public static void selectAll(ArrayList<Member> members) {
        if (!members.isEmpty()) {
            for (Member member : members) {
                System.out.println(member.toString());
            }
        }
    }

    public static void updateMember(ArrayList<Member> members) {
        System.out.print("Please enter update email : ");
        String email = sc.nextLine().trim();
        System.out.println();

        Member targetMember = null;

        for (Member member : members) {
            if (member.getEmail().equals(email)) {
                targetMember = member;
            }
        }

        if (targetMember == null) {
            System.out.println("Data not found\n");
            return;
        }

        String newEmail = "";

        while (true) {
            System.out.print("Please enter new email : ");
            newEmail = sc.nextLine().trim();
            System.out.println();

            if (!newEmail.matches(emailRegex)) {
                System.out.println("Invalid email format. \n");
                continue;
            }

            if (!checkEmail(members, newEmail)) {
                break;
            } else {
                System.out.println("Please enter valid email");
                System.out.println();
            }
        }

        System.out.print("Please enter new name : ");
        String newName = sc.nextLine().trim();
        System.out.println();

        System.out.print("Please enter new phone number : ");
        String newPhone = sc.nextLine().trim();
        System.out.println();

        targetMember.setEmail(newEmail);
        targetMember.setName(newName);
        targetMember.setPhone(newPhone);

        System.out.println("Your membership has been successfully updated");
    }

    public static void deleteMember(ArrayList<Member> members) {
        System.out.print("Please enter delete email : ");
        String email = sc.nextLine().trim();
        System.out.println();

        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getEmail().equals(email)) {
                members.remove(i);
                System.out.println("Your membership has been successfully deleted");
                return;
            }
        }

        System.out.println("Data not found\n");
    }

    public static void main(String[] args) {
        ArrayList<Member> members = new ArrayList<>();

        while (true) {
            int choice = printMenu(members.size());

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