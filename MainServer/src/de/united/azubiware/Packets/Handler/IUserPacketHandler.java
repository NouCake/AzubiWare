package de.united.azubiware.Packets.Handler;

import de.united.azubiware.IUser;
import de.united.azubiware.Packets.Handler.IPacket;

public interface IUserPacketHandler {

    void onPacket(IUser user, IPacket packet);

}
