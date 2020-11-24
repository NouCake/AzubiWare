package de.united.azubiware.screens.minigames.vg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.united.azubiware.utility.adapters.ClickListenerAdapter;
import de.united.azubiware.utility.IClickListener;

public class FourWinsGrid extends Group implements IClickListener {

    private static final int numRows = 8;
    private static final int numCols = 6;

    private static final int gridWidth = 128 * numRows;
    private static final int gridHeight = 128 * numCols;

    private final Image gridOverlay;
    private final Image gridBg;
    private final Group stoneLayer;

    private final Texture stonePlayer;
    private final Texture stoneEnemy;

    private final int[] stonesInRow = new int[numRows];

    private final Image hoverStonePlayer;
    private final Image hoverStoneEnemy;

    private int curHintPos = 0;

    public FourWinsGrid(){
        setSize(gridWidth, gridHeight);

        stonePlayer = new Texture(Gdx.files.internal("games/vg/stone_1.png"));
        stoneEnemy = new Texture(Gdx.files.internal("games/vg/stone_2.png"));

        gridOverlay = createOverlay();
        gridBg = createOverlayBackground(gridOverlay);

        hoverStonePlayer = createHoverStone(stonePlayer, gridOverlay);
        hoverStoneEnemy = createHoverStone(stoneEnemy, gridOverlay);

        stoneLayer = new Group();

        addActors();
        addListener(new ClickListenerAdapter(this));


    }

    private void addActors(){
        addActor(gridBg);
        addActor(stoneLayer);
        addActor(hoverStonePlayer);
        addActor(hoverStoneEnemy);
        addActor(gridOverlay);
    }
    private Image createOverlayBackground(Actor overlay){
        Image image = new Image(new Texture(Gdx.files.internal("games/vg/gridBg.png")));
        image.setPosition(overlay.getX(), overlay.getY());
        image.setSize(overlay.getWidth(), overlay.getHeight());
        return image;
    }
    private Image createHoverStone(Texture texture,Actor overlay) {
        Image image = new Image(texture);
        image.setPosition(0, overlay.getHeight() - image.getHeight() * 0.5f);
        image.setVisible(false);
        return image;
    }
    private Image createOverlay(){
        Image image = new Image(new Texture(Gdx.files.internal("games/vg/grid.png")));
        image.setPosition(gridWidth * 0.5f - image.getWidth()*0.5f, gridHeight*0.5f - image.getHeight()*0.5f);
        return image;
    }

    public void addStone(int row){
        addStone(row, stonePlayer);
    }
    public void addEnemyStone(int row){
        addStone(row, stoneEnemy);
    }

    public void setTurn(boolean yourTurn){
        if(yourTurn){
            hoverStoneEnemy.setVisible(false);
            hoverStonePlayer.setVisible(true);
        } else {
            hoverStoneEnemy.setVisible(true);
            hoverStonePlayer.setVisible(false);
        }
    }

    public void setEnemyHint(int row){
        updateHoverStonePosition(hoverStoneEnemy, row);
    }

    protected void onHintChanged(int row){

    }
    protected void onRowClicked(int row){

    }
    private int getStonesInRow(int row){
        return stonesInRow[row];
    }
    private void addStoneInRow(int row){
        stonesInRow[row]++;
    }
    private void updateMouseInput(float x, float y){
        int cellWidth = gridWidth / numRows;
        int mouseRow = (int)(x / cellWidth);
        if(mouseRow >= 0 && mouseRow < numRows){
            if(curHintPos != mouseRow){
                curHintPos = mouseRow;
                updateHoverStonePosition(hoverStonePlayer, curHintPos);
                onHintChanged(curHintPos);
            }
        }
    }
    private void addStone(int row, Texture stone){
        int col = getStonesInRow(row);
        int cellWidth = gridWidth / numRows;

        Vector2 startPos = new Vector2(row * cellWidth, hoverStonePlayer.getY());
        Vector2 targetPos = new Vector2(row * (getWidth() / numRows), col * (getHeight() / numCols));
        final float targetTime = 0.5f;
        final Interpolation pol = Interpolation.exp5In;
        Image image = new Image(stone){
            private float timer;
            @Override
            public void act(float delta) {
                super.act(delta);
                if(timer <= targetTime){
                    timer += delta;
                    setPosition(getX(), pol.apply(startPos.y, targetPos.y, Math.min(timer / targetTime, 1)));
                }
            }
        };
        image.setPosition(startPos.x, startPos.y);
        stoneLayer.addActor(image);

        addStoneInRow(row);
    }
    private void updateHoverStonePosition(Image hoverStone, int row){
        int cellWidth = gridWidth / numRows;
        hoverStone.setPosition(row * cellWidth, hoverStone.getY());
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        int cellWidth = gridWidth / numRows;
        int mouseRow = (int)(x / cellWidth);
        if(mouseRow >= 0 && mouseRow < numRows){
            onRowClicked(mouseRow);
            return true;
        }
        return false;
    }
    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        return false;
    }
    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
        updateMouseInput(x, y);
        return false;
    }
}
