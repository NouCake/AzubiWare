package de.united.azubiware.connection.match;

import de.united.azubiware.connection.client.Client;

import java.util.UUID;

public class VGClient extends MatchClient {

    private IVGListener listener;

    public VGClient(Client client, String adress, UUID matchToken) {
        super(client, adress, matchToken);
    }

    @Override
    public void setMatchListener(IMatchListener matchListener) {
        super.setMatchListener(matchListener);
        if(!(matchListener instanceof IVGListener)){
            throw new RuntimeException("Your MatchListener is wrong :c");
        }
        this.listener = (IVGListener) matchListener;
        addPacketHandler(new VGClientPacketHandler(listener));
    }
}
