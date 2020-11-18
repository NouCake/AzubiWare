package de.united.azubiware.screens.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.screens.menu.MainMenuScreen;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TicTacToeScreen extends ScreenAdapter {

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

        Image image = new Image(new Texture(Gdx.files.internal("games/ttt_bottom.png")));
        image.setWidth(stage.getWidth());
        image.setHeight(0.25f * stage.getWidth());
        image.setPosition(stage.getWidth()/2f-image.getWidth()/2f, 0);

        Button leave = new Button(createButtonStyle());
        leave.setPosition(stage.getWidth()/2f - leave.getWidth()/2f, leave.getHeight()/4.25f);
        leave.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!leave.isDisabled()){
                    dispose();
                    /*
                    LEAVE GAME
                     */
                    game.setScreen(new MainMenuScreen(game));
                }
                return true;
            }
        });

        stage.addActor(image);
        stage.addActor(leave);

        stage.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    List<TicTacToeField> fieldList = fields.values().stream().filter(new Predicate<TicTacToeField>() {
                        @Override
                        public boolean test(TicTacToeField ticTacToeField) {
                            return ticTacToeField.getState() == 0;
                        }
                    }).collect(Collectors.toList());
                    if(fieldList.size() > 0){
                        for(TicTacToeField ticTacToeField : fieldList){
                            float fieldMaxX = ticTacToeField.getMax().x;
                            float fieldMaxY = ticTacToeField.getMax().y;
                            float fieldMinX = ticTacToeField.getMin().x;
                            float fieldMinY = ticTacToeField.getMin().y;

                            if(x >= fieldMinX && x <= fieldMaxX && y >= fieldMinY && y <= fieldMaxY){
                                ticTacToeField.setState(1);
                                break;
                            }
                        }
                    }
                }catch (Exception exception){
                    exception.printStackTrace();
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    public void drawBackground(){
        stage.getBatch().begin();
        stage.getBatch().draw(backgroundSprite, 0, 0, stage.getWidth(), stage.getHeight());
        drawLines();
        stage.getBatch().end();
    }

    private HashMap<Integer[], TicTacToeField> fields;

    public void drawLines(){
        float centerX = stage.getWidth()/2;
        float centerY = stage.getHeight()/1.5f;

        float lineLength = stage.getWidth()*0.45f;
        float lineThickness = 5f;

        float fieldSize = (lineLength-10f)/3;
        Texture texture = new Texture(Gdx.files.internal("line.png"));

        stage.getBatch().draw(texture, centerX-lineLength/2, centerY-(fieldSize/2+lineThickness/2), lineLength, lineThickness);
        stage.getBatch().draw(texture, centerX-lineLength/2, centerY+(fieldSize/2+lineThickness/2), lineLength, lineThickness);

        stage.getBatch().draw(texture, centerX-(fieldSize/2+lineThickness/2), centerY-lineLength/2, lineThickness, lineLength);
        stage.getBatch().draw(texture, centerX+(fieldSize/2+lineThickness/2), centerY-lineLength/2, lineThickness, lineLength);

        if(fields == null) {
            fields = new HashMap<>();
            for (int y = 0; y < 3; y++) {
                float suffY = y > 1 ? 7.5f : -7.5f;
                float fieldLineY = (1 - y) * (fieldSize / 2 + lineThickness / 2);
                float fieldSizeDivY = (1 - y) * (fieldSize / 2 + suffY);
                float posY = centerY + fieldLineY + fieldSizeDivY;
                for (int x = 0; x < 3; x++) {
                    float suffX = x > 1 ? 7.5f : -7.5f;
                    float multiplier = x == 2 ? 1 : -1;
                    if (x == 1)
                        multiplier = 0;
                    float fieldLineX = (multiplier) * (fieldSize / 2 + lineThickness / 2);
                    float fieldSizeDivX = (multiplier) * (fieldSize / 2);
                    float posX = centerX + fieldLineX + fieldSizeDivX + suffX;

                    Vector2 center = new Vector2(posX, posY);
                    Vector2 max = new Vector2(posX + fieldSize / 2, posY + fieldSize / 2);
                    Vector2 min = new Vector2(posX - fieldSize / 2, posY - fieldSize / 2);

                    TicTacToeField ticTacToeField = new TicTacToeField(min, max, center);
                    fields.put(new Integer[]{x, y}, ticTacToeField);
                }
            }
        }else{
            Texture cross = new Texture(Gdx.files.internal("games/ttt/blue_cross.png"));
            Texture circle = new Texture(Gdx.files.internal("games/ttt/red_circle.png"));

            List<TicTacToeField> fieldList = fields.values().stream().filter(new Predicate<TicTacToeField>() {
                @Override
                public boolean test(TicTacToeField ticTacToeField) {
                    return ticTacToeField.getState() != 0;
                }
            }).collect(Collectors.toList());
            if(fieldList.size() > 0) {
                for (TicTacToeField ticTacToeField : fieldList) {
                    if (ticTacToeField.getState() == 1) {
                        stage.getBatch().draw(cross, ticTacToeField.getCenter().x - ((fieldSize - 10f) / 2), ticTacToeField.getCenter().y - ((fieldSize - 10f) / 2), fieldSize - 10f, fieldSize - 10f);
                    } else {
                        stage.getBatch().draw(circle, ticTacToeField.getCenter().x - ((fieldSize - 10f) / 2), ticTacToeField.getCenter().y - ((fieldSize - 10f) / 2), fieldSize - 10f, fieldSize - 10f);
                    }
                }
            }
        }
    }

    private Button.ButtonStyle createButtonStyle(){
        Drawable drawableUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/leave/button_leave_up.png"))));
        Drawable drawableDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/leave/button_leave_down.png"))));

        Button.ButtonStyle style = new Button.ButtonStyle();

        style.up = drawableUp;
        style.down = drawableDown;
        style.over = drawableDown;

        return style;
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
    public void dispose() {
        stage.dispose();
    }



}
