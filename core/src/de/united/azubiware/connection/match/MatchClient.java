package de.united.azubiware.connection.match;

import de.united.azubiware.connection.client.Client;
import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Connection.IConnectionManager;
import de.united.azubiware.Connection.PacketListener;
import de.united.azubiware.Packets.Handler.IPacketHandler;
import de.united.azubiware.Packets.IPacket;
import de.united.azubiware.Packets.MatchLoginPacket;
import de.united.azubiware.connection.WebSocketClient;

import java.net.URI;
import java.util.UUID;

public class MatchClient{

    private IConnection connection;
    private final Client client;
    private final IConnectionManager matchServer;
    private final PacketListener listener;

    private IMatchListener matchListener;
    private final UUID matchToken;

    public MatchClient(Client client, String adress, UUID matchToken) {
        this.client = client;
        this.matchToken = matchToken;

        listener = new PacketListener(new MatchPacketHandler(this)){
            @Override
            public void onConnected(IConnection c) {
                super.onConnected(c);
                connection = c;
                onMatchConnected();
            }
        };

        System.out.println("Connection to Adress: " + adress);
        matchServer = new WebSocketClient(URI.create(adress));
        matchServer.setConnectionListener(listener);
    }

    public void sendPacket(IPacket packet){
        if(connection == null) return;
        connection.send(packet);
    }

    public void start(){
        matchServer.start();
    }

    private void onMatchConnected(){
        connection.send(new MatchLoginPacket(matchToken.toString()));
    }

    public void setMatchListener(IMatchListener matchListener){
        this.matchListener = matchListener;
    }

    protected void addPacketHandler(IPacketHandler handler){
        this.listener.addPacketHandler(handler);
    }

    public void onReady() {
        if(matchListener == null) return;
        matchListener.onMatchReady();
    }

    public IMatchListener getMatchListener() {
        return matchListener;
    }
}
