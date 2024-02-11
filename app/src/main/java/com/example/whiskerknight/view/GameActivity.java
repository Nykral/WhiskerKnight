package com.example.whiskerknight.view;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.whiskerknight.R;
import com.example.whiskerknight.model.Circle;
import com.example.whiskerknight.viewmodel.Joystick;
import com.example.whiskerknight.model.Beam;
import com.example.whiskerknight.model.Slime;
import com.example.whiskerknight.model.Player;
import com.example.whiskerknight.viewmodel.PlayerVM;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    private Player player;
    private PlayerVM playerVM;
    private ImageView imageViewCharacter;
    private Slime slime;
    private Handler enemyHandler = new Handler(Looper.getMainLooper());
    private Handler hitHandler = new Handler(Looper.getMainLooper());
    private final int leftBoundary = 250;
    private final int rightBoundary = 850;
    RelativeLayout layout;

    private boolean isAttacking = false;
    private boolean attackLaunched = false;
    private Beam beam;
    private TextView textViewHealth;
    private int weaponSize = 200;
    private int seconds = 0;
    private int joystickPointerId = 0;
    private Joystick joystick;
    private int numberOfSpellsToCast = 0;
    private List<Slime> slimeList = new ArrayList<Slime>();
    private List<Beam> beamList = new ArrayList<Beam>();
    private RelativeLayout.LayoutParams weaponParams = new RelativeLayout.LayoutParams(
            weaponSize,
            weaponSize
    );
    private Timer scoreTimer;
    private Timer enemyRunner;
    private Timer manaTimer;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        MediaPlayer music = MediaPlayer.create(GameActivity.this, R.raw.scene1);
        music.start();

        playerVM = new ViewModelProvider(this).get(PlayerVM.class);

        textViewHealth = findViewById(R.id.textViewStartingHealth);
        TextView textViewPlayerName = findViewById(R.id.textViewPlayerName);
        TextView textViewScore = findViewById(R.id.textViewScore);
        imageViewCharacter = findViewById(R.id.imageViewCharacter);

        layout = findViewById(R.id.map1);
        player = Player.getPlayer();

        //concatenate player's values with the category
        textViewPlayerName.setText("Player Name: " + player.getUsername());
        textViewHealth.setText("Health: " + player.getHealth());

        imageViewCharacter.setImageResource(R.drawable.stand_down);

        // updates the player's score in real time
        scoreTimer = new Timer();
        scoreTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        seconds++;

                        player.setScore(player.getScore() + 1);
                        textViewScore.setText("Current Score: " + player.getScore());
                        if (player.getHealth() <= 0) {
                            Intent diedIntent = new Intent(GameActivity.this, EndActivity.class);
                            scoreTimer.cancel();
                            enemyRunner.cancel();
                            manaTimer.cancel();
                            startActivity(diedIntent);
                            finish();
                        }

                        if (seconds % 10 == 0) {
                            for (int i = 0; i < seconds / 5; i++) {
                                ImageView slimeImage = new ImageView(GameActivity.this);
                                slimeImage.setImageResource(R.mipmap.ic_launcher);
                                setSlimeImage(slimeImage, 100, 100);
                                slimeList.add(new Slime(player, slimeImage));
                            }
                        }
                    }
                });
            }
        }, 0, 1000);

        enemyRunner = new Timer();
        enemyRunner.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        update(player.getPositionX(), player.getPositionY());
                    }
                });
            }
        }, 0, 35);

        manaTimer = new Timer();
        int delay;
        if (player.getDifficulty().equals("Hard")) {
            delay = 3000;
        } else if (player.getDifficulty().equals("Medium")) {
            delay = 2000;
        } else {
            delay = 1000;
        }
        manaTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (player.getMana() != 5) {
                            player.setMana(player.getMana() + 1);
                        }
                    }
                });
            }
        }, 0, delay);

        layout.setFocusableInTouchMode(true);
        layout.requestFocus();

        layout.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DPAD_UP
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (player.getPositionY() - 1 > 0) {
                        player.setPositionY(player.getPositionY() - 1*Player.speed);
                    }
                    imageViewCharacter.setY((float) player.getPositionY());
                    player.setDirection("UP");
                    player.setMoving(true);
                    player.update();
                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (player.getPositionY() + 1 < 2050) {
                        player.setPositionY(player.getPositionY() + 1*Player.speed);
                    }
                    imageViewCharacter.setY((float) player.getPositionY());
                    player.setDirection("DOWN");
                    player.setMoving(true);
                    player.update();
                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (player.getPositionX() - 1 > 0) {
                        player.setPositionX(player.getPositionX() - 1*Player.speed);
                    }
                    imageViewCharacter.setX((float) player.getPositionX());
                    player.setDirection("LEFT");
                    player.setMoving(true);
                    player.update();
                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (player.getPositionX() + 1 < 1000) {
                        player.setPositionX(player.getPositionX() + 1*Player.speed);
                    }
                    imageViewCharacter.setX((float) player.getPositionX());
                    player.setDirection("RIGHT");
                    player.setMoving(true);
                    player.update();
                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_SPACE
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (player.getMana() != 0) {
                        player.setMana(player.getMana() - 1);
                        ImageView beamImage = new ImageView(GameActivity.this);
                        beamImage.setImageResource(R.mipmap.ic_launcher);
                        setBeamImage(beamImage, 50, 50);
                        beamList.add(new Beam(player, beamImage));
                        return true;
                    }
                }
                player.setMoving(false);
                player.update();
                return false;
            }
        });


//
//        Iterator<Slime> iteratorSlime = slimeList.iterator();
//        while (iteratorSlime.hasNext()) {
//            Circle enemy = iteratorSlime.next();
//            if (Circle.isColliding(enemy, player)) {
//                // Remove enemy if it collides with the player
//                iteratorSlime.remove();
//                player.setHealth(player.getHealth() - 1);
//                continue;
//            }
//
//            Iterator<Beam> iteratorBeam = beamList.iterator();
//            while (iteratorBeam.hasNext()) {
//                Circle beam = iteratorBeam.next();
//                // Remove enemy if it collides with a spell
//                if (Circle.isColliding(beam, enemy)) {
//                    iteratorBeam.remove();
//                    iteratorSlime.remove();
//                    break;
//                }
//            }
//        }

//        if (player.getHealth() <= 0) {
//            Intent diedIntent = new Intent(GameActivity.this, EndActivity.class);
//            statusTimer.cancel();
//            scoreTimer.cancel();
//            music.pause();
//            startActivity(diedIntent);
//            finish();
//
//
//        Iterator<Slime> slimeIterator = enemyList.iterator();
//        while (slimeIterator.hasNext()) {
//            Slime currentSlime = slimeIterator.next();
//            if (currentSlime.hit(findViewById(R.id.imageViewCharacter))) {
//                slimeIterator.remove();
//                player.setHealth(player.getHealth() - 1);
//                continue;
//            }
//
//            Iterator<Beam> laserIterator = beamList.iterator();
//            while (laserIterator.hasNext()) {
//                if (currentSlime.hit(findViewById(R.id.imageViewCharacter))) {
//                    laserIterator.remove();
//                    slimeIterator.remove();
//                    enemyKilled++;
//                    playerVM.changePlayerScore(5);
//                }
//            }
//        }
//    }
//
//        /*
//        int newLeftMargin1 = (int) slime.getImage().getX() + enemyDirection * slime.getSpeed();
//
//        if (newLeftMargin1 < leftBoundary) {
//            slime.getImage().setX(leftBoundary);
//            enemyDirection = 1; // Change direction when hitting the left boundary
//        } else if (newLeftMargin1 + slime.getImage().getWidth() > rightBoundary) {
//            slime.getImage().setX(rightBoundary - slime.getImage().getWidth());
//            enemyDirection = -1; // Change direction when hitting the right boundary
//        } else {
//            slime.getImage().setX(newLeftMargin1);
//        }
//
////    private void destroy() {
////        if (laser.getVisibility() == View.VISIBLE && slime.hit(laser)) {
////            slime.getImage().setImageDrawable(null);
////        }
////    }
//
//    private boolean trackHits() {
//        if (slime.hit(imageViewCharacter)) {
//            playerVM.changePlayerHealth(-1);
//            return true;
//        }
//        return false;
//    }
//    private void attack() {
//        isAttacking = true;
//
//        // Schedule a task to reset isAttacking after a delay (e.g., 1000 milliseconds)
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                isAttacking = false;
//            }
//        }, 1000); // Adjust the delay as needed
//    }
//    private void launchAttack() {
////        if (!attackLaunched) {
////            // Move the weapon away horizontally
////            laser.setVisibility(View.VISIBLE);
////            laser.moveHorizontally(laser.getX() + 200); //Adjust horizontal dist as needed
////            attackLaunched = true;
////        }
//
////
    }
    private void setSlimeImage(ImageView imageView, int width, int height) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);

        imageView.setLayoutParams(params);
        imageView.setImageResource(R.drawable.slime_jump);
        imageView.setVisibility(View.INVISIBLE);
        layout.addView(imageView);
    }

    private void setBeamImage(ImageView imageView, int width, int height) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);

        imageView.setLayoutParams(params);
        imageView.setImageResource(R.drawable.beam);
        imageView.setVisibility(View.INVISIBLE);
        layout.addView(imageView);
    }

    private void update(double posX, double posY) {
        for (Slime slime : slimeList) {
            slimeTracking(slime, posX, posY);
        }
        for (Beam beam : beamList) {
            beam.update();
        }

        Iterator<Slime> slimeIterator = slimeList.iterator();
        while (slimeIterator.hasNext()) {
            Slime currentSlime = slimeIterator.next();
            if (currentSlime.hit(findViewById(R.id.imageViewCharacter))) {
                currentSlime.getImage().setImageResource(0);
                slimeIterator.remove();
                player.setHealth(player.getHealth() - 1);
                textViewHealth.setText("Health: " + player.getHealth());
            }
            Iterator<Beam> iteratorBeam = beamList.iterator();
            while (iteratorBeam.hasNext()) {
                Beam beam = iteratorBeam.next();
                // Remove enemy if it collides with a spell
                if (currentSlime.hit(beam.getImage())) {
                    currentSlime.getImage().setImageResource(0);
                    beam.getImage().setImageResource(0);
                    iteratorBeam.remove();
                    slimeIterator.remove();
                    break;
                }
            }
        }
    }
    public void slimeTracking(Slime slime, double posX, double posY) {
        double distanceToPlayerX = posX - slime.getPositionX();
        double distanceToPlayerY = posY - slime.getPositionY();


        slime.setPositionX(slime.getPositionX() + Math.signum(distanceToPlayerX)*1.5);
        slime.setPositionY(slime.getPositionY() + Math.signum(distanceToPlayerY)*2);

        slime.getImage().setVisibility(View.VISIBLE);

        slime.getImage().setX((float) slime.getPositionX());
        slime.getImage().setY((float) slime.getPositionY());
    }
}
