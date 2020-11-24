package de.united.azubiware.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import de.golfgl.gdxgamesvcs.GpgsClient;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.desktop.login.ActionResolverDesktop;

public class DesktopLauncher {

	public static void main (String[] arg) {
		ActionResolverDesktop actionResolverDesktop = new ActionResolverDesktop();
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		config.setTitle("AzWare");
		config.setResizable(false);
		config.setWindowedMode(800, 720);

		new Lwjgl3Application(new AzubiWareGame(actionResolverDesktop){
			@Override
			public void create() {
				gsClient = new GpgsClient().initialize("TestLoginApp", Gdx.files.internal("gpgs-client_secret.json"), true);
				super.create();
			}
		}, config);
	}
}
