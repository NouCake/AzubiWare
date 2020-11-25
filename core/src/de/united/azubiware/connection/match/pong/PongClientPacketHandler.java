package de.united.azubiware.connection.match.pong;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Games.Pong.PongGameUpdatePacket;
import de.united.azubiware.Games.Pong.PongScoreUpdatePacket;
import de.united.azubiware.Packets.Handler.APacketHandler;

public class PongClientPacketHandler extends APacketHandler {

    private final IPongListener listener;

    public PongClientPacketHandler(IPongListener listener) {
        this.listener = listener;
    }

    public void onGameUpdatePacket(IConnection c, PongGameUpdatePacket packet){
        listener.onBallMoved(packet.getBallX(), packet.getBallY());
        listener.onEnemyMoved(packet.getEnemyX());
    }

    public void onScoreUpdatePacket(IConnection c, PongScoreUpdatePacket packet){
        listener.onScoreChanged(packet.getScoreP1(), packet.getScoreP2());
    }

}
