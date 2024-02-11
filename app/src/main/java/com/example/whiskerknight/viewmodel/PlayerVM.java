package com.example.whiskerknight.viewmodel;

import android.graphics.Rect;
import android.widget.ImageView;

import androidx.lifecycle.ViewModel;

import com.example.whiskerknight.model.Player;

import java.util.Calendar;
import java.util.Date;


public class PlayerVM extends ViewModel {
    private Player player;
    public PlayerVM() {
        player = Player.getPlayer();
    }

    //player entry for leaderboard
    public String playerEntry() {
        Date systemTime = Calendar.getInstance().getTime();
        return new String(player.getScore() + " " + player.getUsername() + " "
                + systemTime.toString());
    }
}