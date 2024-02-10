package com.example.whiskerknight.graphics;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.whiskerknight.graphics.SpriteSheet;

abstract class Tile {

    protected final Rect mapLocationRect;

    public Tile(Rect mapLocationRect) {
        this.mapLocationRect = mapLocationRect;
    }

    public enum TileType {
    }

    public static Tile getTile(int idxTileType, SpriteSheet spriteSheet, Rect mapLocationRect) {
        return null;
    }

    public abstract void draw(Canvas canvas);
}