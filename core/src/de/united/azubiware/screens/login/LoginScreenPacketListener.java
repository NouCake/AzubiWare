package de.united.azubiware.screens.login;

import de.united.azubiware.Connection.IClientListener;
import de.united.azubiware.Connection.PacketListener;
import de.united.azubiware.User.IUser;

public class LoginScreenPacketListener implements IClientListener {

    @Override
    public void onConnected() {

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
    public void onMatchStart(int matchType, IUser... oponents) {

    }
}
