package de.united.azubiware.Connection;

import de.united.azubiware.Packets.LoginPacket;
import de.united.azubiware.Packets.QueueStartPacket;
import de.united.azubiware.Packets.QueueStopPacket;

import java.net.URI;

public class Client {

    private IConnection connection;

    public Client(){
        IConnectionManager client = new WebSocketClient(URI.create("ws://two.noucake.de:12000"));
        client.setConnectionListener(new PacketListener(new ClientPacketHandler()){
            @Override
            public void onConnected(IConnection con) {
                super.onConnected(con);
                System.out.println("Conncted, sending Login!");
                connection = con;
            }
        });
        client.start();
    }

    public void sendLogin(String username){
        if(isConnected()){
            System.out.println("Client is not connected");
        }
        connection.send(new LoginPacket(username));
    }

    public void sendStartQueue(int matchType){
        connection.send(new QueueStartPacket(0));
    }

    public void sendStopQueue(){
        connection.send(new QueueStopPacket());
    }

    public boolean isConnected(){
        return connection != null;
    }

}
