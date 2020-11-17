package de.united.azubiware.login;

import de.united.azubiware.AndroidLauncher;

public class ActionResolverAndroid implements ActionResolver{

    private AndroidLauncher launcher;

    public ActionResolverAndroid(AndroidLauncher launcher){
        this.launcher = launcher;
    }

    @Override
    public void signIn() {
    }
}
