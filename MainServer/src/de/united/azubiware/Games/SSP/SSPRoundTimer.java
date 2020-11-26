package de.united.azubiware.Games.SSP;

public class SSPRoundTimer {

    private long roundTimeout = 6000;
    private long waitTimeout = 2000;
    private SSPMatch sspMatch;
    private Thread thread;

    public SSPRoundTimer(SSPMatch sspMatch){
        this.sspMatch = sspMatch;
    }

    public void startRoundTimer(){
        thread = new Thread(){

            @Override
            public void run() {
                try {
                    thread.sleep(roundTimeout);
                } catch (InterruptedException ignored) { }
                sspMatch.sendRoundOver();
            }
        };
        thread.start();
    }

    public void startWaitTimer(){
        thread = new Thread(){

            @Override
            public void run() {
                try {
                    thread.sleep(waitTimeout);
                } catch (InterruptedException ignored) { }
                sspMatch.startNewRound();
            }
        };
        thread.start();
    }

}
