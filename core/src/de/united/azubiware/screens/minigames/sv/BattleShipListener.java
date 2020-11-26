package de.united.azubiware.screens.minigames.sv;

import de.united.azubiware.connection.match.sv.IBattleshipListener;

public class BattleShipListener implements IBattleshipListener {

    private final BattleshipScreen screen;

    public BattleShipListener(BattleshipScreen screen) {
        this.screen = screen;
    }


    @Override
    public void onEnemyTurn(int cellX, int cellY, boolean hit) {
        screen.enemyTurn(cellX, cellY, hit);
    }

    @Override
    public void onSetup(int[][] ships) {
        screen.setShips(ships);
    }

    @Override
    public void onNextTurn(boolean yourTurn) {
        screen.setTurn(yourTurn);
    }

    @Override
    public void onMatchReady() {

    }

    @Override
    public void onMatchOver(int reason) {

    }

}
