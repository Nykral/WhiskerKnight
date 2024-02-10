package com.example.whiskerknight.model;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.ImageView;

import com.example.whiskerknight.Activity.GameDisplay;
import com.example.whiskerknight.Activity.GameLoop;
import com.example.whiskerknight.Activity.Utils;
import com.example.whiskerknight.gamepanel.HealthBar;
import com.example.whiskerknight.gamepanel.Joystick;
import com.example.whiskerknight.graphics.Animator;

public class Player extends Circle {
    public static final double SPEED_PIXELS_PER_SECOND = 400.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    public static final int maxHealth = 5;
    private Joystick joystick;
    private HealthBar healthBar;
    private int health = maxHealth;
    private Animator animator;
    private PlayerState playerState;

    private String username;
    private int score;
    private static String difficulty;
    private String avatar;
    private int timePlayed;
    private static volatile Player player;
    private Player(Context context, Joystick joystick, Animator animator, String username, int health, int score,
                   String difficulty, String avatar, int timePlayed, double posX, double posY, double radius) {
        super(context, posX, posY, radius);
        this.joystick = joystick;
        this.healthBar = new HealthBar(context, this);
        this.animator = animator;
        this.playerState = new PlayerState(this);
        this.username = username;
        this.health = health;
        this.score = score;
        Player.difficulty = difficulty;
        this.avatar = avatar;
        this.timePlayed = timePlayed;
    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        animator.draw(canvas, gameDisplay, this);

        healthBar.draw(canvas, gameDisplay);
    }

    @Override
    public void update() {

        // Update velocity based on actuator of joystick
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;

        // Update position
        positionX += velocityX;
        positionY += velocityY;

        // Update direction
        if (velocityX != 0 || velocityY != 0) {
            // Normalize velocity to get direction (unit vector of velocity)
            double distance = Utils.getDistanceBetweenPoints(0, 0, velocityX, velocityY);
            directionX = velocityX/distance;
            directionY = velocityY/distance;
        }

        playerState.update();
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
    public static String getDifficulty() {
        return difficulty;
    }
    public String getAvatar() {
        return avatar;
    }
    public int getTimePlayed() {
        return timePlayed;
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
        Player.difficulty = difficulty;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public void setTimePlayed(int timePlayed) {
        this.timePlayed = timePlayed;
    }
    public PlayerState getPlayerState() {
        return playerState;
    }

}