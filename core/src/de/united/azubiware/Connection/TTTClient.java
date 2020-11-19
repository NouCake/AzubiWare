package de.united.azubiware.Connection;

import de.united.azubiware.Packets.Handler.APacketHandler;
import de.united.azubiware.Packets.TTTNextTurnPacket;

import java.util.UUID;

public class TTTClient extends MatchClient {

    private ITTTListener listener;

    public TTTClient(Client client, String adress, UUID matchToken) {
        super(client, adress, matchToken);
        addPacketHandler(new TTTClientPacketHandler());
    }

    private class TTTClientPacketHandler extends APacketHandler {

        void onNextTurn(IConnection c, TTTNextTurnPacket packet){

        }
    }

}
