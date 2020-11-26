package de.united.azubiware.Games.SSP;

import de.united.azubiware.Games.SSP.Packets.SSPRoundOverPacket;
import de.united.azubiware.Games.SSP.Packets.SSPRoundStartPacket;
import de.united.azubiware.Games.SSP.logic.SSP;
import de.united.azubiware.Games.SSP.logic.SSPFigureType;
import de.united.azubiware.Matches.AMatch;
import de.united.azubiware.Matches.MatchUser;
import de.united.azubiware.Packets.MatchOverPacket;
import de.united.azubiware.User.IUser;

public class SSPMatch extends AMatch {

    public final static int MATCH_TYPE = 4;
    private SSP ssp;
    private SSPRoundTimer sspRoundTimer;

    private boolean matchStarted = false;
    private int round = 0;

    public SSPMatch(int port, IUser userOne, IUser userTwo) {
        super(MATCH_TYPE, port, userOne, userTwo);
        addPacketHandler(new SSPPacketHandler(this));
        ssp = new SSP();
        sspRoundTimer = new SSPRoundTimer(this);
    }

    public boolean isMatchStarted() {
        return matchStarted;
    }

    @Override
    protected void onAllUserConnected() {
        matchStarted = true;
        startNewRound();
    }

    public void doPlayerPick(MatchUser matchUser, int pickType){
        if(SSPFigureType.findByOrdinal(pickType) == null)
            return;
        try {
            ssp.setPick(matchUser.getPlayerIndex(), SSPFigureType.findByOrdinal(pickType));
        } catch (SSP.IllegalPickException e) {
            e.printStackTrace();
        }
    }

    public void startNewRound(){
        if(!checkMatchOver()) {
            round++;
            sendRoundStart();
            sspRoundTimer.startRoundTimer();
        }else{
            int result = ssp.getMatchResult();
            if(result != 0) {
                setPlayerWon(result);
                onMatchOver(MatchOverPacket.REASONS.GAME_DONE.ordinal());
            }else{
                onMatchOver(MatchOverPacket.REASONS.GAME_DONE.ordinal());
            }
        }
    }

    private boolean checkMatchOver(){
        return round >= 3;
    }

    public void sendRoundOver(){
        int result = ssp.getRoundResult();
        for(IUser user : getUserList()){
            MatchUser matchUser = (MatchUser)user;
            matchUser.send(new SSPRoundOverPacket(ssp.getPick(matchUser.getPlayerIndex()).ordinal(), result));
        }
        sspRoundTimer.startWaitTimer();
    }

    private void sendRoundStart(){
        for(IUser user : getUserList()){
            MatchUser matchUser = (MatchUser)user;
            matchUser.send(new SSPRoundStartPacket(round));
        }
    }

}
