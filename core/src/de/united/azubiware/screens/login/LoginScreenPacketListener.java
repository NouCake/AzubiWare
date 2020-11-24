package de.united.azubiware.screens.login;

import de.united.azubiware.User.IUser;
import de.united.azubiware.utility.adapters.ClientListenerAdapter;

public class LoginScreenPacketListener extends ClientListenerAdapter {

    private LoginScreen loginScreen;

    public LoginScreenPacketListener(LoginScreen loginScreen){
        this.loginScreen = loginScreen;
    }

    @Override
    public void onError(String messsage) {

    }

    @Override
    public void onWelcome(IUser user) {

    }

}
