package de.united.azubiware;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import de.golfgl.gdxgamesvcs.IGameServiceClient;
import de.golfgl.gdxgamesvcs.NoGameServiceClient;
import de.united.azubiware.User.IUser;
import de.united.azubiware.User.SimpleUser;
import de.united.azubiware.connection.client.Client;
import de.united.azubiware.connection.client.IClient;
import de.united.azubiware.login.ActionResolver;
import de.united.azubiware.minigames.GameManager;
import de.united.azubiware.screens.menu.MainMenuScreen;
import de.united.azubiware.screens.minigames.ssp.SSPScreen;
import de.united.azubiware.screens.splash.SplashScreen;
import de.united.azubiware.utility.FontLoader;
import de.united.azubiware.utility.GpgpClientListener;


public class AzubiWareGame extends Game {

	ActionResolver resolver;
	boolean initiatedSignIn = false;

	BitmapFont font;
	IClient client;

	private GameManager gameManager;
	private IUser user;
	public IGameServiceClient gsClient;

	public AzubiWareGame(ActionResolver resolver){
		this.resolver = resolver;
		this.gameManager = new GameManager();
		this.client = new Client();
	}

	@Override
	public void create() {
		if (gsClient == null)
			gsClient = new NoGameServiceClient();

		gsClient.setListener(new GpgpClientListener());
		gsClient.resumeSession();

		client.start();

		font = new FontLoader().loadFont("fonts/8-bitArcadeIn.ttf");

		this.setScreen(new MainMenuScreen(this));
		//user = new SimpleUser(null, "Ancozockt");
		//this.setScreen(new SSPScreen(this, new SimpleUser(null, "NouCake")));
	}

	public void render() {
		super.render();
	}

	public void dispose() {
		client.stop();
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

	public void initiateSignIn(){
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

	public GameManager getGameManager() {
		return gameManager;
	}

	public void setUser(IUser user) {
		this.user = user;
	}

	public IUser getUser() {
		return user;
	}
}
