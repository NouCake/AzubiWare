package de.united.azubiware.screens.minigames.ttt;

import com.badlogic.gdx.math.Vector2;

public class TicTacToePostition {

    private Vector2 min;
    private Vector2 max;
    private Vector2 center;

    private int posX;
    private int posY;

    private int state;

    public TicTacToePostition(Vector2 min, Vector2 max, Vector2 center, int posX, int posY){
        this.min = min;
        this.max = max;
        this.center = center;

        this.posX = posX;
        this.posY = posY;

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

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
