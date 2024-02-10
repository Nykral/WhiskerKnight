package com.example.whiskerknight.viewmodel;
import android.graphics.Rect;
import android.widget.ImageView;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class EnemyVM extends ViewModel {
    private int difficultyMultiplier;
    private Rect playerLRect;
    private PlayerVM playerVM;
    private List<EnemySubscriber> enemySubscribers = new ArrayList<>();
    public EnemyVM(int difficultyMultiplier, Rect playerLRect, PlayerVM playerVM) {
        this.difficultyMultiplier = difficultyMultiplier;
        this.playerLRect = playerLRect;
        this.playerVM = playerVM;
    }
    public void trackPlayer(ImageView player) {
        playerLRect = new Rect();
        player.getHitRect(playerLRect);
        notifySubscribers();
    }
    //getters and setters beyond this point
    public void setDifficultyMultiplier(String difficultyMultiplier) {
        if (difficultyMultiplier.equals("Hard")) {
            this.difficultyMultiplier = 3;
        } else if (difficultyMultiplier.equals("Medium")) {
            this.difficultyMultiplier = 2;
        } else {
            this.difficultyMultiplier = 1;
        }
        notifySubscribers();
    }
    public int getDifficultyMultiplier() {
        return difficultyMultiplier;
    }
    public Rect getPlayerLRect() {
        return playerLRect;
    }
    public PlayerVM getPlayerVM() {
        return playerVM;
    }
    //subscriber logic methods
    public void subscribe(EnemySubscriber enemySubscriber) {
        enemySubscribers.add(enemySubscriber);
    }
    public void unsubscribe(EnemySubscriber enemySubscriber) {
        enemySubscribers.remove(enemySubscriber);
    }
    protected void notifySubscribers() {
        for (EnemySubscriber enemySubscriber : enemySubscribers) {
            enemySubscriber.update(this);
        }
    }
}
