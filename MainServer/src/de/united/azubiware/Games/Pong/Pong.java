package de.united.azubiware.Games.Pong;

import org.dyn4j.collision.Fixture;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.Settings;
import org.dyn4j.dynamics.contact.ContactConstraint;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.dyn4j.world.ContactCollisionData;
import org.dyn4j.world.World;
import org.dyn4j.world.listener.ContactListenerAdapter;

public class Pong {

    public static final float updateTime = 0.016f;
    private static final float stepTime = 0.004f;

    public static final int worldWidth = 150;
    public static final int worldHeight = 200;

    private final static int playerBarWidth = 40;
    private final static int playerBarHeight = 5;

    private final static float ballVelEps = 1;

    private final static float ballStartSpeed = 150;
    private final static float ballContactSpeedUp = 25;

    private float ballSpeed = ballStartSpeed;

    private final Body ball;
    private final Body bounds;
    private Fixture top;
    private Fixture bot;

    private final Body p1;
    private int scoreP1 = 0;

    private final Body p2;
    private int scoreP2 = 0;

    public World<Body> world;
    private final IPongScoreListener listener;

    public Pong(IPongScoreListener listener){
        this.listener = listener;
        world = new World<>();
        world.setGravity(0, 0);
        world.getSettings().setMaximumTranslation(10);

        ball = new Body();
        ball.addFixture(Geometry.createCircle(5), BodyFixture.DEFAULT_DENSITY, 0, 1.0f);
        ball.setMass(MassType.NORMAL);
        ball.translate(worldWidth*0.5f, worldHeight*0.5f);
        ball.setLinearVelocity(createRandomVector().multiply(ballSpeed));

        world.addBody(ball);
        bounds = createBorders();
        world.addBody(bounds);

        p1 = createPlayerBody((float)Math.PI);
        p2 = createPlayerBody(0);
        p1.translate(0, playerBarHeight * 0.5f);
        p2.translate(0, worldHeight - playerBarHeight * 0.5f);

        world.addBody(p1);
        world.addBody(p2);

        addContactListener();
    }


    public void step(){
        world.step((int)(updateTime/stepTime), stepTime);
        float balVelDif = (float)ball.getLinearVelocity().getMagnitude() - ballSpeed;
        if(balVelDif * balVelDif > ballVelEps){
            ball.setLinearVelocity(ball.getLinearVelocity().getNormalized().multiply(ballSpeed));
        }
    }
    public void updatePlayer1(float pos){
        updatePlayerBar(p1, pos);
    }
    public void updatePlayer2(float pos){
        updatePlayerBar(p2, pos);
    }

    private void updatePlayerBar(Body playerBar, float pos){
        pos = Math.min(1, Math.max(0, pos));

        float min = playerBarWidth*0.5f;
        float max = worldWidth - playerBarWidth*0.5f;
        float dif = max - min;
        float newX = min + dif * pos;
        playerBar.getTransform().setTranslationX(newX);
    }

    private void onScoreChanged(){
        if(listener != null) listener.onScoreChanged(scoreP1, scoreP2);
        ballSpeed = ballStartSpeed;
    }

    private void onBallCollision(Body other, Fixture fixture){
        if(other == bounds){
            if(fixture == top){
                scoreP1++;
                onScoreChanged();
            } else if(fixture == bot){
                scoreP2++;
                onScoreChanged();
            } else {
                ballSpeed += ballContactSpeedUp;
            }
        }
    }

    private Body createBorders(){
        final float borderThickness = 2;
        Body borders = new Body();
        var left = Geometry.createRectangle(borderThickness, worldHeight + 2* borderThickness);
        var right = Geometry.createRectangle(borderThickness, worldHeight + 2* borderThickness);
        var up = Geometry.createRectangle(worldWidth, borderThickness);
        var down = Geometry.createRectangle(worldWidth, borderThickness);


        left.translate(borderThickness*0.5f, worldHeight*0.5f + borderThickness);
        right.translate(worldWidth - borderThickness*0.5f, worldHeight*0.5f + borderThickness);
        up.translate(worldWidth*0.5f, worldHeight - borderThickness*0.5f);
        down.translate(worldWidth*0.5f, borderThickness*0.5f);

        borders.addFixture(left);
        borders.addFixture(right);
        borders.addFixture(up);
        top = borders.getFixture(borders.getFixtureCount()-1);
        borders.addFixture(down);
        bot = borders.getFixture(borders.getFixtureCount()-1);

        borders.setMass(MassType.INFINITE);
        return borders;
    }
    private Body createPlayerBody(float rot){
        Body player = new Body();
        player.addFixture(Geometry.createEllipse(playerBarWidth, playerBarHeight*2));
        var base = Geometry.createRectangle(playerBarWidth, playerBarHeight);
        base.translate(0, playerBarHeight*0.5f);
        base.rotate(rot);
        player.addFixture(base);
        player.setMassType(MassType.INFINITE);
        player.translate(worldWidth*0.5f, 0.0f);
        return player;
    }
    private Vector2 createRandomVector(){
        return new Vector2(Math.random()*2-1, Math.random()*2-1).getNormalized();
    }
    private void addContactListener(){
        world.addContactListener(new ContactListenerAdapter<>(){
            @Override
            public void collision(ContactCollisionData<Body> collision, ContactConstraint<Body> contactConstraint) {
                super.collision(collision, contactConstraint);
                if(collision.getBody1() == ball){
                    onBallCollision(collision.getBody2(), collision.getFixture2());
                } else if(collision.getBody2() == ball){
                    onBallCollision(collision.getBody1(), collision.getFixture1());
                }
            }
        });
    }

    public Vector2 getRelativeBallPosition(){
        return new Vector2(ball.getTransform().getTranslationX()/worldWidth, ball.getTransform().getTranslationY()/worldHeight);
    }

    private float getRelativePlayerPos(Body player){
        float min = playerBarWidth*0.5f;
        float max = worldWidth - playerBarWidth*0.5f;
        return ((float)player.getTransform().getTranslationX() - min) / (max-min);
    }

    public float getRelativeP1Pos(){
        return getRelativePlayerPos(p1);
    }
    public float getRelativeP2Pos(){
        return getRelativePlayerPos(p2);
    }

}
