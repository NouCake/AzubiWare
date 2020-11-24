package de.united.azubiware.Connection;

import de.united.azubiware.Packets.Handler.IMessageHandler;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PacketListener implements IConnectionListener{

    private IConnectionListener listener;
    private final List<IMessageHandler> handlers;

    public PacketListener(IMessageHandler...handlers) {
        this.handlers = new LinkedList<>();
        Collections.addAll(this.handlers, handlers);
    }

    public PacketListener(IConnectionListener cListener, IMessageHandler...handlers){
        this(handlers);
        this.listener = cListener;
    }

    public void addPacketHandler(IMessageHandler...handler){
        Collections.addAll(this.handlers, handler);
    }

    @Override
    public void onMessage(IConnection connection, String message) {
        if(this.listener != null) listener.onMessage(connection, message);
        for(IMessageHandler handler : handlers){
            handler.onMessage(connection, message);
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

    @Override
    public void afterShutdown() {
        if(this.listener != null) listener.afterShutdown();
    }
}
