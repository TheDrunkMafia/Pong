package pong.states.menu;

import pong.Game;
import pong.sprites.Button;
import pong.sprites.Sprite;
import pong.states.game.ClientGameState;
import pong.states.game.ServerGameState;
import pong.states.State;

import javax.swing.*;
import java.awt.*;

public class MultiplayerMenuState extends JPanel implements State {

    Button join, start, back;

    public void start() {
        setFocusable(true);
        setVisible(true);
        Game.instance.frame.add(this);

        join = new Button("Join Game",  new Runnable() {public void run() { Game.instance.attachState(new ClientGameState()); }}, Color.BLACK, Color.GRAY, Game.instance.getWidth() / 2 - 250 / 2, 100, 250, 60);
        start = new Button("Create Game", new Runnable() {public void run() { Game.instance.attachState(new ServerGameState()); }}, Color.BLACK, Color.GRAY, Game.instance.getWidth() / 2 - 250 / 2, 180, 250, 60);
        back = new Button("Back",  new Runnable() {public void run() { Game.instance.attachState(new PlayMenuState()); }}, Color.BLACK, Color.GRAY, Game.instance.getWidth() / 2 - 250 / 2, 260, 250, 60);
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
        String text = "PING PONG";

        g.drawString(text, Game.instance.getWidth() / 2 - text.length() * 15, 60);

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
