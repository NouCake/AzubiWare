package de.united.azubiware.screens.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.utility.TicTacToeButtons;

public class TicTacToeScreen implements Screen {

    final AzubiWareGame game;

    Texture backgroundTexture;
    Sprite backgroundSprite;

    Stage stage;

    public TicTacToeScreen(AzubiWareGame game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/backgroundCastles.png"));
        backgroundSprite = new Sprite(backgroundTexture);
    }

    public void drawBackground(){
        stage.getBatch().begin();
        stage.getBatch().draw(backgroundSprite, 0, 0, stage.getWidth(), stage.getHeight());
        drawLines();
        stage.getBatch().end();
    }



    public void drawLines(){
        float centerX = stage.getWidth()/2;
        float centerY = stage.getHeight()/2;

        float lineLength = stage.getWidth()*0.5f;
        float lineThickness = 5f;

        float fieldSize = ((stage.getWidth()*0.5f)-10f)/3;

        Texture texture = new Texture(Gdx.files.internal("line.png"));

        stage.getBatch().draw(texture, centerX-lineLength/2, centerY-(fieldSize/2+5f), lineLength, lineThickness);
        stage.getBatch().draw(texture, centerX-lineLength/2, centerY+(fieldSize/2+5f), lineLength, lineThickness);

        stage.getBatch().draw(texture, centerX-(fieldSize/2+5f), centerY-lineLength/2, lineThickness, lineLength);
        stage.getBatch().draw(texture, centerX+(fieldSize/2+5f), centerY-lineLength/2, lineThickness, lineLength);
    }

    public void createButtons(){
        for(int y = 0; y < 3; y++){
            for(int x = 0; x < 3; x++){
                Button button = new Button(TicTacToeButtons.generateButtonStyle());

                float centerX = stage.getWidth()/2-button.getWidth()/2;
                float centerY = stage.getHeight()/2+button.getHeight()*1.5f;

                float buttonX = centerX-((1-x)*(1.25f*button.getWidth()));
                float buttonY = centerY+((1-y)*(1.25f*button.getHeight()));

                button.setPosition(buttonX, buttonY);

                stage.addActor(button);
            }
        }

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        drawBackground();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
