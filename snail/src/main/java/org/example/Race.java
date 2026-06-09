package org.example;

import java.util.ArrayList;
import java.util.List;

public class Race {
    private List<String> rankList = new ArrayList<String>();

    public synchronized void finish(String name) {  // 한 번만 선언되도록 보호
        if (!rankList.contains(name)) {
            rankList.add(name);
        }
    }

    public List<String> getRankList() {
        return rankList;
    }
}
