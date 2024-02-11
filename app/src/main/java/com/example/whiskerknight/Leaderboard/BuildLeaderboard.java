package com.example.whiskerknight.Leaderboard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BuildLeaderboard {
    private static BuildLeaderboard instance;
    private List<String> leaderboardList;

    private BuildLeaderboard() {
        this.leaderboardList = new ArrayList<>();
    }

    public static BuildLeaderboard getInstance() {
        if (instance == null) {
            synchronized (BuildLeaderboard.class) {
                if (instance == null) {
                    instance = new BuildLeaderboard();
                }
            }
        }
        return instance;
    }

    public void addEntry(String playerEntry) {
        this.leaderboardList.add(playerEntry);
    }

    public List<String> sortList() {
        List<String> sortedList = new ArrayList<>(leaderboardList);
        sortedList.sort(new Comparator<String>() {
            @Override
            public int compare(String entry1, String entry2) {
                int newEntry = Integer.parseInt(entry1.substring(0, entry1.indexOf(" ")));
                int compareEntry = Integer.parseInt(entry2.substring(0, entry2.indexOf(" ")));
                return Integer.compare(compareEntry, newEntry);
            }
        });
        return sortedList.subList(0, Math.min(5, sortedList.size()));
    }
}
