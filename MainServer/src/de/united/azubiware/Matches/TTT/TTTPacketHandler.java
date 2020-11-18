package de.united.azubiware.Matches.TTT;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Packets.ErrorResponsePacket;
import de.united.azubiware.Packets.Handler.APacketHandler;
import de.united.azubiware.Packets.TTTPacket;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class TTTPacketHandler extends APacketHandler {

    private TTTMatch match;

    private TicTacToe ttt;

    public TTTPacketHandler(){
        ttt = new TicTacToe();
    }

    public void setMatch(TTTMatch match) {
        this.match = match;
    }

    public void onTTTPacket(IConnection connection, TTTPacket packet){
        int player = match.getPlayerFromConnection(connection);
        try{
            ttt.setField(player, packet.getFieldX(), packet.getFieldY());
        } catch (RuntimeException e){
            connection.send(new ErrorResponsePacket("Error with your Packet: " + e.getMessage()));
        }

        int won = ttt.checkPlayerWin();
        if(won != 0){
            System.out.println("Player "+won+" has won");
        }
    }

}
