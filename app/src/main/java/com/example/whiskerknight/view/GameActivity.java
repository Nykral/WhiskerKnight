package com.example.whiskerknight.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.whiskerknight.R;
import com.example.whiskerknight.gamepanel.Joystick;
import com.example.whiskerknight.model.Laser;
import com.example.whiskerknight.model.Slime;
import com.example.whiskerknight.model.Player;
import com.example.whiskerknight.viewmodel.PlayerVM;

import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class GameActivity extends AppCompatActivity {
    private Player player;
    private PlayerVM playerVM;
    private ImageView imageViewCharacter;
    private Slime slime;
    private Handler enemyHandler = new Handler(Looper.getMainLooper());
    private Handler hitHandler = new Handler(Looper.getMainLooper());
    private int enemyDirection = 1; // 1 for right, -1 for left
    private final int leftBoundary = 250;
    private final int rightBoundary = 850;
    private boolean isAttacking = false;
    private boolean attackLaunched = false;
    private Laser laser;
    private int weaponSize = 200;
    private int enemyKilled = 0;
    private int pointsEarned = 0;


    private List<Slime> enemyList = new ArrayList<>();
    private List<Laser> laserList = new ArrayList<>();
    private RelativeLayout.LayoutParams weaponParams = new RelativeLayout.LayoutParams(
            weaponSize,
            weaponSize
    );
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay_screen1);

        //MediaPlayer music = MediaPlayer.create(GameActivity1.this, R.raw.scene1);
        //music.start();

        playerVM = new ViewModelProvider(this).get(PlayerVM.class);
        //player = new Player();

        TextView textViewDifficulty = findViewById(R.id.textViewDifficulty);
        TextView textViewHealth = findViewById(R.id.textViewStartingHealth);
        TextView textViewPlayerName = findViewById(R.id.textViewPlayerName);
        TextView textViewScore = findViewById(R.id.textViewScore);
        TextView textViewTime = findViewById(R.id.textViewTime);
        imageViewCharacter = findViewById(R.id.imageViewCharacter);

        //player.setX(500);
        //player.setY(1236);

        slime = new Slime(this, player, findViewById(R.id.enemy1_1));

        //concatenate player's values with the category
        textViewPlayerName.setText("Player Name: " + player.getUsername());
        textViewDifficulty.setText("Difficulty: " + player.getDifficulty());
        textViewHealth.setText("Health: " + player.getHealth());
        textViewTime.setText("Time Spent: " + player.getTimePlayed() + "s");

        imageViewCharacter.setImageResource(R.drawable.sprite3);
        laser = new Laser(this, player);

        // updates the player's score in real time
        Timer scoreTimer = new Timer();
        scoreTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        playerVM.changePlayerScore(1);
                        playerVM.changePlayerTime(1);
                        textViewTime.setText("Time Spent: " + player.getTimePlayed() + "s");
                        textViewScore.setText("Current Score: " + player.getScore());
                    }
                });
            }
        }, 0, 1000);

        enemyHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //updateEnemies();
                update();
                // Repeat the task after a delay
                enemyHandler.postDelayed(this, 100); // adjust the delay as needed
            }
        }, 80);

        //update screen to match values
        hitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //destroy();
                if (trackHits()) {
                    textViewHealth.setText("Health: " + player.getHealth());
                    textViewScore.setText("Current Score: " + player.getScore());
                    enemyHandler.postDelayed(this, 1500); // adjust the delay as needed
                } else {
                    enemyHandler.postDelayed(this, 100); // adjust the delay as needed
                }
            }
        }, 80);

        Button btnUp = findViewById(R.id.btnUp);
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.getPositionY() - 64 > 340) {
                    //player.setY(player.getY() - 64);
                }
                imageViewCharacter.setY((int) player.getPositionY());
            }
        });

        Button btnDown = findViewById(R.id.btnDown);
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.getPositionY() + 64 < 1430) {
                    //player.setY(player.getY() + 64);
                }
                imageViewCharacter.setY((int) player.getPositionY());
            }
        });
        Button btnLeft = findViewById(R.id.btnLeft);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.getPositionX() - 64 > 140) {
                    //player.setX(player.getPositionX() - 64);
                }
                imageViewCharacter.setX((int) player.getPositionX());
            }
        });
        Button btnRight = findViewById(R.id.btnRight);
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.getPositionX() + 64 < 820) {
                    //player.setY(player.getPositionY() + 64);
                }
                imageViewCharacter.setX((int) player.getPositionX());
            }
        });
        //End button option
        Button btnEndGame = findViewById(R.id.btnEndGame);
        btnEndGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent endingIntent = new Intent(GameActivity.this, EndActivity.class);
                scoreTimer.cancel();
                //music.pause();
                startActivity(endingIntent);
                finish();
            }
        });
        RelativeLayout yourRelativeLayout = findViewById(R.id.map1); // Replace with your layout ID

        // Set the initial position of the weapon to match the player's position
        weaponParams.leftMargin = (int) player.getPositionX();
        weaponParams.topMargin = (int) player.getPositionY();
        // Initially set weapon as invisible
        //laser.setVisibility(View.INVISIBLE);
        //yourRelativeLayout.addView(laser, weaponParams);


        // Spacebar handling
        yourRelativeLayout.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_SPACE
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (!attackLaunched) {
                        // Throw the weapon
                        launchAttack();
                    } else {
                        // Reset weapon position and make it invisible
                        //laser.setVisibility(View.VISIBLE);
                    }
                    // Toggle the thrown state
                    attackLaunched = !attackLaunched;
                    return true;
                }
                return false;
            }
        });

        // Set focus on the layout to receive key events
        yourRelativeLayout.setFocusableInTouchMode(true);
        yourRelativeLayout.requestFocus();

        Timer statusTimer = new Timer();
        statusTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        if (playerVM.isCollisionDoor(imageViewCharacter, door.getImageDoor())) {
//                            Intent nextMap2 = new Intent(GameActivity.this, GameActivity2.class);
//                            scoreTimer.cancel();
//                            statusTimer.cancel();
//                            music.pause();
//                            startActivity(nextMap2);
//                        }
                        if (player.getHealth() <= 0) {
                            Intent diedIntent = new Intent(GameActivity.this, EndActivity.class);
                            statusTimer.cancel();
                            scoreTimer.cancel();
//                            music.pause();
                            startActivity(diedIntent);
                            finish();
                        }
                    }
                });
            }
        }, 0, 250); // Check every .25 seconds
    }

    private void spawnSlime() {
        if (player.getTimePlayed() % 60 == 0) {
            for (Slime slime : enemyList) {
                slime.setSpeed(slime.getSpeed() + 0.2);
                slime.setRespawnTimerMin(slime.getSpawnRateMin() + 5);
            }
        }

        if (Slime.readyToSpawn()) {
            enemyList.add(new Slime(this, player, findViewById(R.id.enemy1_1)));
        }
    }
    private void update() {
        spawnSlime();

        for (Slime slime : enemyList) {
            slime.update();
        }

        for (Laser laser : laserList) {
            laser.update();
        }
        Iterator<Slime> slimeIterator = enemyList.iterator();
        while (slimeIterator.hasNext()) {
            Slime currentSlime = slimeIterator.next();
            if (currentSlime.hit(findViewById(R.id.imageViewCharacter))) {
                slimeIterator.remove();
                player.setHealth(player.getHealth() - 1);
                continue;
            }

            Iterator<Laser> laserIterator = laserList.iterator();
            while (laserIterator.hasNext()) {
                if (currentSlime.hit(findViewById(R.id.imageViewCharacter))) {
                    laserIterator.remove();
                    slimeIterator.remove();
                    enemyKilled++;
                    playerVM.changePlayerScore(5);
                }
            }
        }
    }

        /*
        int newLeftMargin1 = (int) slime.getImage().getX() + enemyDirection * slime.getSpeed();

        if (newLeftMargin1 < leftBoundary) {
            slime.getImage().setX(leftBoundary);
            enemyDirection = 1; // Change direction when hitting the left boundary
        } else if (newLeftMargin1 + slime.getImage().getWidth() > rightBoundary) {
            slime.getImage().setX(rightBoundary - slime.getImage().getWidth());
            enemyDirection = -1; // Change direction when hitting the right boundary
        } else {
            slime.getImage().setX(newLeftMargin1);
        }*/
/*
    private void updateEnemies() {
        moveSlime(slime);
    }*/

//    private void destroy() {
//        if (laser.getVisibility() == View.VISIBLE && slime.hit(laser)) {
//            slime.getImage().setImageDrawable(null);
//        }
//    }

    private boolean trackHits() {
        if (slime.hit(imageViewCharacter)) {
            playerVM.changePlayerHealth(-1);
            return true;
        }
        return false;
    }
    /*
    public int difficultyMultiplier() {
        if (player.getDifficulty().equals("Hard")) {
            return  3;
        } else if (player.getDifficulty().equals("Medium")) {
            return  2;
        } else {
            if (true) {
                return 1;
            } else {
                return 1;
            }
        }
    }*/
    private void attack() {
        isAttacking = true;

        // Schedule a task to reset isAttacking after a delay (e.g., 1000 milliseconds)
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                isAttacking = false;
            }
        }, 1000); // Adjust the delay as needed
    }
    private void launchAttack() {
//        if (!attackLaunched) {
//            // Move the weapon away horizontally
//            laser.setVisibility(View.VISIBLE);
//            laser.moveHorizontally(laser.getX() + 200); //Adjust horizontal dist as needed
//            attackLaunched = true;
//        }

//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Make weapon invisible again
//                laser.setVisibility(View.INVISIBLE);
//                laser.moveHorizontally(laser.getX() - 200);
//            }
//        }, 500);
    }
}
