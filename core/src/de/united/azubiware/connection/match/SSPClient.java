package de.united.azubiware.connection.match;

import de.united.azubiware.connection.client.Client;

import java.util.UUID;

public class SSPClient extends MatchClient{

    private ISSPListener listener;

    public SSPClient(Client client, String adress, UUID matchToken) {
        super(client, adress, matchToken);
    }

    @Override
    public void setMatchListener(IMatchListener matchListener) {
        super.setMatchListener(matchListener);
        if(!(matchListener instanceof ISSPListener)){
            throw new RuntimeException("Your MatchListener is wrong :c");
        }
        this.listener = (ISSPListener)matchListener;
        addPacketHandler(new SSPClientPacketHandler(listener));
    }
}
