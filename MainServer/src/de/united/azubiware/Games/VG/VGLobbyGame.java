package de.united.azubiware.Games.VG;

import de.united.azubiware.Connection.PortManager;
import de.united.azubiware.Games.TTT.TTTMatch;
import de.united.azubiware.Lobby.ALobbyGame;
import de.united.azubiware.Matches.IMatch;
import de.united.azubiware.Matches.IMatchListener;
import de.united.azubiware.User.IUser;

public class VGLobbyGame extends ALobbyGame {
    @Override
    public int getMatchType() {
        return VGMatch.MATCH_TYPE;
    }

    @Override
    public IMatch startMatch(IUser... users) {
        int port;

        try {
            port = PortManager.ports.getFreePort();
        } catch (PortManager.NoFreePortException e) {
            System.out.println("Couldn't start Match: No Free Port");
            return null;
        }

        IMatch match = new VGMatch(port, users[0], users[1]);
        addMatchListener(match, port);
        return match;
    }

    @Override
    public boolean isUserCounterValid(int userCount) {
        return userCount >= 2;
    }

    private void addMatchListener(IMatch match, int port){
        match.setMatchListener(new IMatchListener() {
            @Override
            public void onMatchFinished() {
            }

            @Override
            public void onMatchTimedOut() {
            }

            @Override
            public void onMatchClose(){
                PortManager.ports.freePort(port);
            }
        });
    }
}
