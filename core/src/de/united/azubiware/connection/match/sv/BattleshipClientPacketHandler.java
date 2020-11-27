package de.united.azubiware.connection.match.sv;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Games.SV.BattleshipNextTurnPacket;
import de.united.azubiware.Games.SV.BattleshipTurnPacket;
import de.united.azubiware.Games.SV.ShipSetupPacket;
import de.united.azubiware.Packets.Handler.APacketHandler;

public class BattleshipClientPacketHandler extends APacketHandler {

    private final IBattleshipListener listener;

    public BattleshipClientPacketHandler(IBattleshipListener listener) {
        this.listener = listener;
    }

    public void onShipSetupPacket(IConnection c, ShipSetupPacket packet){
        listener.onSetup(packet.getShips());
    }

    public void onEnemyTurn(IConnection c, BattleshipTurnPacket packet){
        if(packet.isYou()){
            listener.onOwnturn(packet.getCellX(), packet.getCellY(), packet.isHit());
        } else {
            listener.onEnemyTurn(packet.getCellX(), packet.getCellY(), packet.isHit());
        }
    }

    public void onNextTurn(IConnection c, BattleshipNextTurnPacket packet){
        listener.onNextTurn(packet.isYourTurn());
    }

}
