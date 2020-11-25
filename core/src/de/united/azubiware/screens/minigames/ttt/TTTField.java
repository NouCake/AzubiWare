package de.united.azubiware.screens.minigames.ttt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TTTField {

    private Stage stage;

    private HashMap<Vector2, Vector2> linePoints;
    private HashMap<Integer, HashMap<Integer, TTTTPostition>> postitions;
    private ArrayList<TTTTPostition> positionList;

    private float lineLength;
    private float lineThickness;
    private float fieldSize;

    public TTTField(Stage stage){
        this.stage = stage;

        lineLength = stage.getWidth()*0.45f;
        fieldSize = Math.round((lineLength-10f)/3);
        lineLength = fieldSize*3+10;
        lineThickness = 5f;

        generateLines();
        generatePostitions();
    }

    public void generateLines(){
        linePoints = new HashMap<>();

        float centerX = stage.getWidth()/2;
        float centerY = stage.getHeight()/1.5f;

        linePoints.put(new Vector2(centerX-lineLength/2+(lineThickness/2), centerY-(fieldSize/2+lineThickness/2)), new Vector2(lineLength, lineThickness));
        linePoints.put(new Vector2(centerX-lineLength/2+(lineThickness/2), centerY+(fieldSize/2+lineThickness/2)), new Vector2(lineLength, lineThickness));

        linePoints.put(new Vector2(centerX-(fieldSize/2+lineThickness/2), centerY-lineLength/2+(lineThickness/2)), new Vector2(lineThickness, lineLength));
        linePoints.put(new Vector2(centerX+(fieldSize/2+lineThickness/2), centerY-lineLength/2+(lineThickness/2)), new Vector2(lineThickness, lineLength));
    }

    public void generatePostitions(){
        postitions = new HashMap<>();
        positionList = new ArrayList<>();

        float centerX = stage.getWidth()/2;
        float centerY = stage.getHeight()/1.5f;

        for (int y = 0; y < 3; y++) {
            HashMap<Integer, TTTTPostition> xPositions = new HashMap<>();
            float fieldLineY = (1 - y) * (fieldSize / 2 + lineThickness);
            float fieldSizeDivY = (1 - y) * (fieldSize / 2);
            float posY = centerY + fieldLineY + fieldSizeDivY;
            for (int x = 0; x < 3; x++) {
                float multiplier = x == 2 ? 1 : -1;
                if (x == 1)
                    multiplier = 0;
                float fieldLineX = (multiplier) * (fieldSize / 2 + lineThickness);
                float fieldSizeDivX = (multiplier) * (fieldSize / 2);
                float posX = centerX + fieldLineX + fieldSizeDivX;

                Vector2 center = new Vector2(posX, posY);
                Vector2 max = new Vector2(posX + fieldSize / 2, posY + fieldSize / 2);
                Vector2 min = new Vector2(posX - fieldSize / 2, posY - fieldSize / 2);

                TTTTPostition ticTacToePostition = new TTTTPostition(min, max, center, x, y);
                xPositions.put(x, ticTacToePostition);
                positionList.add(ticTacToePostition);
            }
            postitions.put(y, xPositions);
        }
    }

    public void draw(){
        Texture texture = new Texture(Gdx.files.internal("line.png"));
        for(Vector2 pos : linePoints.keySet()){
            Vector2 scale = linePoints.get(pos);
            stage.getBatch().draw(texture, pos.x, pos.y, scale.x, scale.y);
        }

        Texture cross = new Texture(Gdx.files.internal("games/ttt/blue_cross.png"));
        Texture circle = new Texture(Gdx.files.internal("games/ttt/red_circle.png"));
        List<TTTTPostition> positionList = this.positionList.stream().filter(new Predicate<TTTTPostition>() {
            @Override
            public boolean test(TTTTPostition ticTacToeField) {
                return ticTacToeField.getState() != 0;
            }
        }).collect(Collectors.toList());
        for(TTTTPostition postition : positionList){
            if(postition.getState() == 1){
                stage.getBatch().draw(cross, postition.getCenter().x-(fieldSize/2-lineThickness), postition.getCenter().y-(fieldSize/2-lineThickness), fieldSize-lineThickness*2, fieldSize-lineThickness*2);
            }else{
                stage.getBatch().draw(circle, postition.getCenter().x-(fieldSize/2-lineThickness), postition.getCenter().y-(fieldSize/2-lineThickness), fieldSize-lineThickness*2, fieldSize-lineThickness*2);
            }
        }
    }

    public TTTTPostition findPosition(float inputX, float inputY){
        TTTTPostition result = null;

        List<TTTTPostition> filterdPositionList = positionList.stream().filter(new Predicate<TTTTPostition>() {
            @Override
            public boolean test(TTTTPostition ticTacToeField) {
                float fieldMaxX = ticTacToeField.getMax().x;
                float fieldMaxY = ticTacToeField.getMax().y;
                float fieldMinX = ticTacToeField.getMin().x;
                float fieldMinY = ticTacToeField.getMin().y;

                return ticTacToeField.getState() == 0 && (inputX >= fieldMinX && inputX <= fieldMaxX && inputY >= fieldMinY && inputY <= fieldMaxY);
            }
        }).collect(Collectors.toList());

        if(!filterdPositionList.isEmpty()){
            result = filterdPositionList.get(0);
        }

        return result;
    }

    public TTTTPostition findPositionByVector(int posX, int posY){
        HashMap<Integer, TTTTPostition> xPostitions = postitions.getOrDefault(posY, null);
        if(xPostitions != null){
            return xPostitions.getOrDefault(posX, null);
        }
        return null;
    }

}