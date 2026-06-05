package org.example;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AccountBookImpl implements AccountBook {
    private final String DIR = "accountbook"; // 저장 폴더
    private final Scanner sc = new Scanner(System.in);

    public AccountBookImpl() {
        File folder = new File(DIR);
        if (!folder.exists()) folder.mkdir(); // 폴더 없으면 생성
    }

    @Override
    public void addAccount() {
        String today = LocalDate.now().toString();
        File file = new File(DIR, today + ".txt");

        int total = 0;

        if (file.exists()) {
            try {
                List<String> lines = Files.readAllLines(file.toPath());
                StringBuilder content = new StringBuilder();

                for (String line : lines) {
                    if (line.trim().isEmpty()) continue;

                    if (line.startsWith("Total")) {
                        String prevTotal = line.replace("Total", "").replace(":", "").replace("KRW", "").trim();
                        total = Integer.parseInt(prevTotal);
                    } else {
                        content.append(line).append(System.lineSeparator());
                    }
                }

                Files.writeString(file.toPath(), content.toString());
            } catch (IOException | NumberFormatException e) {
                System.out.println("ERROR!");
            }
        }

        StringBuilder sb = new StringBuilder();

        mainLoop:
        while (true) {
            System.out.print("Enter Item: ");
            String itemName = sc.nextLine();

            int price;
            while (true) {
                try {
                    System.out.print("Enter Price: ");
                    price = Integer.parseInt(sc.nextLine());
                    total += price;
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a NUMBER.\n");
                }
            }

            sb.append(itemName).append(" : ").append(price).append(" KRW").append(System.lineSeparator());

            while (true) {
                System.out.print("Do you have any more data to add? (y/n):  ");
                String answer = sc.nextLine().trim().toLowerCase();

                if (answer.equals("y") || answer.equals("yes")) {
                    break;
                } else if (answer.equals("n") || answer.equals("no")) {
                    break mainLoop;
                } else {
                    System.out.print("WRONG ANSWER. Please enter y or n");
                }
            }
        }

        sb.append("Total").append(" : ").append(total).append(" KRW").append(System.lineSeparator());

        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(sb.toString());
            System.out.println("Account Added Successfully!");
        } catch (IOException e) {
            System.out.println("ERROR!");
        }
    }

    @Override
    public void showAccount() {
        File folder = new File(DIR);
        String[] files = folder.list();

        if (files == null || files.length == 0) {
            System.out.println("No account books found.");
            return;
        }

        System.out.println("--- Available Dates ---");
        for (String name : files) {
            if (name.endsWith(".txt"))
                System.out.println(name.replace(".txt", "")); // 날짜만
        }
        System.out.println();

        System.out.print("Enter search date : ");
        String date = sc.nextLine();
        System.out.println();

        File file = new File(DIR, date + ".txt");

        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;

                System.out.printf("[%s]\n", date);

                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.out.println("ERROR!");
            }
        } else {
            System.out.println("No account books found.");
        }
    }

    @Override
    public void deleteAccount() {
        System.out.print("Enter delete date : ");
        String date = sc.nextLine();
        System.out.println();

        File file = new File(DIR, date + ".txt");

        if (file.exists()) {
            if (file.delete()) System.out.printf("[%s] Delete Completed\n", date);
            else System.out.println("ERROR!");
        } else {
            System.out.println("Does not exist");
        }
    }
}
