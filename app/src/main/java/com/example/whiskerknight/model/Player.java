package com.example.whiskerknight.model;

import android.content.Context;
import android.graphics.Canvas;
import com.example.whiskerknight.R;


import com.example.whiskerknight.Activity.GameDisplay;
import com.example.whiskerknight.viewmodel.Utils;
import com.example.whiskerknight.viewmodel.Joystick;

import java.util.Calendar;
import java.util.Date;

import pl.droidsonroids.gif.GifImageView;

public class Player extends Circle {
    public static final double speed = 10.0;
    public final int maxMana = 5;
    public int health = 5;
    public int mana;
    private boolean isMoving;
    private String username;
    private int score;
    private String difficulty;
    private volatile static Player player;
    private String lastPress;
    private GifImageView image;


    public Player(boolean isMoving, String username, int health, int score,
                  String difficulty, double posX, double posY, double radius, String lastPress, GifImageView image) {
        super(posX, posY, radius);
        this.isMoving = false;
        this.username = username;
        this.health = health;
        this.mana = maxMana;
        this.score = score;
        this.difficulty = difficulty;
        this.lastPress = lastPress;
        this.image = image;
    }

    public Player() {
        super(500, 1100, 30);
        this.isMoving = false;
        this.username = "Username";
        this.health = 5;
        this.mana = maxMana;
        this.score = 0;
        this.difficulty = "Medium";
        this.lastPress = "DOWN";
        this.image = null;
    }

    public static Player getPlayer() {
        if (player == null) {
            synchronized (Player.class) {
                if (player == null) {
                    player = new Player();
                }
            }
        }
        return player;
    }

    public void setImage(GifImageView image) {
        this.image = image;
    }

    public GifImageView getImage() {
        return image;
    }

    public void difficultyModeConfiguration() {
        if (getDifficulty().equals("Hard")) {
            setHealth(3);
        } else if (getDifficulty().equals("Medium")) {
            setHealth(4);
        } else if (getDifficulty().equals("Easy")) {
            setHealth(5);
        }
    }
    @Override
    public void update() {
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public void setDirection(String direction) {
        if (direction.equals("UP")) {
            this.lastPress = "UP";
            this.directionY = -1;
            this.directionX = 0;
        } else if (direction.equals("DOWN")) {
            this.lastPress = "DOWN";
            this.directionY = 1;
            this.directionX = 0;
        } else if (direction.equals("LEFT")) {
            this.lastPress = "LEFT";
            this.directionX = -1;
            this.directionY = 0;
        } else {
            this.lastPress = "RIGHT";
            this.directionX = 1;
            this.directionY = 0;
        }
    }


    public void setLastPress(String lastPress) {
        this.lastPress = lastPress;
    }

    public String getLastPress() {
        return lastPress;
    }

    public String getUsername() {
        return username;
    }
    public int getHealth() {
        return health;
    }
    public int getScore() {
        return score;
    }
    public int getMana() {
        return mana;
    }
    public String getDifficulty() {
        return difficulty;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void setMana(int mana) {
        this.mana = mana;
    }
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

}