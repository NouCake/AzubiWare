package de.united.azubiware;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import de.united.azubiware.login.ActionResolver;
import de.united.azubiware.screens.SplashScreen;
import de.united.azubiware.utility.FontLoader;

public class AzubiWareGame extends Game {

	ActionResolver resolver;
	boolean initiatedSignIn = false;
	boolean isHTML = false;
	BitmapFont font;

	public AzubiWareGame(ActionResolver resolver){
		this.resolver = resolver;
	}

	public AzubiWareGame(ActionResolver resolver, boolean isHTML){
		this.resolver = resolver;
		this.isHTML = isHTML;
	}

	@Override
	public void create() {
		if(!isHTML) {
			font = new FontLoader().loadFont("fonts/8-bitArcadeIn.ttf");
		}else{
			font = new BitmapFont();
		}

		this.setScreen(new SplashScreen(this));
	}

	public void render() {
		super.render(); //important!
	}

	public void dispose() {
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
}