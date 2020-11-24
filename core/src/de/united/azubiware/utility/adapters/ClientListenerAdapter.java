package de.united.azubiware.utility.adapters;

import de.united.azubiware.User.IUser;
import de.united.azubiware.connection.client.IClientListener;

public class ClientListenerAdapter implements IClientListener {

    @Override
    public void onConnected() {

    }

    @Override
    public void onClosed() {

    }

    @Override
    public void onError(String messsage) {

    }

    @Override
    public void onWelcome(IUser user) {

    }

    @Override
    public void onQueueUpdate(int matchType, int usersInQueue) {

    }

    @Override
    public void onMatchFound(int matchType, IUser... oponents) {

    }
}
