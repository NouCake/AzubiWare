package de.united.azubiware.connection.match.pong;

import de.united.azubiware.connection.client.Client;
import de.united.azubiware.connection.match.IMatchListener;
import de.united.azubiware.connection.match.ITTTListener;
import de.united.azubiware.connection.match.MatchClient;
import de.united.azubiware.connection.match.TTTClientPacketHandler;

import java.util.UUID;

public class PongClient extends MatchClient {

    private IPongListener listener;

    public PongClient(Client client, String adress, UUID matchToken) {
        super(client, adress, matchToken);
    }

    @Override
    public void setMatchListener(IMatchListener matchListener) {
        super.setMatchListener(matchListener);
        if(!(matchListener instanceof IPongListener)){
            throw new RuntimeException("Your MatchListener is wrong :c");
        }
        this.listener = (IPongListener)matchListener;
        addPacketHandler(new PongClientPacketHandler(listener));
    }

}
