package de.united.azubiware.Connection;

import de.united.azubiware.Packets.Handler.IPacketHandler;
import de.united.azubiware.Packets.MatchLoginPacket;

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

        matchServer = new WebSocketClient(URI.create(adress));
        matchServer.setConnectionListener(listener);
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
}
