package de.united.azubiware.screens.splash;

import de.united.azubiware.utility.adapters.ClientListenerAdapter;

public class SplashScreenPacketListener extends ClientListenerAdapter {

    private SplashScreen splashScreen;

    public SplashScreenPacketListener(SplashScreen splashScreen){
        this.splashScreen = splashScreen;
    }

    @Override
    public void onConnected() {
        splashScreen.setState("Connected");
    }

    @Override
    public void onError(String messsage) {
        super.onError(messsage);
    }
}
