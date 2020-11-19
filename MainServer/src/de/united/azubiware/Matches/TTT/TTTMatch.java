package de.united.azubiware.Matches.TTT;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Matches.AMatch;
import de.united.azubiware.Matches.MatchUser;
import de.united.azubiware.Packets.ErrorResponsePacket;
import de.united.azubiware.Packets.TTTNextTurnPacket;
import de.united.azubiware.User.IUser;

public class TTTMatch extends AMatch {

    private final static int MATCH_TYPE = 1;
    private final TicTacToe tttGame;

    private boolean matchStarted = false;

    public TTTMatch(int port, IUser u1, IUser u2) {
        this(port, new TTTPacketHandler(), u1, u2);
    }

    private TTTMatch(int port, TTTPacketHandler handler, IUser u1, IUser u2){
        super(MATCH_TYPE, port, handler, u1, u2);
        handler.setMatch(this);

        tttGame = new TicTacToe();
    }


    @Override
    protected void onAllUserConnected() {
        matchStarted = true;
        sendNextTurnPackets();
    }

    public void doPlayerTurn(MatchUser user, int fieldX, int fieldY) {
        try{
            tttGame.setField(user.getPlayerIndex(), fieldX, fieldY);
        } catch (TicTacToe.IllegalTurnException e){
            user.send(new ErrorResponsePacket("You are bad at this game :c \n"+e.getMessage()));
            return;
        }

        if(!checkMatchOver())
            sendNextTurnPackets();
    }

    private boolean checkMatchOver(){
        int playerWon = tttGame.checkPlayerWin();
        if(playerWon != 0){
            onMatchOver();
            return true;
        }
        return false;
    }

    private void sendNextTurnPackets(){
        int nextPlayer = tttGame.getNextPlayer();
        for(IUser u : getUserList()){
            MatchUser mu = (MatchUser)u;
            mu.send(new TTTNextTurnPacket(mu.getPlayerIndex() == nextPlayer));
        }
    }

    public boolean isMatchStarted() {
        return matchStarted;
    }
}