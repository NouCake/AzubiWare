package de.united.azubiware.Lobby;


import de.united.azubiware.Packets.Handler.IUserPacketHandler;
import de.united.azubiware.User.IUser;

public interface ILobby extends IUserPacketHandler {

    void startQueueing(IUser user);
    void stopQueueing(IUser user);

}
