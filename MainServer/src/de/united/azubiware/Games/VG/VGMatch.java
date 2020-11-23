package de.united.azubiware.Games.VG;

import de.united.azubiware.Matches.AMatch;
import de.united.azubiware.Matches.MatchUser;
import de.united.azubiware.Packets.ErrorResponsePacket;
import de.united.azubiware.Packets.MatchOverPacket;
import de.united.azubiware.User.IUser;


public class VGMatch extends AMatch {

    public final static int MATCH_TYPE = 2;
    private final VierGewinnt vgGame;

    private boolean matchStarted = false;

    public VGMatch(int port, IUser u1, IUser u2) {
        super(MATCH_TYPE, port, u1, u2);
        addPacketHandler(new VGPacketHandler(this));
        vgGame = new VierGewinnt();
    }

    @Override
    protected void onAllUserConnected() {
        matchStarted = true;
        sendNextTurnPackets();
    }

    public void doPlayerTurn(MatchUser user, int fieldX) {
        try{
            vgGame.setField(user.getPlayerIndex(), fieldX);
        } catch (VierGewinnt.IllegalTurnException e){
            user.send(new ErrorResponsePacket("You are bad at this game :c \n"+e.getMessage()));
            return;
        }

        int otherPlayer = vgGame.getNextPlayer();
        MatchUser otherUser = getPlayerFromIndex(otherPlayer);
        if(otherUser == null) throw new RuntimeException("Something bad happened :c");
        otherUser.send(new VGPacket(fieldX));


        if(!checkMatchOver())
            sendNextTurnPackets();
    }

    private boolean checkMatchOver(){
        int playerWon = vgGame.getWinner();
        if(playerWon != 0){
            setPlayerWon(playerWon);
            onMatchOver(MatchOverPacket.REASONS.GAME_DONE.ordinal());
            return true;
        }
        return false;
    }

    private void sendNextTurnPackets(){
        int nextPlayer = vgGame.getNextPlayer();
        for(IUser u : getUserList()){
            MatchUser mu = (MatchUser)u;
            mu.send(new VGNextTurnPacket(mu.getPlayerIndex() == nextPlayer));
        }
    }

    public boolean isMatchStarted() {
        return matchStarted;
    }
}
