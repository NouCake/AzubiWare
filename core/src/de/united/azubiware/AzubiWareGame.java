package de.united.azubiware;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import de.united.azubiware.login.ActionResolver;
import de.united.azubiware.screens.SplashScreen;

public class AzubiWareGame extends Game {

	ActionResolver resolver;
	boolean initiatedSignIn = false;
	BitmapFont font;

	public AzubiWareGame(ActionResolver resolver){
		this.resolver = resolver;
	}

	@Override
	public void create() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/8-bitArcadeIn.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 32;
		font = generator.generateFont(parameter);

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