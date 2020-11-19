package de.united.azubiware.screens.minigames;

import com.badlogic.gdx.math.Vector2;

public class TicTacToePostition {

    private Vector2 min;
    private Vector2 max;
    private Vector2 center;

    private int state;

    public TicTacToePostition(Vector2 min, Vector2 max, Vector2 center){
        this.min = min;
        this.max = max;
        this.center = center;
        state = 0;
    }

    public Vector2 getMax() {
        return max;
    }

    public Vector2 getMin() {
        return min;
    }

    public Vector2 getCenter() {
        return center;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
