package de.united.azubiware.connection.client;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Packets.ErrorResponsePacket;
import de.united.azubiware.Packets.Handler.APacketHandler;
import de.united.azubiware.Packets.Handler.QueueResponsePacket;
import de.united.azubiware.Packets.MatchConnectionInfoPacket;
import de.united.azubiware.Packets.WelcomePacket;
import de.united.azubiware.User.SimpleUser;

public class ClientPacketHandler extends APacketHandler {

    private final Client client;

    public ClientPacketHandler(Client client) {
        this.client = client;
    }

    public void onErrorPacket(IConnection c, ErrorResponsePacket packet){
        client.doError(packet.getMessage());
    }

    public void onWelcomePacket(IConnection c, WelcomePacket packet){
        client.doWelcome(new SimpleUser(packet.getUuid(), packet.getUsername()));
    }

    public void onMatchInfoPacket(IConnection c, MatchConnectionInfoPacket packet){
        client.startMatch(packet.getMatchtype(), packet.getAdress(), packet.getMatchToken(), packet.getOponents());
    }

    public void onQueueUpdate(IConnection c, QueueResponsePacket packet){
        client.updateQueue(packet.getMatchType(), packet.getQueueLength());
    }

}
