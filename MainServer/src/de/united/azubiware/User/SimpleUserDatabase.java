package de.united.azubiware.User;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Packets.LoginPacket;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class SimpleUserDatabase implements IUserDatabase {

    private List<IUserConnection> users;

    public SimpleUserDatabase(){
        users = new LinkedList<>();
    }

    @Override
    public IUserConnection getUserFromUUID(UUID id) {
        for(IUserConnection user : users){
            if(user.getId().equals(id)) return user;
        }
        return null;
    }

    @Override
    public IUserConnection getUserFromLoginPacket(LoginPacket packet, IConnection connection) {
        IUserConnection user = new User(packet.getUsername(), connection);
        users.add(user);
        return user;
    }

}
