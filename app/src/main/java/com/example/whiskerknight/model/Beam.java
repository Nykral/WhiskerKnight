package com.example.whiskerknight.model;

import android.content.Context;
import android.graphics.Canvas;

import com.example.whiskerknight.Activity.GameDisplay;

public class Beam extends Circle {

    public static final double speed = 800.0;

    public Beam(Player cat) {
        super(cat.getPositionX(), cat.getPositionY(), 25);
        velocityX = cat.getDirectionX()*speed;
        velocityY = cat.getDirectionY()*speed;
    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
    }

    @Override
    public void update() {
        positionX = positionX + velocityX;
        positionY = positionY + velocityY;
    }
}