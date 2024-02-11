package com.example.whiskerknight.model;

import android.content.Context;
import android.graphics.Canvas;

import com.example.whiskerknight.Activity.GameDisplay;
import com.example.whiskerknight.viewmodel.Utils;
import com.example.whiskerknight.viewmodel.Joystick;

import java.util.Calendar;
import java.util.Date;

public class Player extends Circle {
    public static final double speed = 10.0;
    public int health = 5;
    private boolean isMoving;
    private String direction;
    private PlayerState playerState;
    private String username;
    private int score;
    private String difficulty;
    private volatile static Player player;


    public Player(boolean isMoving, String username, int health, int score,
                  String difficulty, String avatar, int timePlayed, double posX, double posY, double radius, String direction) {
        super(posX, posY, radius);
        this.isMoving = false;
        this.playerState = new PlayerState(this);
        this.username = username;
        this.health = health;
        this.score = score;
        this.difficulty = difficulty;
        this.direction = direction;
    }

    public Player() {
        super(500, 1100, 30);
        this.isMoving = false;
        this.playerState = new PlayerState(this);
        this.username = "Username";
        this.health = 5;
        this.score = 0;
        this.difficulty = "Medium";
        this.direction = "DOWN";
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
        playerState.update(this.direction, this.isMoving);
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
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
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    public PlayerState getPlayerState() {
        return playerState;
    }

}