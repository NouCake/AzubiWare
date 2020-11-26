package de.united.azubiware.connection.match.sv;

import de.united.azubiware.connection.client.Client;
import de.united.azubiware.connection.match.IMatchListener;
import de.united.azubiware.connection.match.MatchClient;
import de.united.azubiware.connection.match.pong.IPongListener;
import de.united.azubiware.connection.match.pong.PongClientPacketHandler;

import java.util.UUID;

public class BattleShipClient extends MatchClient {

    private IBattleshipListener listener;

    public BattleShipClient(Client client, String adress, UUID matchToken) {
        super(client, adress, matchToken);
    }

    @Override
    public void setMatchListener(IMatchListener matchListener) {
        super.setMatchListener(matchListener);
        if(!(matchListener instanceof IPongListener)){
            throw new RuntimeException("Your MatchListener is wrong :c");
        }
        this.listener = (IBattleshipListener)matchListener;
        addPacketHandler(new BattleshipClientPacketHandler(listener));
    }
}
