package de.united.azubiware;

import de.united.azubiware.Packets.Handler.IPacket;
import de.united.azubiware.Packets.Handler.IUserPacketHandler;

public class LobbyPacketHandler implements IUserPacketHandler {

    private ILobby lobby;

    public LobbyPacketHandler(ILobby lobby) {
        this.lobby = lobby;
    }

    private void onQueueStartPacket(IUser user){
        lobby.startQueueing(user);
    }

    @Override
    public void onPacket(IUser user, IPacket packet) {

    }
}
