package de.united.azubiware.User;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Packets.LoginPacket;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class SimpleUserDatabase implements IUserDatabase {

    private List<IUser> users;

    public SimpleUserDatabase(){
        users = new LinkedList<>();
    }

    @Override
    public IUser getUserFromUUID(UUID id) {
        for(IUser user : users){
            if(user.getId().equals(id)) return user;
        }
        return null;
    }

    @Override
    public IUser getUserFromLoginPacket(LoginPacket packet, IConnection connection) {
        IUser user = new User(packet.getUsername(), connection);
        users.add(user);
        return user;
    }

}
