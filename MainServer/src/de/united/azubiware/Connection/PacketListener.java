package de.united.azubiware.Connection;

import de.united.azubiware.Packets.Handler.IPacketHandler;
import de.united.azubiware.Packets.IPacket;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PacketListener implements IConnectionListener{

    private IConnectionListener listener;
    private final List<IPacketHandler> handlers;

    public PacketListener(IPacketHandler ...handlers) {
        this.handlers = new LinkedList<>();
        Collections.addAll(this.handlers, handlers);
    }

    public PacketListener(IConnectionListener cListener, IPacketHandler ...handlers){
        this(handlers);
        this.listener = cListener;
    }

    public void addPacketHandler(IPacketHandler ...handler){
        Collections.addAll(this.handlers, handler);
    }

    @Override
    public void onMessage(IConnection connection, IPacket packet) {
        if(this.listener != null) listener.onMessage(connection, packet);
        for(IPacketHandler handler : handlers){
            handler.onPacket(connection, packet);
        }
    }

    @Override
    public void onConnected(IConnection connection) {
        if(this.listener != null) listener.onConnected(connection);
    }

    @Override
    public void onClosed(IConnection connection){
        if(this.listener != null) listener.onClosed(connection);
    }
}
