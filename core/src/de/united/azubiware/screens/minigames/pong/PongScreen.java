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

    private final static float paddingTB = 100;

    private final PongField field;

    public PongScreen(AzubiWareGame game, IUser... oponents) {
        super(game, oponents);

        field = new PongField();
        initFieldPosition();
        getStage().addActor(field);
        reorderOverlays();
    }

    public void updateBall(float rX, float rY){
        field.updateBall(rX, rY);
    }

    private void onPlayerUpdated(float relativeX){
        getGame().getClient().sendMatchPacket(new PongPlayerUpdatePacket(relativeX));
    }

    public void updateEnemy(float relativeX){
        field.updatePlayer(relativeX);
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
