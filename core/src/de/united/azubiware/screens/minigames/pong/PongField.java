package de.united.azubiware.screens.minigames.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class PongField extends Group {

    private final int fieldWidth;
    private final int fieldHeight;

    private final Image ball;
    private final Image bg;
    private final Image player;
    private final Image enemy;

    public PongField() {
        bg = new Image(new Texture(Gdx.files.internal("games/pong/bg.png")));
        ball = new Image(new Texture(Gdx.files.internal("games/pong/ball.png")));
        player = new Image(new Texture(Gdx.files.internal("games/pong/player.png")));
        enemy = new Image(new Texture(Gdx.files.internal("games/pong/enemy.png")));


        addActor(bg);
        addActor(ball);
        addActor(player);
        addActor(enemy);

        fieldWidth = (int)bg.getWidth();
        fieldHeight = (int)bg.getHeight();
        setSize(fieldWidth, fieldHeight);

        initComponentPositions();
    }

    private void initComponentPositions(){
        float bgScale = 1.0f;
        bg.setScale(bgScale);
        bg.setPosition(bg.getWidth() * (bgScale-1) * -0.5f, bg.getHeight() * (bgScale-1) * -0.5f);

        ball.setPosition(fieldWidth*0.5f, fieldHeight*0.5f, Align.center);
        player.setPosition(fieldWidth*0.5f, 0, Align.center | Align.bottom);
        enemy.setPosition(fieldWidth*0.5f, fieldHeight, Align.center | Align.top);

        addListener(new ClickListener(){
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                updatePlayer((x - player.getWidth()*0.5f) / (fieldWidth - player.getWidth()));
                return super.mouseMoved(event, x, y);
            }
        });
        updatePlayer(1);
    }

    public void updateBall(float relativeX, float relativeY){
        ball.setPosition(fieldWidth * relativeX, fieldHeight * relativeY, Align.center);
    }

    public void updateEnemy(float relativeX){
        updateBar(enemy, relativeX);
    }

    public void updatePlayer(float relativeX){
        updateBar(player, relativeX);
    }

    private void updateBar(Actor bar, float relativeX){
        relativeX = Math.min(1, Math.max(0, relativeX));
        bar.setPosition(bar.getWidth()*0.5f + (fieldWidth - bar.getWidth()) * relativeX, bar.getY(), Align.center | Align.bottom);
    }

}
