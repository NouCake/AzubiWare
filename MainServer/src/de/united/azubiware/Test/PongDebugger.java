package de.united.azubiware.Test;

import de.united.azubiware.Games.Pong.Pong;
import org.dyn4j.collision.Fixture;
import org.dyn4j.dynamics.Body;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class PongDebugger {
    public static void StartPongDebug(Pong pong){
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        //frame.setUndecorated(true);

        JPanel p = new JPanel(){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                float scale = getWidth()/ Pong.worldWidth;
                Graphics2D g2d = (Graphics2D)g;
                for(Body body : pong.world.getBodies()){
                    AffineTransform ot = g2d.getTransform();

                    AffineTransform lt = new AffineTransform();
                    lt.translate(body.getTransform().getTranslationX() * scale, body.getTransform().getTranslationY() * scale);
                    lt.rotate(body.getTransform().getRotationAngle());
                    g2d.transform(lt);
                    for(Fixture f : body.getFixtures()){
                        Graphics2DRenderer.render(g2d, f.getShape(), scale, Color.BLUE);
                    }
                    g2d.setTransform(ot);
                }
            }
        };
        p.setPreferredSize(new Dimension(450, 600));
        frame.add(p);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.pack();
        new Thread(() -> {
            while(true){
                frame.repaint();
                try {
                    Thread.sleep((long)(Pong.updateTime*1000));
                    pong.step();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
