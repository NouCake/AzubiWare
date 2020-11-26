package de.united.azubiware.Games.SSP;

public class SSPRoundTimer {

    private long roundTimeout = 6000;
    private long waitTimeout = 2000;
    private SSPMatch sspMatch;

    private Thread roundThread;
    private Thread waitThread;

    public SSPRoundTimer(SSPMatch sspMatch){
        this.sspMatch = sspMatch;
        init();
    }

    private void init(){
        roundThread = new Thread(){

            @Override
            public void run() {
                try {
                    roundThread.sleep(roundTimeout);
                } catch (InterruptedException ignored) { }
                sspMatch.sendRoundOver();
            }
        };

        waitThread = new Thread(){

            @Override
            public void run() {
                try {
                    waitThread.sleep(waitTimeout);
                } catch (InterruptedException ignored) { }
                sspMatch.startNewRound();
            }
        };
    }

    public void startRoundTimer(){
        roundThread.start();
    }

    public void startWaitTimer(){
        waitThread.start();
    }

}
