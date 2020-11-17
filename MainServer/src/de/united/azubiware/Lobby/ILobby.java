package de.united.azubiware.Lobby;

import de.united.azubiware.Packets.Handler.IPacketHandler;
import de.united.azubiware.User.IUser;

public interface ILobby {

    void startQueueing(IUser user);
    void stopQueueing(IUser user);

}
