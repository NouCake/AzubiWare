package de.united.azubiware.Matches.VierGewinnt;

import de.united.azubiware.Packets.Handler.APacketHandler;

public class VGPacketHandler extends APacketHandler {

    private final VGMatch match;

    public VGPacketHandler(VGMatch match) {
        this.match = match;
    }


}
