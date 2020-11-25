package de.united.azubiware.screens.menu;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.minigames.FourWins;
import de.united.azubiware.minigames.GameManager;
import de.united.azubiware.minigames.interfaces.IGame;
import de.united.azubiware.minigames.SSP;
import de.united.azubiware.minigames.TicTacToe;
import de.united.azubiware.minigames.interfaces.IGameManager;

import java.util.HashMap;

public class MiniGamePaginator extends Group {

    private Stage stage;

    private int min = -1;
    private int max = 2;

    private int current = 0;
    private int finish = 0;

    private final float finalX;

    private boolean isPaginating = false;

    private HashMap<Integer, IGame> miniGame = new HashMap<>();
    private HashMap<Integer, Image> gameImages = new HashMap<>();

    public MiniGamePaginator(Stage stage, IGameManager gameManager){
        this.stage = stage;
        this.finalX = (stage.getWidth()/2f-(stage.getWidth()*0.25f));

        create(gameManager);
    }

    public void create(IGameManager gameManager){
        int counter = min;
        for(IGame game : gameManager.getGames()){
            miniGame.put(counter, game);

            Image image = new Image(game.getSplash());
            image.setSize(stage.getWidth()/2, stage.getHeight()/2);

            if(counter == 0){
                image.setPosition(stage.getWidth()/2f-image.getWidth()/2, stage.getHeight()/2f-((image.getHeight()/2)*0.4f));
            }else if(counter > 0){
                image.setPosition(counter*stage.getWidth() + image.getWidth()/2, stage.getHeight()/2f-((image.getHeight()/2)*0.4f));
            }else{
                image.setPosition(counter*(image.getWidth()*1.5f), stage.getHeight()/2f-((image.getHeight()/2)*0.4f));
            }
            gameImages.put(counter, image);
            stage.addActor(image);

            counter++;
        }

        /*
        miniGame.put(0, new TicTacToe());
        Image tttImage = new Image(miniGame.get(0).getSplash());
        tttImage.setSize(stage.getWidth()*0.5f, (stage.getWidth()*0.5f));
        tttImage.setPosition(stage.getWidth()/2f-tttImage.getWidth()/2, stage.getHeight()/2f-((tttImage.getHeight()/2)*0.4f));
        gameImages.put(0, tttImage);

        miniGame.put(-1, new Pong());
        Image sspImage = new Image(miniGame.get(-1).getSplash());
        sspImage.setSize(stage.getWidth()*0.5f, (stage.getWidth()*0.5f));
        sspImage.setPosition(-(sspImage.getWidth()*1.5f), stage.getHeight()/2f-((sspImage.getHeight()/2)*0.4f));
        gameImages.put(-1, sspImage);

        miniGame.put(1,new FourWins());
        Image vgImage = new Image(miniGame.get(1).getSplash());
        vgImage.setSize(stage.getWidth()*0.5f, (stage.getWidth()*0.5f));
        vgImage.setPosition(stage.getWidth() + vgImage.getWidth()/2, stage.getHeight()/2f-((vgImage.getHeight()/2)*0.4f));
        gameImages.put(1, vgImage);

        stage.addActor(tttImage);
        stage.addActor(sspImage);
        stage.addActor(vgImage);
         */
    }

    public void paginate(){
        if(isPaginating){
            float distance = finalX - gameImages.get(finish).getX();
            float speed;
            if(finish < current) {
                speed = getSpeed(distance);
            }else{
                speed = getSpeed(distance*-1);
            }
            for(Image image : gameImages.values()){
                if(finish < current) {
                    image.setX(image.getX()+speed);
                }else{
                    image.setX(image.getX()-speed);
                }
            }
            if(distance == 0){
                isPaginating = false;
                current = finish;
                finish = 0;
            }
        }
    }

    float startSpeed = 30;
    float currentSpeed;

    public float getSpeed(float distance){
        if(distance >= currentSpeed){
            return currentSpeed;
        }else{
            currentSpeed-=0.25f;
            return getSpeed(distance);
        }
    }

    public boolean hasNext(){
        return current < max;
    }

    public boolean hasPrev(){
        return current > min;
    }

    public void next(){
        if(hasNext() && !isPaginating){
            finish = current+1;
            isPaginating = true;
            currentSpeed = startSpeed;
        }
    }

    public void prev(){
        if(hasPrev() && !isPaginating){
            finish = current-1;
            isPaginating = true;
            currentSpeed = startSpeed;
        }
    }

    float minY = -2.5f;
    float maxY = 2.5f;
    float currentY = 0f;
    float direction = -0.25f;

    public void waiting(){
        Image image = gameImages.get(current);
        if(direction > 0){
            if(currentY < maxY){
                currentY+=direction;
                image.setY(image.getY()+direction);
            }else{
                direction*=-1;
            }
        }else{
            if(currentY > minY){
                currentY+=direction;
                image.setY(image.getY()+direction);
            }else{
                direction*=-1;
            }
        }
    }

    public void reset(){
        direction = -0.25f;

        Image image = gameImages.get(current);
        image.setY(stage.getHeight()/2f-50);
    }

    public int getCurrent() {
        return current;
    }

    public int getCurrentMatchType(){
        return miniGame.containsKey(current) ? miniGame.get(current).getMatchType() : 0;
    }

}
