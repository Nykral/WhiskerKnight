package com.example.whiskerknight.model;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.widget.ImageView;

import com.example.whiskerknight.Activity.GameDisplay;

public class Beam extends Circle {

    public static final double speed = 3.0;
    public ImageView image;

    public Beam(Player cat, ImageView image) {
        super(cat.getPositionX(), cat.getPositionY(), 25);
        this.image = image;
        this.directionX = cat.getDirectionX();
        this.directionY = cat.getDirectionY();
    }

    public ImageView getImage() {
        return image;
    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
    }

    @Override
    public void update() {
        image.setVisibility(View.VISIBLE);
        image.setX((float) positionX);
        image.setY((float) positionY);
        positionX = positionX + directionX*speed;
        positionY = positionY + directionY*speed;
    }
}