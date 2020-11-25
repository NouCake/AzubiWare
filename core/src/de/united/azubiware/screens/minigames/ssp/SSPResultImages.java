package de.united.azubiware.screens.minigames.ssp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.HashMap;

public class SSPResultImages extends Group {

    private final float stageWidth;
    private final float stageHeight;

    private HashMap<Integer, Image> greenImages;
    private HashMap<Integer, Image> orangeImages;

    public SSPResultImages(float stageWith, float stageHeight){
        this.stageWidth = stageWith;
        this.stageHeight = stageHeight;

        createGreenImages();
        createOrangeImages();

        setSize(greenImages.get(0).getWidth()*3, greenImages.get(0).getHeight());

        addActors();
    }

    private void addActors(){
        for(Image image : greenImages.values()){
            addActor(image);
        }

        for(Image image : orangeImages.values()){
            addActor(image);
        }
    }

    private void createGreenImages(){
        greenImages = new HashMap<>();

        for(SSPValue value : SSPValue.values()){
            Image image = createImage(value.toString().toLowerCase() + "_green");

            image.setPosition(0, 0);
            image.setVisible(false);

            greenImages.put(value.ordinal(), image);
        }
    }

    private void createOrangeImages(){
        orangeImages     = new HashMap<>();

        for(SSPValue value : SSPValue.values()){
            Image image = createImage(value.toString().toLowerCase() + "_orange");

            image.setPosition(image.getWidth()*2, 0);
            image.setVisible(false);

            orangeImages.put(value.ordinal(), image);
        }
    }

    private Image createImage(String imageName){
        Image image = new Image(new Texture(Gdx.files.internal("games/ssp/result/" + imageName + ".png")));

        image.setWidth(stageWidth*0.25f);
        image.setHeight(image.getWidth());

        return image;
    }

    public void show(int pickType, int enemyPickType){
        greenImages.get(pickType).setVisible(true);
        orangeImages.get(enemyPickType).setVisible(true);
    }

    public void hide(){
        for(Image image : greenImages.values()){
            image.setVisible(false);
        }

        for(Image image : orangeImages.values()){
            image.setVisible(false);
        }
    }
}
