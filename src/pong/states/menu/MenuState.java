package pong.states.menu;

import pong.Game;
import pong.sprites.Button;
import pong.sprites.Sprite;
import pong.states.State;
import pong.states.game.ClientGameState;
import pong.states.game.ServerGameState;

import javax.swing.*;
import java.awt.*;

public class MenuState extends JPanel implements State {

    Button play, help, settings;

    public void start() {
        setFocusable(true);
        setVisible(true);
        Game.getInstance().frame.add(this);

        play = new Button("Play", new Runnable() {public void run() { Game.getInstance().attachState(new PlayMenuState()); }}, Color.BLACK, Color.GRAY, Game.getInstance().getWidth() / 2 - 250 / 2, 100, 250, 60);
        help = new Button("Help", new Runnable() {public void run() { Game.getInstance().attachState(new HelpMenuState()); }}, Color.BLACK, Color.GRAY, Game.getInstance().getWidth() / 2 - 250 / 2, 180, 250, 60);
        settings = new Button("Settings",  new Runnable() {public void run() { Game.getInstance().attachState(new SettingState()); }}, Color.BLACK, Color.GRAY, Game.getInstance().getWidth() / 2 - 250 / 2, 260, 250, 60);
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

        g.drawString(text, Game.getInstance().getWidth() / 2 - text.length() * 15, 60);

        for (int i = 0; i < Sprite.spritePool.size(); i++)
            Sprite.spritePool.get(i).paint(g2d);
    }

    @Override
    public int getWidth() {
        return Game.getInstance().getWidth();
    }

    @Override
    public int getHeight() {
        return Game.getInstance().getHeight();
    }

    public void end() {
        setVisible(false);
    }

    @Override
    public void close() {

    }
}
