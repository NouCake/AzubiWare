package de.united.azubiware.Games.Pong;

import de.united.azubiware.Matches.AMatch;
import de.united.azubiware.Packets.MatchOverPacket;
import de.united.azubiware.User.IUser;

public class PongMatch extends AMatch implements IPongScoreListener{

    public final static int MATCH_TYPE = 3;

    private final static int playerUpdateTime = 100;
    private final static float maxMatchTimeSec = 180;
    private final static int matchWaitTime = 3000;
    private final static int maxPoint = 5;
    private final Pong game;
    private boolean matchRunning = false;

    public PongMatch(int matchType, int port, IUser opponent) {
        super(matchType, port, opponent);
        this.game = new Pong();
    }

    public void onPlayerUpdate(int playerIndex, float x){
        if(playerIndex == 1) game.updatePlayer1(x);
        else if(playerIndex == 2) game.updatePlayer2(x);
    }

    private void sendScoreUpdate(int p1, int p2){
        sendAllUsers(new PongScoreUpdatePacket(p1, p2));
    }

    @Override
    protected void onMatchOver(int reason) {
        super.onMatchOver(reason);
        matchRunning = false;
    }

    @Override
    protected void onAllUserConnected() {
        new Thread(() -> {
            try{
                startMatchLoop();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }).start();
    }

    private void startMatchLoop() throws InterruptedException {
        Thread.sleep(matchWaitTime);
        matchRunning = true;
        final long startTime = System.currentTimeMillis();

        sendGameUpdate();
        float lastPlayerUpdate = System.currentTimeMillis();
        while(matchRunning){
            Thread.sleep((long)(Pong.updateTime*1000));
            game.step();

            long curTime = System.currentTimeMillis();
            //Check if MatchTime exeeds limit => draw
            if(curTime - startTime > maxMatchTimeSec * 1000){
                onMatchOver(MatchOverPacket.REASONS.GAME_DONE.ordinal());
            }

            //Update Player GamePackets
            if(curTime - lastPlayerUpdate > playerUpdateTime){
                lastPlayerUpdate = curTime;
                sendGameUpdate();
            }

        }
    }

    private void sendGameUpdate(){
        float ballX = (float)game.getRelativeBallPosition().x;
        float ballY = (float)game.getRelativeBallPosition().y;
        getPlayerFromIndex(1).send(new PongGameUpdatePacket(game.getRelativeP2Pos(), ballX, ballY));
        getPlayerFromIndex(2).send(new PongGameUpdatePacket(game.getRelativeP1Pos(), ballX, ballY));
    }

    @Override
    public void onScoreChanged(int p1, int p2) {
        if(p1 >= maxPoint || p2 >= maxPoint){
            setPlayerWon(p1 >= maxPoint ? 1 : 2);
            onMatchOver(MatchOverPacket.REASONS.GAME_DONE.ordinal());
            return;
        }

        sendScoreUpdate(p1, p2);
    }
}
