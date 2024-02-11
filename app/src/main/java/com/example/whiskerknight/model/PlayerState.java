package com.example.whiskerknight.model;

public class PlayerState {

    public enum State {
        NOT_MOVING_UP,
        NOT_MOVING_DOWN,
        NOT_MOVING_LEFT,
        NOT_MOVING_RIGHT,
        MOVING_UP,
        MOVING_DOWN,
        MOVING_LEFT,
        MOVING_RIGHT
    }

    private Player player;
    private State state;

    public PlayerState(Player player) {
        this.player = player;
        this.state = State.NOT_MOVING_DOWN;
    }

    public State getState() {
        return state;
    }

    public void update(String direction, boolean isMoving) {
        if (direction.equals("UP")) {
            if (isMoving) {
                state = State.MOVING_UP;
            } else {
                state = State.NOT_MOVING_UP;
            }
        } else if (direction.equals("DOWN")) {
            if (isMoving) {
                state = State.MOVING_DOWN;
            } else {
                state = State.NOT_MOVING_DOWN;
            }
        } else if (direction.equals("LEFT")) {
            if (isMoving) {
                state = State.MOVING_LEFT;
            } else {
                state = State.NOT_MOVING_LEFT;
            }
        } else {
            if (isMoving) {
                state = State.MOVING_RIGHT;
            } else {
                state = State.NOT_MOVING_RIGHT;
            }
        }
    }
}