package de.united.azubiware;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import de.golfgl.gdxgamesvcs.IGameServiceClient;
import de.golfgl.gdxgamesvcs.NoGameServiceClient;
import de.united.azubiware.connection.client.Client;
import de.united.azubiware.connection.client.IClient;
import de.united.azubiware.login.ActionResolver;
import de.united.azubiware.screens.SplashScreen;
import de.united.azubiware.utility.FontLoader;
import de.united.azubiware.utility.GpgpClientListener;


public class AzubiWareGame extends Game {

	ActionResolver resolver;
	boolean initiatedSignIn = false;
	boolean isHTML = false;
	BitmapFont font;
	IClient client;

	public IGameServiceClient gsClient;

	public AzubiWareGame(ActionResolver resolver){
		this.resolver = resolver;

		this.client = new Client();
	}

	public AzubiWareGame(ActionResolver resolver, boolean isHTML){
		this.resolver = resolver;
		this.isHTML = isHTML;

		this.client = new Client();
	}

	@Override
	public void create() {
		if (gsClient == null)
			gsClient = new NoGameServiceClient();

		gsClient.resumeSession();
		gsClient.setListener(new GpgpClientListener());
		gsClient.logIn();

		client.start();

		if(!isHTML) {
			font = new FontLoader().loadFont("fonts/8-bitArcadeIn.ttf");
		}else{
			font = new BitmapFont();
		}

		this.setScreen(new SplashScreen(this));
	}

	public void render() {
		super.render();
	}

	public void dispose() {
		getClient().stop();
		gsClient.logOff();
	}

	@Override
	public void pause() {
		super.pause();

		gsClient.pauseSession();
	}

	@Override
	public void resume() {
		super.resume();

		gsClient.resumeSession();
	}

	public void initateSignIn(){
		initiatedSignIn = true;
		resolver.signIn();
	}

	public boolean isInitiatedSignIn() {
		return initiatedSignIn;
	}

	public BitmapFont getFont() {
		return font;
	}

	public IClient getClient() {
		return client;
	}

}
