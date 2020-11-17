package de.united.azubiware.Packets;

public interface IQueueStopPacket extends IPacket{

    int typeID = 2;

    @Override
    default int getType(){
        return typeID;
    };

}
