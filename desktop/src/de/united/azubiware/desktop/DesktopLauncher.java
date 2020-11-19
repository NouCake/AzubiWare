package de.united.azubiware.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.desktop.login.ActionResolverDesktop;

public class DesktopLauncher {
	public static void main (String[] arg) {
		ActionResolverDesktop actionResolverDesktop = new ActionResolverDesktop();
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		config.setTitle("AzubiWare");
		config.setResizable(false);

		new Lwjgl3Application(new AzubiWareGame(actionResolverDesktop), config);
	}
}
