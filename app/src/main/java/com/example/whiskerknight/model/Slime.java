package com.example.whiskerknight.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.widget.ImageView;

import com.example.whiskerknight.Activity.GameDisplay;
import com.example.whiskerknight.Activity.GameLoop;
import com.example.whiskerknight.viewmodel.EnemySubscriber;
import com.example.whiskerknight.viewmodel.EnemyVM;
import com.example.whiskerknight.viewmodel.PlayerVM;


public class Slime extends Circle {
    private ImageView image;
    private static double speed = com.example.whiskerknight.model.Player.SPEED_PIXELS_PER_SECOND*0.6;
    private static final double MAX_SPEED = speed / GameLoop.MAX_UPS;
    private static double spawnRateMin;
    private static final double spawnRateSec = spawnRateMin/60.0;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS/spawnRateSec;
    private static double updatesUntilNextSpawn = UPDATES_PER_SPAWN;
    private boolean isDestroyed = false;
    private Player player;
    private PlayerVM playervm;

    public Slime(Context context, Player player, double positionX, double positionY, double radius, ImageView image) {
        super(context, positionX, positionY, radius);
        this.player = player;
        this.image = image;
        setDifficulty(player);
    }

    public Slime(Context context, Player player, ImageView image) {
        super(context, Math.random()*1000, Math.random()*1000, 30);
        this.player = player;
        this.image = image;
        setDifficulty(player);
    }

    public void setDifficulty(Player player) {
        String difficulty = Player.getDifficulty();
        if (difficulty.equals("Easy")) {
            speed = 0.4;
            spawnRateMin = 20;
        } else if (difficulty.equals("Medium")) {
            speed = 0.6;
            spawnRateMin = 30;
        } else {
            speed = 0.8;
            spawnRateMin = 45;
        }
    }

    public static boolean readyToSpawn() {
        if (updatesUntilNextSpawn <= 0) {
            updatesUntilNextSpawn += UPDATES_PER_SPAWN;
            return true;
        } else {
            updatesUntilNextSpawn --;
            return false;
        }
    }


    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {

    }

    @Override
    public void update() {
        double distanceToPlayerX = player.getPositionX() - positionX;
        double distanceToPlayerY = player.getDirectionY() - positionY;

        // Calculate (absolute) distance between enemy (this) and player
        double distanceToPlayer = GameObject.getDistanceBetweenObjects(this, player);

        // Calculate direction from enemy to player
        double directionX = distanceToPlayerX/distanceToPlayer;
        double directionY = distanceToPlayerY/distanceToPlayer;

        // Set velocity in the direction to the player
        if(distanceToPlayer > 0) { // Avoid division by zero
            velocityX = directionX*MAX_SPEED;
            velocityY = directionY*MAX_SPEED;
        } else {
            velocityX = 0;
            velocityY = 0;
        }

        positionX += velocityX;
        positionY += velocityY;
    }


    public boolean hit(ImageView player) {
        Rect playerRect = new Rect();
        player.getHitRect(playerRect);

        int width = 0;
        int height = 0;

        Rect enemyRect = new Rect();
        image.getHitRect(enemyRect);

        width += 1;
        height += 1;
        return Rect.intersects(playerRect, enemyRect);
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
    public void setSpeed(double num) {
        speed = num;
    }
    public void setRespawnTimerMin (double timer) { spawnRateMin = timer; }
    public void takeDamage(int damage) {
        if (!isDestroyed) {
            destroyEnemy();
        }
    }
    private void destroyEnemy() {
        isDestroyed = true;
    }
    public ImageView getImage() {
        return image;
    }
    public double getSpeed() {
        return speed;
    }

    public double getSpawnRateMin() { return spawnRateMin; }

}
