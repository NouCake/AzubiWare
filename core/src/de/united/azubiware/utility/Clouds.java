package de.united.azubiware.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Clouds {

    private List<Image> clouds;
    private Stage stage;

    public Clouds(Stage stage){
        this.stage = stage;
        createClouds();
    }

    public void createClouds(){
        clouds = new ArrayList<>();
        int cloudAmount = 4+new Random().nextInt(4);
        for(int i = 0; i < cloudAmount; i++){
            Texture texture = new Texture(Gdx.files.internal("backgrounds/clouds/cloud" + (new Random().nextInt(8)+1) + ".png"));
            Image image = new Image(texture);
            image.setSize(90f, 90f);
            image.setPosition(stage.getWidth()/2f, stage.getHeight()/2f);
            if(new Random().nextInt(30) >= 14){
                image.setPosition(stage.getWidth()/2f-(new Random().nextInt((int) (stage.getWidth()/2))+2), stage.getHeight()-(100+(new Random().nextInt(10))));
            }else{
                image.setPosition(stage.getWidth()/2f+(new Random().nextInt((int) (stage.getWidth()/2))+2), stage.getHeight()-(100+(new Random().nextInt(10))));
            }
            clouds.add(image);
            stage.addActor(image);
        }
    }

    public void moveClouds(){
        for(Image cloud : clouds){
            if(cloud.getX() < stage.getWidth()+95) {
                cloud.setX(cloud.getX() + 0.15f);
            }else{
                cloud.setX(-100);
            }
        }
    }

}
