package org.example;

import java.util.List;
import java.util.Scanner;

public class NoticeImpl implements Notice {
    private Scanner sc = new Scanner(System.in);
    private NoticeDAO noticeDAO = new NoticeDAO();
    private boolean status;
    private String userId, name;

    @Override
    public int printMenu() {
        System.out.println("\n=== Notice Board Menu ===");
        System.out.println("[1] Sign In  [2] Sign Up  [3] New Notice  [4] Notice List");
        System.out.println("[5] Update Notice [6] Delete Notice [7] Sign Out [8] Delete Account");
        System.out.println("[9] Exit");
        System.out.print("Select Menu > ");
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return 0; // 잘못된 입력 시 0 반환
        }
    }

    @Override
    public void signUp() {
        System.out.println("\n=== Sign Up ===");

        System.out.print("Enter User ID: ");
        String inputId = sc.nextLine();

        if (noticeDAO.checkUserId(inputId)) {
            System.out.println("User ID already exists. Please try another one.");
            return;
        }

        System.out.print("Enter Password: ");
        String inputPassword = sc.nextLine();

        System.out.print("Enter Name: ");
        String inputName = sc.nextLine();

        if (noticeDAO.sigupExc(inputId, inputPassword, inputName)) {
            System.out.println("Sign up successful! You can now sign in.");
        } else {
            System.out.println("Registration failed. Please try again later.");
        }
    }

    @Override
    public void signIn() {
        System.out.println("\n=== Sign In ===");

        System.out.print("Enter User ID: ");
        String inputId = sc.nextLine();

        System.out.print("Enter Password: ");
        String inputPassword = sc.nextLine();

        SignInResponseDTO res = noticeDAO.signInExc(inputId, inputPassword);

        if (res == null) {
            System.out.println("User does not exist.");
            return;
        }

        if (res.status()) {
            userId = res.userId();
            name = res.name();

            setUserInfo(true, userId, name);
            System.out.println("Sign in successful! Welcome, " + name + ".");
        } else {
            System.out.println("Sign in failed. Please try again later.");
        }
    }

    @Override
    public void newNotice() {
        if (!checkSignIn()) {
            return;
        }

        System.out.println("\n=== Write New Notice ===");
        System.out.print("Enter Content: ");
        String content = sc.nextLine();

        if (noticeDAO.newNotice(userId, content)) {
            System.out.println("New Notice successful!");
        } else {
            System.out.println("New Notice failed!");
        }
    }

    @Override
    public void getList() {
        System.out.println("\n=== Notice List ===");
        noticeDAO.getList().forEach(System.out::println);
    }

    @Override
    public void updateNotice() {
        if (!checkSignIn()) {
            return;
        }

        List<String> list = noticeDAO.getListByUserId(userId);

        if (list.isEmpty()) {
            System.out.println("Nothing to update.");
            return;
        }

        System.out.println("\n=== Update Notice ===");
        list.forEach(System.out::println);

        System.out.print("\nEnter the Notice ID to update: ");

        int id;
        while (true) {
            try {
                id = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format.");
            }
        }

        System.out.print("Enter New Content: ");
        String content = sc.nextLine();

        if (noticeDAO.updateNotice(id, content, this.userId)) {
            System.out.println("Update Notice successful!");
        } else {
            System.out.println("Update Notice failed!");
        }
    }

    @Override
    public void deleteNotice() {
        if (!checkSignIn()) {
            return;
        }

        List<String> list = noticeDAO.getListByUserId(userId);

        if (list.isEmpty()) {
            System.out.println("Nothing to delete.");
            return;
        }

        System.out.println("\n=== My Notices ===");
        list.forEach(System.out::println);

        System.out.print("\nEnter the Notice ID to delete: ");

        int id;
        while (true) {
            try {
                id = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format.");
            }
        }

        if (noticeDAO.deleteNotice(id, this.userId)) {
            System.out.println("Delete Notice successful!");
        } else {
            System.out.println("Delete Notice failed!");
        }
    }

    @Override
    public void signOut() {
        if (!checkSignIn()) {
            return;
        }

        setUserInfo(false, null, null);
        System.out.println("Sign Out successful!");
    }

    @Override
    public void leave() {
        if (!checkSignIn()) {
            return;
        }

        System.out.println("\n=== Delete Account ===");
        System.out.print("Enter User ID to delete: ");
        String targetId = sc.nextLine();

        if (noticeDAO.checkUserId(targetId)) {
            if (noticeDAO.leaveExc(targetId)) {
                noticeDAO.deleteContentExc(targetId);

                if (targetId.equals(userId)) {
                    signOut();
                }
            } else {
                System.out.println("Failed to delete the account.");
            }
        } else {
            System.out.println("User does not exist.");
        }

    }

    private void setUserInfo(boolean status, String userId, String name) {
        this.status = status;
        this.userId = userId;
        this.name = name;
    }

    private boolean checkSignIn() {
        if (!status) {
            System.out.println("Please sign in first.");
            return false;
        }

        return true;
    }
}
