package de.united.azubiware.screens.minigames.vg;

import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.Games.VG.VGPacket;
import de.united.azubiware.Games.VG.VGTurnHint;
import de.united.azubiware.User.IUser;
import de.united.azubiware.screens.minigames.bases.TurnBasedMinigameScreen;

public class FourWinsScreen extends TurnBasedMinigameScreen {

    private final int hintUpdateTime = 200;
    private final FourWinsGrid grid;
    private long lastHitnSent = System.currentTimeMillis();


    public FourWinsScreen(AzubiWareGame game, IUser opponent){
        super(game, opponent, "Forest");

        grid = createGrid();
    }

    private FourWinsGrid createGrid(){
        FourWinsGrid grid = new FourWinsGrid(){
            @Override
            public void onRowClicked(int row) {
                super.onRowClicked(row);
                FourWinsScreen.this.onRowClicked(row);
            }

            @Override
            protected void onHintChanged(int row) {
                super.onHintChanged(row);
                if(System.currentTimeMillis() - lastHitnSent > hintUpdateTime){
                    getGame().getClient().sendMatchPacket(new VGTurnHint(row));
                    lastHitnSent = System.currentTimeMillis();
                }
            }
        };
        float padding = 50;
        float targetWidth = getStage().getWidth() - 2 * padding;
        float scale = targetWidth / grid.getWidth();
        grid.setScale(scale);
        float gridHeight = grid.getHeight() * scale;
        grid.setPosition(getStage().getWidth() *0.5f - targetWidth * 0.5f, getStage().getHeight() *0.5f - gridHeight * 0.5f);
        getStage().addActor(grid);
        return grid;
    }
    private void onRowClicked(int row){
        if(!isInputBlocked() && isMyTurn()){
            getGame().getClient().sendMatchPacket(new VGPacket(row));
            grid.addStone(row);
            setTurn(false);
        }
    }

    @Override
    public void setTurn(boolean yourTurn) {
        super.setTurn(yourTurn);
        grid.setTurn(yourTurn);
    }
    public void doEnemyTurn(int row) {
        grid.addEnemyStone(row);
    }
    public void onHint(int row) {
        grid.setEnemyHint(row);
    }
}
