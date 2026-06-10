package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private Scanner sc = new Scanner(System.in);
    private MemberManager manager = new MemberManager();
    private final String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public int printMenu() {
        System.out.println();
        System.out.println("==============================================");
        System.out.printf("[Select the task - Current membership : %d]\n", manager.getMemberCount());
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

    public void addMember() {
        String email;

        while (true) {
            System.out.print("Please enter your email : ");
            email = sc.nextLine().trim();
            System.out.println();

            if (!email.matches(emailRegex)) {
                System.out.println("Invalid email format. \n");
                continue;
            }

            if (manager.isEmailExist(email)) {
                System.out.println("Email already exists. Please enter another email.\n");
            } else {
                break;
            }
        }

        System.out.print("Please enter your name : ");
        String name = sc.nextLine().trim();
        System.out.println();

        System.out.print("Please enter your phone number : ");
        String phone = sc.nextLine().trim();
        System.out.println();

        manager.addMember(new Member(name, email, phone));

        System.out.println("Your membership has been successfully added");
    }

    public void selectEmail() {
        System.out.print("Please enter select email : ");
        String email = sc.nextLine().trim();

        Member member = manager.findByEmail(email);

        if (member != null) {
            System.out.println("\n" + member.toString());
        } else {
            System.out.println("Invalid email format. \n");
        }
    }

    public void selectName() {
        System.out.print("Please enter your name : ");
        String name = sc.nextLine().trim();

        ArrayList<Member> members = manager.findByName(name);

        if (!members.isEmpty()) {
            System.out.println();
            for (Member member : members) {
                System.out.println(member.toString());
            }
        } else {
            System.out.println("\nData not found");
        }
    }

    public void selectAll() {
        ArrayList<Member> members = manager.getAllMembers();

        if (!members.isEmpty()) {
            for (Member member : members) {
                System.out.println(member.toString());
            }
        } else {
            System.out.println("Data not found");
        }
    }

    public void updateMember() {
        System.out.print("Please enter update email : ");
        String email = sc.nextLine().trim();

        Member member = manager.findByEmail(email);

        if (member == null) {
            System.out.println("Invalid email format. \n");
        }

        String newEmail;
        while (true) {
            System.out.print("Please enter new email : ");
            newEmail = sc.nextLine().trim();

            if (!manager.isEmailExist(newEmail)) {
                break;
            } else {
                System.out.println("Email already exists. Please enter valid email\n");
            }

            if (!newEmail.matches(emailRegex)) {
                System.out.println("Invalid email format. \n");
                continue;
            }
        }

        System.out.print("Please enter new name : ");
        String newName = sc.nextLine().trim();

        System.out.print("Please enter new phone number : ");
        String newPhone = sc.nextLine().trim();

        member.setEmail(newEmail);
        member.setName(newName);
        member.setPhone(newPhone);

        System.out.println("Your membership has been successfully updated");
    }

    public void deleteMember() {
        System.out.print("Please enter delete email : ");
        String email = sc.nextLine().trim();
        System.out.println();

        if (manager.deleteMember(email)) {
            System.out.println("Your membership has been successfully deleted");
        } else {
            System.out.println("Data not found\n");
        }
    }

    public void start() {
        while (true) {
            int choice = printMenu();

            switch (choice) {
                case 1: addMember(); break;
                case 2: selectEmail(); break;
                case 3: selectName(); break;
                case 4: selectAll(); break;
                case 5: updateMember(); break;
                case 6: deleteMember(); break;
                case 7: System.out.println("thank you"); return;
                default: System.out.println("Enter valid choice");
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }
}