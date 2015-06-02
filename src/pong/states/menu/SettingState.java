package pong.states.menu;

import pong.Game;
import pong.sprites.Button;
import pong.sprites.Sprite;
import pong.states.State;
import pong.states.game.ClientGameState;
import pong.states.game.ServerGameState;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 145413 on 28/04/2015.
 */
public class SettingState extends JPanel implements State {

    public static int scoreLimit = 10;

    Button incrmentScore, decrementScore, back;

    public void start() {
        setFocusable(true);
        setVisible(true);
        Game.instance.frame.add(this);

        incrmentScore = new Button(">",  new Runnable() {public void run() { SettingState.scoreLimit += scoreLimit < 20 ? 1 : 0; }} , Color.BLACK, Color.GRAY, Game.instance.getWidth() - 100, 180, 60, 60);
        decrementScore = new Button("<", new Runnable() {public void run() { SettingState.scoreLimit -= scoreLimit > 1 ? 1 : 0; }} , Color.BLACK, Color.GRAY, Game.instance.getWidth() - 260, 180, 60, 60);

        back = new Button("Back",  new Runnable() {public void run() { Game.instance.attachState(new MenuState()); }}, Color.BLACK, Color.GRAY,  Game.instance.getWidth() / 2 - 250 / 2, Game.instance.getHeight() - 90, 250, 60);
    }

    public void update() {
        repaint();
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.GRAY);
        g.setFont(new Font("Verdana", Font.BOLD, 40));
        String text = "Settings";

        g.drawString(text, Game.instance.getWidth() / 2 - text.length() * 15, 60);
        g.drawString("-=Score=-", Game.instance.getWidth() - 270, 170);
        g.drawString(scoreLimit + "", Game.instance.getWidth() - 180, 230);

        for (Sprite s : Sprite.spritePool)
            s.paint(g2d);
    }

    @Override
    public int getWidth() {
        return Game.instance.getWidth();
    }

    @Override
    public int getHeight() {
        return Game.instance.getHeight();
    }

    public void end() {
        setVisible(false);
    }

    @Override
    public void close() {

    }
}