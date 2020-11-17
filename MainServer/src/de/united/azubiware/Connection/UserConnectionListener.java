package de.united.azubiware.Connection;

import de.united.azubiware.Connection.WebSocket.IUserListener;
import de.united.azubiware.IUser;
import de.united.azubiware.Packets.Handler.IPacket;
import de.united.azubiware.Packets.LoginPacket;
import de.united.azubiware.User;

import java.util.List;

public class UserConnectionListener implements IConnectionListener{

    private List<IUser> connectedUsers;
    private final IUserListener listener;

    public UserConnectionListener(IUserListener listener) {
        this.listener = listener;
    }

    private IUser getUserFromConnection(IConnection connection){
        for(IUser user : connectedUsers){
            if(user.getConnection() == connection) return user;
        }
        return null;
    }
    private IUser tryLogin(IConnection connection, IPacket packet){
        if(!(packet instanceof LoginPacket)) return null;
        LoginPacket loginPacket = (LoginPacket) packet;

        IUser user = new User(loginPacket.getUsername(), connection);
        connectedUsers.add(user);
        listener.onLogin(user);
        return user;
    }

    @Override
    public void onMessage(IConnection connection, IPacket packet) {
        IUser user = getUserFromConnection(connection);

        if(user == null) { // => User is not logged in
            user = tryLogin(connection, packet);
            if(user == null) return;
        }

        listener.onPacket(user, packet);
    }
    @Override
    public void onConnected(IConnection connection) {
    }
    @Override
    public void onClosed(IConnection connection) {
        IUser user = getUserFromConnection(connection);
        if(user == null) return;

        connectedUsers.remove(user);
        listener.onLogout(user);
    }

}
