package org.example.solid;

// 단일 책임 원칙

import java.util.ArrayList;

public class Srp {
    public class Journal {
        private ArrayList<String> entries = new ArrayList<>();

        public void add(String text) { entries.add(text); }

        public String getText() {
            StringBuilder text = new StringBuilder();

            for (String entry : entries) {
                text.append(entry).append("\n");
            }

            return text.toString();
        }
    }

    public class JournalSaver {
        public void print(Journal journal) {
            System.out.println(journal.getText());
        }
    }

}
