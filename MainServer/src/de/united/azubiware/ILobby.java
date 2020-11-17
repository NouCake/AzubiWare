package de.united.azubiware;


import de.united.azubiware.Packets.Handler.IUserPacketHandler;

public interface ILobby extends IUserPacketHandler {

    void startQueueing(IUser user);
    void stopQueueing(IUser user);

}
