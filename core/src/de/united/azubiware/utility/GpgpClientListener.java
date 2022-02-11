package de.united.azubiware.utility;

import de.golfgl.gdxgamesvcs.IGameServiceListener;

public class GpgpClientListener implements IGameServiceListener {


    @Override
    public void gsOnSessionActive() {
        System.out.println("[GS] Session active");
    }

    @Override
    public void gsOnSessionInactive() {
        System.out.println("[GS] Session inactive");
    }

    @Override
    public void gsShowErrorToUser(GsErrorType et, String msg, Throwable t) {
        System.out.println(et.toString() + "|" + msg);
    }
}
