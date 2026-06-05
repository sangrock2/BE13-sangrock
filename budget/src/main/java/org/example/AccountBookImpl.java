package org.example;

import java.util.*;

public class AccountBookImpl implements AccountBook {
    private Map<String, List<Item>> data = new HashMap<>();
    private Scanner sc = new Scanner(System.in);
    private final String FILENAME = "accounts.txt";

    @Override
    public void addAccount() {
        System.out.print("Enter Date: ");
        String date = sc.nextLine();

        List<Item> items = data.computeIfAbsent(date, k -> new ArrayList<>());

        mainLoop:
        while (true) {
            System.out.print("Enter Item: ");
            String itemName = sc.nextLine();

            int price;
            while (true) {
                try {
                    System.out.print("Enter Price: ");
                    price = Integer.parseInt(sc.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a NUMBER.");
                    System.out.println();
                }
            }

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

        int total = items.stream().mapToInt(Item::getPrice).sum();

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

        List<Item> items = data.get(date);

        items.forEach(item -> {
            System.out.printf("%s : %d KRW \n", item.getName(), item.getPrice());
        });

        int total = items.stream().mapToInt(Item::getPrice).sum();

        System.out.println("==================");
        System.out.printf("Total : %d KRW\n", total);
    }

    @Override
    public void deleteAll() {
        if (data.isEmpty()) {
            System.out.println("Data not found");
            return;
        }

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
        if (data.isEmpty()) {
            System.out.println("Data not found");
            return;
        }

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

        int deleteIdx;

        while (true) {
            try {
                System.out.print("Enter item number to delete: ");
                deleteIdx = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a NUMBER.");
            }
        }

        if (deleteIdx > 0 && deleteIdx <= items.size()) {
            items.remove(deleteIdx-1);

            if (items.isEmpty()) {
                data.remove(date);
            }
        } else {
            System.out.println("Data not found\n");
        }
    }

    @Override
    public void showTotal() {
        if (data.isEmpty()) {
            System.out.println("Data not found");
            return;
        }

        int total = data.values().stream().flatMap(List::stream).mapToInt(Item::getPrice).sum();

        System.out.println("==================");
        System.out.printf("Total : %d KRW\n", total);
        System.out.println("==================");
        System.out.println();
    }

    @Override
    public void updateAccount() {
        if (data.isEmpty()) {
            System.out.println("Data not found");
            return;
        }

        System.out.print("Enter date to update: ");
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

        int updateIdx;

        while (true) {
            try {
                System.out.print("Enter item number to update: ");
                updateIdx = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a NUMBER.");
            }
        }

        if (updateIdx > 0 && updateIdx <= items.size()) {
            int newPrice;

            while (true) {
                try {
                    System.out.print("Enter new price to update: ");
                    newPrice = Integer.parseInt(sc.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a NUMBER.");
                }
            }

            Item targetItem = items.get(updateIdx-1);
            items.set(updateIdx-1, new Item(targetItem.getName(), newPrice));

            System.out.println("Update Completed\n");
        } else {
            System.out.println("Data not found\n");
        }
    }

    /*
    @Override
    public void save() {
        try ( BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME, true))) {
            for (Map.Entry<String, List<Item>> entry : data.entrySet()) {
                String date = entry.getKey();
                List<Item> items = entry.getValue();

                for (Item item : items) {
                    bw.write(String.format("%s|%s|%d \n", date, item.getName(), item.getPrice()));
                    bw.newLine();
                }
            }

            System.out.println("Save Completed\n");
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }
     */

    /*
    @Override
    public void load() {
        File file = new File(FILENAME);

        if (!file.exists()) {
            System.out.println("File not found");
            return;
        }
    }
     */
}
