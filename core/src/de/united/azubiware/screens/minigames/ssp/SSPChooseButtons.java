package de.united.azubiware.screens.minigames.ssp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class SSPChooseButtons extends Group {

    private SSPScreen sspScreen;

    private final float stageWidth;
    private final float stageHeight;

    private Button scissors;
    private Button rock;
    private Button paper;

    private final float paddingX = 15;

    public SSPChooseButtons(float stageWith, float stageHeight, SSPScreen sspScreen){
        this.sspScreen = sspScreen;

        this.stageWidth = stageWith;
        this.stageHeight = stageHeight;

        scissors = createScissorsButton();
        rock = createRockButton();
        paper = createPaperButton();

        float groupWith = rock.getWidth() + scissors.getWidth() + paper.getWidth() + (paddingX*2);
        float groupHeight = rock.getHeight();

        setSize(groupWith, groupHeight);

        addActors();
    }

    private void addActors(){
        addActor(rock);
        addActor(scissors);
        addActor(paper);
    }

    private Button.ButtonStyle createButtonStyle(String type){
        Drawable drawableUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("games/ssp/" + type + "_button_up.png"))));
        Drawable drawableDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("games/ssp/" + type + "_button_down.png"))));

        Button.ButtonStyle style = new Button.ButtonStyle();

        style.up = drawableUp;
        style.down = drawableDown;
        style.checked = drawableDown;
        style.over = drawableDown;

        return style;
    }

    private Button createScissorsButton(){
        Button btn =  new Button(createButtonStyle("scissors"));

        btn.setWidth(0.25f*stageWidth);
        btn.setHeight(btn.getWidth());

        btn.setPosition(0, 0);

        btn.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!btn.isChecked() && !btn.isDisabled()){
                    rock.setChecked(false);
                    paper.setChecked(false);
                    sspScreen.setPickType(0);
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        return btn;
    }

    private Button createRockButton(){
        Button btn =  new Button(createButtonStyle("rock"));

        btn.setWidth(0.25f*stageWidth);
        btn.setHeight(btn.getWidth());

        btn.setPosition(scissors.getX() + scissors.getWidth() + paddingX, 0);

        btn.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!btn.isChecked() && !btn.isDisabled()){
                    scissors.setChecked(false);
                    paper.setChecked(false);
                    sspScreen.setPickType(1);
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });


        return btn;
    }

    private Button createPaperButton(){
        Button btn =  new Button(createButtonStyle("paper"));

        btn.setWidth(0.25f*stageWidth);
        btn.setHeight(btn.getWidth());

        btn.setPosition(rock.getX() + rock.getWidth() + paddingX, 0);

        btn.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!btn.isChecked() && !btn.isDisabled()){
                    scissors.setChecked(false);
                    rock.setChecked(false);
                    sspScreen.setPickType(2);
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });


        return btn;
    }

    public void disableButtons(){
        scissors.setDisabled(true);
        rock.setDisabled(true);
        paper.setDisabled(true);
    }

    public void enableButtons(){
        scissors.setDisabled(false);
        rock.setDisabled(false);
        paper.setDisabled(false);
    }

}
