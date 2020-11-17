package de.united.azubiware.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.desktop.login.ActionResolverDesktop;

public class DesktopLauncher {
	public static void main (String[] arg) {
		ActionResolverDesktop actionResolverDesktop = new ActionResolverDesktop();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "AzubiWare";
		config.resizable = false;

		new LwjglApplication(new AzubiWareGame(actionResolverDesktop), config);
	}
}
