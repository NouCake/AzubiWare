package de.united.azubiware.Connection;

import de.united.azubiware.Connection.WebSocket.IUserListener;
import de.united.azubiware.Packets.Handler.PacketParser;
import de.united.azubiware.User.IUserConnection;
import de.united.azubiware.User.IUserDatabase;
import de.united.azubiware.Packets.IPacket;
import de.united.azubiware.Packets.LoginPacket;

import java.util.LinkedList;
import java.util.List;

public class UserConnectionManager implements IConnectionListener{

    private List<IUserConnection> connectedUsers;
    private final IUserListener listener;
    private final IUserDatabase manager;

    private PacketParser parser;

    public UserConnectionManager(IUserListener listener, IUserDatabase manager) {
        this.listener = listener;
        this.manager = manager;

        connectedUsers = new LinkedList<>();

        parser = new PacketParser();
        parser.addPacketClass(LoginPacket.class);
    }

    public IUserConnection getUserFromConnection(IConnection connection){
        for(IUserConnection user : connectedUsers){
            if(user.getConnection() == connection) return user;
        }
        return null;
    }
    private IUserConnection tryLogin(IConnection connection, IPacket packet){
        if(!(packet instanceof LoginPacket)) return null;
        LoginPacket loginPacket = (LoginPacket) packet;

        IUserConnection user = manager.getUserFromLoginPacket(loginPacket, connection);
        if(user == null) {
            return null;
        }

        connectedUsers.add(user);
        listener.onLogin(user);
        return user;
    }

    @Override
    public void onMessage(IConnection connection, String message) {
        IUserConnection user = getUserFromConnection(connection);

        if(user == null) { // => User is not logged in
            IPacket packet = parser.createPacketFromJson(message);
            if(packet == null){
                System.out.println("Not a valid login Packet");
                return;
            }
            user = tryLogin(connection, packet);
            return;
        }

        listener.onMessage(user, message);
    }
    @Override
    public void onConnected(IConnection connection) {
    }
    @Override
    public void onClosed(IConnection connection) {
        IUserConnection user = getUserFromConnection(connection);
        if(user == null) return;

        connectedUsers.remove(user);
        listener.onLogout(user);
    }

    @Override
    public void afterShutdown() {

    }

}
