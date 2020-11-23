package de.united.azubiware.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.screens.menu.buttonlistener.LeftButtonListener;
import de.united.azubiware.screens.menu.buttonlistener.PlayButtonListener;
import de.united.azubiware.screens.menu.buttonlistener.RightButtonListener;
import de.united.azubiware.utility.MiniGamePaginator;

public class MenuButtonManager {

    private AzubiWareGame game;
    private Stage stage;
    private MenuButtonStyler buttonStyler;
    private MiniGamePaginator paginator;

    private Button playButton;

    private Button rightButton;
    private Button leftButton;

    public MenuButtonManager(Stage stage, MiniGamePaginator paginator, AzubiWareGame game){
        this.game = game;
        this.stage = stage;
        this.paginator = paginator;
        buttonStyler = new MenuButtonStyler();

        createPlayButton();
        createPaginationButtons();
    }

    public void createPlayButton(){
        playButton = new Button(buttonStyler.createPlayButtonStyle());

        playButton.setPosition((stage.getWidth()/2f)-playButton.getWidth()/2, stage.getHeight()/4.5f);
        playButton.addListener(new PlayButtonListener(this, playButton));

        stage.addActor(playButton);
    }

    public void createPaginationButtons(){
        leftButton = new Button(buttonStyler.createArrowStyle("Left"));
        leftButton.setPosition(playButton.getX()-(playButton.getWidth()/2), playButton.getY());
        leftButton.addListener(new LeftButtonListener(this, leftButton));
        stage.addActor(leftButton);

        rightButton = new Button(buttonStyler.createArrowStyle("Right"));
        rightButton.setPosition(playButton.getX()+(playButton.getWidth()/2)+rightButton.getWidth()*2, playButton.getY());
        rightButton.addListener(new RightButtonListener(this, rightButton));
        stage.addActor(rightButton);
    }

    public void playSound(){
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/click2.ogg"));
        sound.play();
    }

    public boolean isInQueue(){
        return playButton.isChecked();
    }

    public void queueUp(){
        game.getClient().sendQueueStart(paginator.getCurrentMatchType());

        rightButton.setDisabled(true);
        leftButton.setDisabled(true);
    }

    public void quitQueue(){
        game.getClient().sendQueueStop();

        rightButton.setDisabled(false);
        leftButton.setDisabled(false);
    }

    public MiniGamePaginator getPaginator() {
        return paginator;
    }
}
