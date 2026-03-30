package com.elevator.models;

public abstract class Button {

    private boolean isPressed;

    public Button() {
        this.isPressed = false;
    }

    public void press() {
        this.isPressed = true;
        handlePress();
    }

    public void reset() {
        this.isPressed = false;
    }

    public boolean isPressed() {
        return isPressed;
    }

    protected abstract void handlePress();
}
