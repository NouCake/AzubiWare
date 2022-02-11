package ttt;

import match.Match;
import match.MatchPacketHandler;
import match.MatchPlayer;

public class TTTMatch extends Match implements MatchPacketHandler {

    private final TicTacToe tttGame;

    public TTTMatch(MatchPlayer[] players) {
        super(players);
        tttGame = new TicTacToe();
    }

    @Override
    public MatchPacketHandler getPacketHandler() {
        return this;
    }

    public void on(MatchPlayer player, TTTPacket packet){
        try{
            tttGame.setField(player.getIndex(), packet.getFieldX(), packet.getFieldY());
        } catch (TicTacToe.IllegalTurnException e){
            send(player, null /* new ErrorResponsePacket("You are bad at this game :c \n"+e.getMessage()) */);
            return;
        }

        int nextPlayer = tttGame.getNextPlayer();
        send(getPlayerByIndex(nextPlayer), packet);
        if(checkMatchOver()){
            int winnerIndex = tttGame.checkPlayerWin();
            onMatchOver(getPlayerByIndex(winnerIndex));
        } else {
            for(MatchPlayer p : players) send(p, new TTTNextPacket(p.getIndex() == nextPlayer));
        }
    }

    private boolean checkMatchOver(){
        int playerWon = tttGame.checkPlayerWin();
        return playerWon != -1 || tttGame.isMatchOver();
    }

}
