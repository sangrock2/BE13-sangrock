package org.example;

import java.util.*;

public class AccountBookImpl implements AccountBook {
    private Map<String, List<Item>> data = new HashMap<>();
    private Scanner sc = new Scanner(System.in);

    @Override
    public void addAccount() {
        System.out.print("Enter Date: ");
        String date = sc.nextLine();

        List<Item> items = new ArrayList<>();

        mainLoop:
        while (true) {
            System.out.print("Enter Item: ");
            String itemName = sc.nextLine();

            System.out.print("Enter Price: ");
            int price = Integer.parseInt(sc.nextLine());

            items.add(new Item(itemName, price));

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

        System.out.println("==================");
        System.out.printf("[%s] Add Completed\n", date);

        items.forEach(item -> {
            System.out.printf("%s : %d KRW \n", item.getName(), item.getPrice());
        });

        int total = items.stream().mapToInt(item -> item.getPrice()).sum();

        System.out.println("==================");
        System.out.printf("Total : %d KRW\n", total);

        data.put(date, items);
    }

    @Override
    public void showAccount() {
        if (data.isEmpty()) {
            System.out.println("Data not found");
            return;
        }

        List<String> dates = new ArrayList<>(data.keySet());
        dates.sort(Collections.reverseOrder());

        System.out.println("== DATE ==");
        for (String date : dates) {
            System.out.println(date);
        }

        System.out.print("Enter search date : ");
        String date = sc.nextLine();
        System.out.println();

        if (!data.containsKey(date)) {
            System.out.println("Data not found\n");
            return;
        }

        System.out.printf("[%s]\n", date);

        data.get(date).forEach(item -> {
            System.out.printf("%s : %d KRW \n", item.getName(), item.getPrice());
        });
    }

    @Override
    public void deleteAll() {
        System.out.print("Enter delete date : ");
        String date = sc.nextLine();
        System.out.println();

        if (!data.containsKey(date)) {
            System.out.println("Data not found\n");
            return;
        }

        data.remove(date);
        System.out.printf("[%s] Delete Completed\n", date);
    }

    @Override
    public void deleteItem() {
        System.out.print("Enter delete date : ");
        String date = sc.nextLine();
        System.out.println();

        if (!data.containsKey(date)) {
            System.out.println("Data not found\n");
            return;
        }

        List<Item> items = data.get(date);

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            System.out.printf("%d. %s : %s KRW \n", i+1, item.getName(), item.getPrice());
        }

        int deleteIdx = Integer.parseInt(sc.nextLine());

        if (deleteIdx > 0 && deleteIdx <= items.size()) {
            items.remove(deleteIdx-1);

            if (items.isEmpty()) {
                data.remove(date);
            }
        } else {
            System.out.println("Data not found\n");
        }
    }
}
