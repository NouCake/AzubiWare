package de.united.azubiware.Packets;

import de.united.azubiware.IUser;

public interface IUserPacketHandler {

    void onPacket(IUser user, IPacket packet);

}
