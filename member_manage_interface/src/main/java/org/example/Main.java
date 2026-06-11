package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Select plan: [1]Lite(10) [2]Basic(20) [3]Premium(30)");
        System.out.print("> ");
        int plan = Integer.parseInt(sc.nextLine());

        MemberManager manager = new MemberManager(plan * 10);

        while (true) {
            System.out.println("\n========== Member Management ==========");
            System.out.printf("Current membership: %d / %d\n", manager.getCount(), manager.getCapacity());
            System.out.println("[1]Add [2]Search by Email [3]Search by Name");
            System.out.println("[4]Show All [5]Update [6]Delete [7]Exit");
            System.out.print("Select menu > ");

            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 7) {
                System.out.println("EXIT");
                break;
            }

            switch (choice) {
                case 1:
                    if (manager.isFull()) {
                        System.out.println("Member is FULL");
                        break;
                    }

                    System.out.println("Grade [1]Normal [2]VIP");
                    int grade = Integer.parseInt(sc.nextLine());

                    System.out.print("Name > ");
                    String name  = sc.nextLine();

                    System.out.print("Email > ");
                    String email = sc.nextLine();

                    System.out.print("Phone > ");
                    String phone = sc.nextLine();

                    if (manager.existsEmail(email)) {
                        System.out.println("Email already exists");
                    } else {
                        Member m = (grade == 2) ? new VipMember(name, email, phone) : new NormalMember(name, email, phone);
                        manager.add(m);
                        System.out.println("Member added successfully!");
                    }
                    break;
                case 2:
                    System.out.print("Enter email to search > ");
                    String searchEmail = sc.nextLine();
                    Member foundByEmail = manager.findByEmail(searchEmail);

                    if (foundByEmail != null) {
                        foundByEmail.printInfo();
                        foundByEmail.printGreeting();
                    } else {
                        System.out.println("Data not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter name to search > ");
                    String searchName = sc.nextLine();
                    ArrayList<Member> foundByNameList = manager.findByName(searchName);

                    if (!foundByNameList.isEmpty()) {
                        for (Member m : foundByNameList) {
                            m.printInfo();
                        }
                    } else {
                        System.out.println("Data not found.");
                    }
                    break;
                case 4:
                    manager.printAll();
                    break;
                case 5:
                    System.out.print("Enter target email to update > ");
                    String targetEmail = sc.nextLine();

                    if (manager.findByEmail(targetEmail) == null) {
                        System.out.println("Data not found.");
                        break;
                    }

                    System.out.print("New Name > ");
                    String newName = sc.nextLine();

                    System.out.print("New Email > ");
                    String newEmail = sc.nextLine();

                    System.out.print("New Phone > ");
                    String newPhone = sc.nextLine();

                    if (manager.update(targetEmail, newName, newEmail, newPhone)) {
                        System.out.println("Member updated successfully!");
                    } else {
                        System.out.println("Update failed");
                    }
                    break;
                case 6:
                    System.out.print("Enter target email to delete > ");
                    String delEmail = sc.nextLine();

                    if (manager.delete(delEmail)) {
                        System.out.println("Member deleted successfully!");
                    } else {
                        System.out.println("Data not found.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}