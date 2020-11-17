package de.united.azubiware.Packets.Handler;


import de.united.azubiware.Packets.IPacket;
import de.united.azubiware.User.IUser;

public interface IUserPacketHandler {

    void onPacket(IUser user, IPacket packet);

}
