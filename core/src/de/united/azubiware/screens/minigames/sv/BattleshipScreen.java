package de.united.azubiware.screens.minigames.sv;

import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.Games.SV.BattleshipTurnPacket;
import de.united.azubiware.User.SimpleUser;
import de.united.azubiware.screens.minigames.bases.MinigameBaseScreen;
import de.united.azubiware.screens.minigames.bases.TurnBasedMinigameScreen;

public class BattleshipScreen extends TurnBasedMinigameScreen {

    private final BattleShipField yourField;
    private final BattleShipField enemyField;

    public BattleshipScreen(AzubiWareGame game) {
        super(game, new SimpleUser(null, "User1"));

        final float padding = 25;
        yourField = new BattleShipField(this);
        enemyField = new BattleShipField(this);


        float targetWidth = getStage().getWidth()*0.5f - 2*padding;
        float scale = targetWidth / yourField.getWidth();

        yourField.setScale(scale);
        enemyField.setScale(scale);

        yourField.setPosition(padding, getStage().getHeight()*0.5f - yourField.getHeight()*0.5f * scale);
        enemyField.setPosition(getStage().getWidth() - yourField.getWidth() * scale - padding, getStage().getHeight()*0.5f - yourField.getHeight()*0.5f * scale);


        getStage().addActor(yourField);
        getStage().addActor(enemyField);
    }

    public void setShips(int[][] ships){
        for(int s = 0; s < ships.length; s++){
            yourField.setFieldShip(ships[s][0], ships[s][1]);
        }
    }

    public void onFieldClicked(int cellX, int cellY, BattleShipField field){
        if(field == yourField) return;
        if(!isMyTurn()) return;
        //Send Server Shoot
        getGame().getClient().sendMatchPacket(new BattleshipTurnPacket(cellX, cellY, false, true));

    }

    public void enemyTurn(int cellX, int cellY, boolean hit){
        if(hit){
            enemyField.setFieldHit(cellX, cellY);
        } else {
            enemyField.setFieldMiss(cellX, cellY);
        }
    }

}
