package de.united.azubiware.connection.client;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Connection.IConnectionManager;
import de.united.azubiware.Connection.PacketListener;
import de.united.azubiware.Matches.TTT.TTTMatch;
import de.united.azubiware.Packets.*;
import de.united.azubiware.User.IUser;
import de.united.azubiware.connection.WebSocketClient;
import de.united.azubiware.connection.match.IMatchListener;
import de.united.azubiware.connection.match.MatchClient;
import de.united.azubiware.connection.match.TTTClient;

import java.net.URI;
import java.util.UUID;

public class Client implements IClient {

    private IConnection connection;
    private IClientListener listener;
    private IConnectionManager client;

    private MatchClient currentMatchClient;

    public Client(){
        client = new WebSocketClient(URI.create("ws://two.noucake.de:12000"));
        //client = new WebSocketClient(URI.create("ws://localhost:12000"));
        client.setConnectionListener(new PacketListener(new ClientPacketHandler(this)){
            @Override
            public void onConnected(IConnection con) {
                super.onConnected(con);
                connection = con;
                if(listener != null) listener.onConnected();
            }
        });
    }

    public void doError(String message){
        System.out.println("Error Packet: \n"+message);
        if(listener == null) return;
            listener.onError(message);
    }

    public void doWelcome(IUser user){
        if(listener == null) return;
        listener.onWelcome(user);
    }

    public void startMatch(int matchType, String adress, UUID matchToken, IUser[] opponents){
        if(listener == null) return;
        if(matchType == TTTMatch.MATCH_TYPE){
            currentMatchClient = new TTTClient(this, adress, matchToken);
        }

        listener.onMatchFound(matchType, opponents);
    }

    public void doMatchOver(){
        if(currentMatchClient != null){
            currentMatchClient.stop();
            currentMatchClient = null;
        }
    }

    @Override
    public void setMatchListener(IMatchListener listener) {
        if(currentMatchClient == null){
            System.out.println("Match is not set!");
            return;
        }

        currentMatchClient.setMatchListener(listener);
        currentMatchClient.start();
    }

    @Override
    public IMatchListener getMatchListener() {
        return currentMatchClient.getMatchListener();
    }

    @Override
    public void sendMatchPacket(IPacket packet) {
        if(currentMatchClient == null){
            System.out.println("Match is not set!");
            return;
        }
        currentMatchClient.sendPacket(packet);
    }

    @Override
    public void sendMatchLeave() {

    }

    public void updateQueue(int matchType, int queueLength){
        if(listener == null) return;
        listener.onQueueUpdate(matchType, queueLength);
    }

    @Override
    public void start() {
        client.start();
    }

    @Override
    public void setClientLister(de.united.azubiware.connection.client.IClientListener lister) {
        this.listener = lister;
    }

    @Override
    public void stop() {
        client.stop();
        connection = null;
        if(currentMatchClient != null) currentMatchClient.stop();
    }

    @Override
    public void sendLogin(String username) {
        if(!isConnected()){
            System.out.println("Client is not connected");
            return;
        }
        connection.send(new LoginPacket(username));
    }

    @Override
    public void sendQueueStart(int matchType) {
        if(!isConnected()){
            System.out.println("Client is not connected");
            return;
        }
        connection.send(new QueueStartPacket(matchType));
    }

    @Override
    public void sendQueueStop() {
        if(!isConnected()){
            System.out.println("Client is not connected");
            return;
        }
        connection.send(new QueueStopPacket());
    }

    @Override
    public void sendQueuePoll(int matchType) {
        if(!isConnected()){
            System.out.println("Client is not connected");
            return;
        }
        connection.send(new QueuePollPacket(matchType));
    }


    @Override
    public boolean isConnected(){
        return connection != null;
    }

}
