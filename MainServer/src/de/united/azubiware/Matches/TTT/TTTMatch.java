package de.united.azubiware.Matches.TTT;

import de.united.azubiware.Matches.AMatch;
import de.united.azubiware.Matches.MatchUser;
import de.united.azubiware.Packets.ErrorResponsePacket;
import de.united.azubiware.Packets.MatchOverPacket;
import de.united.azubiware.Packets.TTTNextTurnPacket;
import de.united.azubiware.Packets.TTTPacket;
import de.united.azubiware.User.IUser;

public class TTTMatch extends AMatch {

    public final static int MATCH_TYPE = 1;
    private final TicTacToe tttGame;

    private boolean matchStarted = false;

    public TTTMatch(int port, IUser u1, IUser u2) {
        super(MATCH_TYPE, port, u1, u2);
        addPacketHandler(new TTTPacketHandler(this));
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

        int otherPlayer = tttGame.getNextPlayer();

        MatchUser otherUser = getPlayerFromIndex(otherPlayer);
        //System.out.println("Current Player: " + user.getPlayerIndex() + " | " + otherUser.getPlayerIndex());
        if(otherUser == null) throw new RuntimeException("Something bad happend :c");
        otherUser.send(new TTTPacket(fieldX, fieldY));


        if(!checkMatchOver())
            sendNextTurnPackets();
    }

    private boolean checkMatchOver(){
        int playerWon = tttGame.checkPlayerWin();
        if(playerWon != 0){
            setPlayerWon(playerWon);
            onMatchOver(MatchOverPacket.REASONS.GAME_DONE.ordinal());
            return true;
        }
        return tttGame.isMatchOver();
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
