package com.example.whiskerknight.viewmodel;

import android.graphics.Rect;
import android.widget.ImageView;

import androidx.lifecycle.ViewModel;

import com.example.whiskerknight.model.Player;

import java.util.Calendar;
import java.util.Date;


public class PlayerVM extends ViewModel {
    private int x = 0;
    private Player player;
    public PlayerVM() {
        player = Player.getPlayer();
    }
    public void changePlayerHealth(int i) {
        player.setHealth(player.getHealth() + i);
    }
    public void changePlayerScore(int i) {
        if (player.getScore() > 0) {
            player.setScore(player.getScore() + i);
        } else if (player.getScore() < 0) {
            x++;
        }
    }
    public void difficultyModeConfiguration() {
        // sets lives based on difficulty
        if (Player.getDifficulty().equals("Hard")) {
            player.setHealth(5);
        } else if (Player.getDifficulty().equals("Medium")) {
            player.setHealth(3);
        } else {
            player.setHealth(3);
        }
    }

    //player entry for leaderboard
    public String playerEntry() {
        Date systemTime = Calendar.getInstance().getTime();
        return new String(player.getScore() + " " + player.getUsername() + " "
                + systemTime.toString());
    }
}