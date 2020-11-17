package de.united.azubiware;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import de.united.azubiware.login.ActionResolverAndroid;

public class AndroidLauncher extends AndroidApplication {

	private ActionResolverAndroid actionResolverAndroid;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.actionResolverAndroid = new ActionResolverAndroid(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		initialize(new AzubiWareGame(actionResolverAndroid), config);
	}
}
