package de.united.azubiware;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import de.golfgl.gdxgamesvcs.GpgsClient;
import de.united.azubiware.login.ActionResolverAndroid;

public class AndroidLauncher extends AndroidApplication {

	private ActionResolverAndroid actionResolverAndroid;
	private GpgsClient gpgsClient;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.actionResolverAndroid = new ActionResolverAndroid(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		config.hideStatusBar = true;

		AzubiWareGame game = new AzubiWareGame(actionResolverAndroid);

		//gpgsClient = new GpgsClient().initialize(this, false);
		//game.gsClient = gpgsClient;

		initialize(game, config);
	}

	/*
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (gpgsClient != null)
			gpgsClient.onGpgsActivityResult(requestCode, resultCode, data);
	}
	 */
}
