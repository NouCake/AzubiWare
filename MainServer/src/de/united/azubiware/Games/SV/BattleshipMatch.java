package de.united.azubiware.Games.SV;

import de.united.azubiware.Games.VG.VGNextTurnPacket;
import de.united.azubiware.Matches.AMatch;
import de.united.azubiware.Matches.MatchUser;
import de.united.azubiware.Packets.MatchOverPacket;
import de.united.azubiware.User.IUser;

public class BattleshipMatch extends AMatch {

    private final static int MATCH_TYPE = 5;
    private Battleship game;

    public BattleshipMatch( int port, IUser... userlist) {
        super(MATCH_TYPE, port, userlist);
        game = new Battleship();
    }

    private void sendNextTurnPackets(){
        //TODO: get nextPlayer
        int nextPlayer = 1;
        for(IUser u : getUserList()){
            MatchUser mu = (MatchUser)u;
            mu.send(new BattleshipNextTurnPacket(mu.getPlayerIndex() == nextPlayer));
        }
    }



    @Override
    protected void onAllUserConnected() {
        sendNextTurnPackets();
    }

    public void playerTurn(int playerIndex, int cellX, int cellY){
        MatchUser user = getPlayerFromIndex(playerIndex);
        try {
            boolean hit = game.hitField(playerIndex, cellX, cellY);
            user.send(new BattleshipTurnPacket(cellX, cellY, hit, true));
            getOtherPlayer(playerIndex).send(new BattleshipTurnPacket(cellX, cellY, hit, false));
        } catch(Exception e){
            user.send(new BattleshipIllegalTurnPacket(e.getMessage()));
        }

    }

    private boolean checkWin(){
        return false;
    }

    private void startNextTurn(){
        //TODO: check win
        if(checkWin()){
            onMatchOver(MatchOverPacket.REASONS.GAME_DONE.ordinal());
            return;
        }
        sendNextTurnPackets();
    }

    private MatchUser getOtherPlayer(int playerIndex){
        return getPlayerFromIndex((playerIndex%2) + 1);
    }

}
