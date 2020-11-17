package de.united.azubiware.Packets;

import de.united.azubiware.Packets.IPacket;

public interface IQueueStartPacket extends IPacket {

    int typeID = 1;

    @Override
    default int getType(){
       return typeID;
    };

}
