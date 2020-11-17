package de.united.azubiware;

import de.united.azubiware.Packets.IQueueStartPacket;
import de.united.azubiware.Packets.IUserPacketHandler;

public interface ILobby extends IUserPacketHandler {

    void startQueueing(IUser user, IQueueStartPacket packet);
    void stopQueueing(IUser user);

}
