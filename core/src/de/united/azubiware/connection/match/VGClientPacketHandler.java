package de.united.azubiware.connection.match;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Packets.*;
import de.united.azubiware.Packets.Handler.APacketHandler;

public class VGClientPacketHandler extends APacketHandler {

    private final IVGListener listener;

    public VGClientPacketHandler(IVGListener listener) {
        this.listener = listener;
    }


    public void onNextTurn(IConnection c, VGNextTurnPacket packet){
        listener.onNextTurn(packet.isYourTurn());
    }

//    public void onIllegalTurn(IConnection c,  packet){
//        System.out.println("You did some bad Move :c");
//        System.out.println(packet.getMessage());
//        listener.onInvalidTurn();
//    }

    public void onEnemyTurn(IConnection c, VGPacket packet){
        listener.onEnemyTurn(packet.getFieldX());
    }

}
