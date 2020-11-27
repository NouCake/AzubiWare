package de.united.azubiware.Games.SV;

import de.united.azubiware.Matches.AMatch;
import de.united.azubiware.Matches.MatchUser;
import de.united.azubiware.Packets.MatchOverPacket;
import de.united.azubiware.User.IUser;

public class BattleshipMatch extends AMatch {

    public final static int MATCH_TYPE = 5;
    private Battleship game;

    public BattleshipMatch( int port, IUser... userlist) {
        super(MATCH_TYPE, port, userlist);
        game = new Battleship();
        addPacketHandler(new BattleshipPacketHandler(this));
    }

    private void sendNextTurnPackets(){
        int nextPlayer = game.getNextPlayer();
        for(IUser u : getUserList()){
            MatchUser mu = (MatchUser)u;
            mu.send(new BattleshipNextTurnPacket(mu.getPlayerIndex() == nextPlayer));
        }
    }

    @Override
    protected void onAllUserConnected() {
        getPlayerFromIndex(1).send(new ShipSetupPacket(game.getSetup(1)));
        getPlayerFromIndex(2).send(new ShipSetupPacket(game.getSetup(2)));
        sendNextTurnPackets();
    }

    public void playerTurn(int playerIndex, int cellX, int cellY){
        MatchUser user = getPlayerFromIndex(playerIndex);
        MatchUser otherPlayer = getOtherPlayer(playerIndex);
        boolean hit;
        try {
            hit = game.doPlayerTurn(playerIndex, cellX, cellY);
        } catch (Battleship.IllegalTurnException e) {
            user.send(new BattleshipIllegalTurnPacket(e.getMessage()));
            return;
        }

        user.send(new BattleshipTurnPacket(cellX, cellY, hit, false));
        otherPlayer.send(new BattleshipTurnPacket(cellX, cellY, hit, true));

        startNextTurn();
    }

    private boolean checkWin(){
        int playerWon = game.checkPlayerWin();
        if(playerWon > 0) {
            setPlayerWon(playerWon);
            onMatchOver(MatchOverPacket.REASONS.GAME_DONE.ordinal());
            return true;
        }
        return false;
    }

    private void startNextTurn(){
        if(!checkWin())
            sendNextTurnPackets();
    }

    private MatchUser getOtherPlayer(int playerIndex){
        return getPlayerFromIndex((playerIndex%2) + 1);
    }

}
