package de.united.azubiware.screens.minigames.sv;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class BattleShipField extends Group {

    private static final int cellSize = 128;
    private static final int gridWidth = 10;
    private static final int gridHeight = 10;

    private final boolean[][] hittedFields = new boolean[gridWidth][gridHeight];

    private final Texture texHit;
    private final Texture texMiss;

    private final Texture texShip;

    private final Image cellSelector;

    private final Image background;

    private final BattleshipScreen screen;

    public BattleShipField(BattleshipScreen screen) {
        this.screen = screen;
        Texture txBg = new Texture(Gdx.files.internal("games/sv/bg.png"), true);
        txBg.setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.Linear);
        background = new Image(txBg);
        setSize(background.getWidth(), background.getHeight());
        cellSelector = new Image(new Texture(Gdx.files.internal("games/sv/selector.png")));

        texHit = new Texture(Gdx.files.internal("games/sv/hit.png"), true);
        texHit.setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.Linear);
        texMiss = new Texture(Gdx.files.internal("games/sv/miss.png"), true);
        texMiss.setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.Linear);
        texShip = new Texture(Gdx.files.internal("games/sv/ship.png"), true);
        texShip.setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.Linear);

        addActor(background);
        addActor(cellSelector);
        addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                onFieldClicked(x, y);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                onMouseMove(x, y);
                return super.mouseMoved(event, x, y);
            }
        });
    }

    private void onMouseMove(float x, float y){
        int cellX = (int)(x/cellSize);
        int cellY = (int)(y/cellSize);
        cellSelector.setPosition(cellX * cellSize, cellY * cellSize);
    }

    private void onFieldClicked(float x, float y){
        int cellX = (int)(x/cellSize);
        int cellY = (int)(y/cellSize);
        if(isFieldHit(cellX, cellY)) return;
        screen.onFieldClicked(cellX, cellY, this);
    }

    private void setFeild(Texture tex, int cellX, int cellY){
        Image image = new Image(tex);
        image.setPosition(cellX * cellSize, cellY * cellSize);
        addActor(image);
    }

    public void setFieldHit(int cellX, int cellY){
        setFeild(texHit, cellX, cellY);
        hittedFields[cellX][cellY] = true;
    }

    public void setFieldMiss(int cellX, int cellY){
        setFeild(texMiss, cellX, cellY);
        hittedFields[cellX][cellY] = true;
    }

    public void setFieldShip(int cellX, int cellY){
        setFeild(texShip, cellX, cellY);
    }

    private boolean isFieldHit(int cellX, int cellY){
        return hittedFields[cellX][cellY];
    }

}
