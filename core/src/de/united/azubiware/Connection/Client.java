package de.united.azubiware.Connection;

import de.united.azubiware.Packets.LoginPacket;
import de.united.azubiware.Packets.QueuePollPacket;
import de.united.azubiware.Packets.QueueStartPacket;
import de.united.azubiware.Packets.QueueStopPacket;
import de.united.azubiware.User.IUser;

import java.net.URI;
import java.util.UUID;

public class Client implements IClient{

    private IConnection connection;
    private IClientListener listener;
    private IConnectionManager client;

    public Client(){
        client = new WebSocketClient(URI.create("ws://two.noucake.de:12000"));
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
        listener.onMatchStart(matchType, opponents);
        //Start Match Client
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
    public void setClientLister(IClientListener lister) {
        this.listener = lister;
    }

    @Override
    public void stop() {
        client.stop();
        connection = null;
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
