package de.united.azubiware.screens.minigames.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.Games.Pong.PongPlayerUpdatePacket;
import de.united.azubiware.User.IUser;
import de.united.azubiware.screens.minigames.bases.MinigameBaseScreen;

public class PongScreen extends MinigameBaseScreen {

    private final static long playerUpdateTime = 32;
    private final static float paddingTB = 100;

    private final PongField field;
    private long lastPlayerUpdate = System.currentTimeMillis();

    public PongScreen(AzubiWareGame game, IUser... oponents) {
        super(game, oponents);

        field = new PongField(){
            @Override
            protected void onPlayerUpdated(float x) {
                super.onPlayerUpdated(x);
                PongScreen.this.onPlayerUpdated(x);
            }
        };
        initFieldPosition();
        getStage().addActor(field);
        reorderOverlays();
    }

    public void updateBall(float rX, float rY){
        field.updateBall(rX, rY);
    }

    private void onPlayerUpdated(float relativeX){
        long curTime = System.currentTimeMillis();
        if(curTime - lastPlayerUpdate > playerUpdateTime){
            getGame().getClient().sendMatchPacket(new PongPlayerUpdatePacket(relativeX));
            lastPlayerUpdate = curTime;
        }
    }

    public void updateEnemy(float relativeX){
        field.updateEnemy(relativeX);
    }

    public void updateScore(int p1, int p2){
        System.out.println("Score: " + p1 + ":" + p2);
    }

    private void initFieldPosition(){
        float fieldScale = (getStage().getHeight() - paddingTB*2) / field.getHeight();
        field.setPosition(getStage().getWidth()*0.5f, getStage().getHeight()*0.5f, Align.center);
        field.setScale(fieldScale);
        field.setPosition(field.getX() - field.getWidth() * (fieldScale-1) * 0.5f, field.getY() - field.getHeight() * (fieldScale-1) * 0.5f);
    }


}
