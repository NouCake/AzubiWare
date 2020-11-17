package de.united.azubiware.utility;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.HashMap;

public class MiniGamePaginator {

    private Stage stage;

    private int min = -1;
    private int max = 0;

    private int current = 0;
    private int finish = 0;

    private final float finalX;

    private boolean isPaginating = false;

    private HashMap<Integer, Image> miniGames = new HashMap<>();

    public MiniGamePaginator(Stage stage){
        this.stage = stage;
        this.finalX = (stage.getWidth()/2f-150);

        create();
    }

    public void create(){
        Texture tttTexture = new Texture("games/TicTacToe.png");
        Image tttImage = new Image(tttTexture);
        tttImage.setPosition(stage.getWidth()/2f-150, stage.getHeight()/2f-50);
        miniGames.put(0, tttImage);

        Texture sspTexture = new Texture("games/SchereSteinPapier.png");
        Image sspImage = new Image(sspTexture);
        sspImage.setPosition(-300, stage.getHeight()/2f-50);
        miniGames.put(-1, sspImage);

        stage.addActor(tttImage);
        stage.addActor(sspImage);
    }

    public void paginate(){
        if(isPaginating){
            float distance = finalX - miniGames.get(finish).getX();
            float speed;
            if(finish < current) {
                speed = getSpeed(distance);
            }else{
                speed = getSpeed(distance*-1);
            }
            for(Image image : miniGames.values()){
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
        Image image = miniGames.get(current);
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
        Image image = miniGames.get(current);
        image.setY(stage.getHeight()/2f-50);
    }
}
