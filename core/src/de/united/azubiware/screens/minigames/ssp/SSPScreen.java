package de.united.azubiware.screens.minigames.ssp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.User.IUser;
import de.united.azubiware.screens.minigames.bases.RoundBasedMinigameScreen;

public class SSPScreen extends RoundBasedMinigameScreen {

    private final Button scissors;
    private final Button rock;
    private final Button paper;

    public SSPScreen(AzubiWareGame game, IUser[] opponents){
        super(game, "Desert", opponents);

        rock = createRockButton();
        scissors = createScissorsButton();
        paper = createPaperButton();
    }

    private final float paddingX = 15;

    private Button createScissorsButton(){
        Drawable drawableUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("games/ssp/scissors_button_up.png"))));
        Drawable drawableDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("games/ssp/scissors_button_down.png"))));

        Button.ButtonStyle style = new Button.ButtonStyle();

        style.up = drawableUp;
        style.down = drawableDown;
        style.checked = drawableDown;
        style.over = drawableDown;

        Button btn =  new Button(style);

        btn.setWidth(0.25f*getStage().getWidth());
        btn.setHeight(btn.getWidth());

        btn.setPosition(rock.getX() - paddingX - btn.getWidth(), getStage().getHeight()/2);

        btn.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!btn.isChecked() && !btn.isDisabled()){
                    rock.setChecked(false);
                    paper.setChecked(false);
                    // Send Choose to server
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        getStage().addActor(btn);

        return btn;
    }


    private Button createRockButton(){
        Drawable drawableUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("games/ssp/rock_button_up.png"))));
        Drawable drawableDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("games/ssp/rock_button_down.png"))));

        Button.ButtonStyle style = new Button.ButtonStyle();

        style.up = drawableUp;
        style.down = drawableDown;
        style.checked = drawableDown;
        style.over = drawableDown;

        Button btn =  new Button(style);

        btn.setWidth(0.25f*getStage().getWidth());
        btn.setHeight(btn.getWidth());
        btn.setPosition(getStage().getWidth()/2 - btn.getWidth()/2, getStage().getHeight()/2);

        btn.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!btn.isChecked() && !btn.isDisabled()){
                    scissors.setChecked(false);
                    paper.setChecked(false);
                    // Send Choose to server
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        getStage().addActor(btn);

        return btn;
    }

    private Button createPaperButton(){
        Drawable drawableUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("games/ssp/paper_button_up.png"))));
        Drawable drawableDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("games/ssp/paper_button_down.png"))));

        Button.ButtonStyle style = new Button.ButtonStyle();

        style.up = drawableUp;
        style.down = drawableDown;
        style.checked = drawableDown;
        style.over = drawableDown;

        Button btn =  new Button(style);

        btn.setWidth(0.25f*getStage().getWidth());
        btn.setHeight(btn.getWidth());
        btn.setPosition(getStage().getWidth()/2 + paddingX + btn.getWidth()/2, getStage().getHeight()/2);

        btn.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!btn.isChecked() && !btn.isDisabled()){
                    scissors.setChecked(false);
                    rock.setChecked(false);
                    // Send Choose to server
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        getStage().addActor(btn);

        return btn;
    }

}
