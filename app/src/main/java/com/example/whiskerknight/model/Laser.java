package com.example.whiskerknight.model;

import android.content.Context;
import android.graphics.Canvas;

//import com.example.whiskerknight.Activity.GameLoop;
import com.example.whiskerknight.Activity.GameDisplay;
import com.example.whiskerknight.R;

public class Laser extends Circle {

    public static final double SPEED_PIXELS_PER_SECOND = 800.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND * 2;
//GameLoop.MAX_UPS;

    public Laser(Context context, Player cat) {
        super(
                context,
                cat.getPositionX(),
                cat.getPositionY(),
                25
        );
        velocityX = cat.getDirectionX()*MAX_SPEED;
        velocityY = cat.getDirectionY()*MAX_SPEED;
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