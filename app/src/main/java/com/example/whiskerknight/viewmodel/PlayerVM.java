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
        //player = Player.getPlayer();
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
    public void changePlayerTime(int i) {
        if (player.getTimePlayed() >= 0) {
            player.setTimePlayed(player.getTimePlayed() + i);
        }
    }
    public void difficultyModeConfiguration() {
        // sets lives based on difficulty
        if (player.getDifficulty().equals("Hard")) {
            player.setHealth(15);
        } else if (player.getDifficulty().equals("Medium")) {
            player.setHealth(30);
        } else {
            player.setHealth(50);
        }
        // sets score based on difficulty
        if (player.getDifficulty().equals("Hard")) {
            player.setScore(30);
        } else if (player.getDifficulty().equals("Medium")) {
            player.setScore(50);
        } else {
            player.setScore(70);
        }

        player.setTimePlayed(0);
    }
    //player entry for leaderboard
    public String playerEntry() {
        Date systemTime = Calendar.getInstance().getTime();
        return new String(player.getScore() + " " + player.getUsername() + " "
                + systemTime.toString());
    }
    public boolean isCollisionDoor(ImageView player, ImageView door) {
        Rect playerLRect = new Rect();
        Rect doorLRect = new Rect();
        player.getHitRect(playerLRect);
        door.getHitRect(doorLRect);
        return Rect.intersects(playerLRect, doorLRect);
    }
}