package de.united.azubiware.connection.match;

import de.united.azubiware.connection.client.Client;
import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Packets.Handler.APacketHandler;
import de.united.azubiware.Packets.TTTIllegalTurnPacket;
import de.united.azubiware.Packets.TTTNextTurnPacket;
import de.united.azubiware.Packets.TTTPacket;

import java.util.UUID;

public class TTTClient extends MatchClient {

    private ITTTListener listener;

    public TTTClient(Client client, String adress, UUID matchToken) {
        super(client, adress, matchToken);
        addPacketHandler(new TTTClientPacketHandler());

    }

    @Override
    public void setMatchListener(IMatchListener matchListener) {
        super.setMatchListener(matchListener);
        if(!(matchListener instanceof ITTTListener)){
            throw new RuntimeException("Your MatchListener is wrong :c");
        }
        this.listener = (ITTTListener)matchListener;
    }

    private class TTTClientPacketHandler extends APacketHandler {

        void onNextTurn(IConnection c, TTTNextTurnPacket packet){
            listener.onNextTurn(packet.isYourTurn());
        }

        void onIllegalTurn(IConnection c, TTTIllegalTurnPacket packet){
            System.out.println("You did some bad Move :c");
            System.out.println(packet.getMessage());
            listener.onInvalidTurn();
        }

        void onEnemyTurn(IConnection c, TTTPacket packet){
            listener.onEnemyTurn(packet.getFieldX(), packet.getFieldY());
        }

    }

}
