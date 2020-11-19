package de.united.azubiware.Connection.Match;

import de.united.azubiware.Connection.Client.Client;

import java.util.UUID;

public class TTTClient extends MatchClient {

    private ITTTListener listener;

    public TTTClient(Client client, String adress, UUID matchToken) {
        super(client, adress, matchToken);

    }

    @Override
    public void setMatchListener(IMatchListener matchListener) {
        super.setMatchListener(matchListener);
        if(!(matchListener instanceof ITTTListener)){
            throw new RuntimeException("Your MatchListener is wrong :c");
        }
        this.listener = (ITTTListener)matchListener;
        addPacketHandler(new TTTClientPacketHandler(listener));
    }

}
