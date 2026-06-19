package org.example;

import java.util.Scanner;

public class Main {
    static int readInt(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Select plan: [1]Lite(10) [2]Basic(20) [3]Premium(30)");
        System.out.print("> ");
        int planChoice = readInt(sc);

        PricePlan plan = PricePlan.from(planChoice * 10);
        if (plan == null) plan = PricePlan.LITE;
        int appCapacity = plan.getCapacity();

        MemberManager manager = new MemberManager(appCapacity);

        while (true) {
            System.out.println("\n========== Member Management ==========");
            System.out.printf("Current membership: %d / %d\n", manager.size(), manager.capacity());
            System.out.println("[1]Add [2]Search by Email [3]Search by Name");
            System.out.println("[4]Show All [5]Update [6]Delete [7]Exit");
            System.out.print("Select menu > ");

            int choice = readInt(sc);

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

                    System.out.print("Grade [1]Normal [2]VIP > ");
                    int gradeInput = readInt(sc);

                    System.out.print("Name > ");
                    String name  = sc.nextLine();
                    System.out.print("Email > ");
                    String email = sc.nextLine();
                    System.out.print("Phone > ");
                    String phone = sc.nextLine();

                    if (manager.existsEmail(email)) {
                        System.out.println("Email already exists");
                    } else {
                        Member m = (gradeInput == 2) ? new VipMember(name, email, phone) : new NormalMember(name, email, phone);
                        manager.add(m);
                        System.out.println("Member added successfully!");
                    }

                    break;
                case 2:
                    System.out.print("Enter email to search > ");
                    String searchEmail = sc.nextLine();
                    Member foundByEmail = manager.findByEmail(searchEmail);
                    if (foundByEmail != null) foundByEmail.printInfo();
                    else System.out.println("Data not found.");

                    break;
                case 3:
                    System.out.print("Enter name to search > ");
                    String searchName = sc.nextLine();
                    Member foundByName = manager.findByName(searchName);

                    if (foundByName != null) {
                        foundByName.printInfo();
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
                        System.out.println("Update failed (Email may already exist)");
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