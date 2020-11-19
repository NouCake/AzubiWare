package de.united.azubiware.Connection;

import de.united.azubiware.Packets.Handler.APacketHandler;
import de.united.azubiware.Packets.IPacket;
import de.united.azubiware.Packets.WelcomePacket;

public class ClientPacketHandler extends APacketHandler {

    public void onWelcomePacket(IConnection c, WelcomePacket packet){
        System.out.println("Got Welcomed!");
    }

}
