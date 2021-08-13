package de.united.azubiware.screens.minigames.sv;

import de.united.azubiware.Packets.MatchOverPacket;
import de.united.azubiware.connection.match.sv.IBattleshipListener;
import de.united.azubiware.screens.minigames.WaitingScreen;

public class BattleShipListener implements IBattleshipListener {

    private final BattleshipScreen screen;
    private final WaitingScreen waitingScreen;

    public BattleShipListener(BattleshipScreen screen, WaitingScreen waitingScreen) {
        this.screen = screen;
        this.waitingScreen = waitingScreen;
    }

    @Override
    public void onOwnturn(int cellX, int cellY, boolean hit) {
        screen.onOwnTurn(cellX, cellY, hit);
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
        waitingScreen.setSwitchToMatch(screen);
    }

    @Override
    public void onMatchOver(int reason) {
        if(reason == MatchOverPacket.REASONS.DRAW.ordinal()){
            screen.getResultOverlay().setResult(0);
        }else if (MatchOverPacket.REASONS.ABORTED.ordinal() == reason || MatchOverPacket.REASONS.YOU_WON.ordinal() == reason) {
            screen.getResultOverlay().setResult(1);
        } else {
            screen.getResultOverlay().setResult(-1);
        }
    }

}
