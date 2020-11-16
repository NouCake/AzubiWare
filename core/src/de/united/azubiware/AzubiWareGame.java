package de.united.azubiware;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.united.azubiware.screens.MainMenu;

public class AzubiWareGame extends Game {

	public Stage stage;
	public BitmapFont font;

	public int width;
	public int height;

	@Override
	public void create() {
		stage = new Stage(new ScreenViewport());
		font = new BitmapFont();

		this.setScreen(new MainMenu(this));
	}

	public void render() {
		super.render(); //important!
	}

	public void dispose() {
		stage.dispose();
	}
}