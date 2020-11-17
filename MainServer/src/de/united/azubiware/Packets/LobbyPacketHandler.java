package de.united.azubiware.Packets;

import de.united.azubiware.ILobby;
import de.united.azubiware.IUser;

public class LobbyPacketHandler implements IUserPacketHandler {

    private ILobby lobby;

    public LobbyPacketHandler(ILobby lobby) {
        this.lobby = lobby;
    }

    private void onQueueStartPacket(IUser user, IQueueStartPacket packet){
        lobby.startQueueing(user, packet);
    }

    @Override
    public void onPacket(IUser user, IPacket packet) {

    }
}
