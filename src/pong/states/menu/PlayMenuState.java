package pong.states.menu;

import pong.Game;
import pong.sprites.Button;
import pong.sprites.Sprite;
import pong.states.game.ClientGameState;
import pong.states.game.GameState;
import pong.states.State;

import javax.swing.*;
import java.awt.*;

public class PlayMenuState extends JPanel implements State {

    Button single, multi, back;

    public void start() {
        setFocusable(true);
        setVisible(true);
        Game.getInstance().frame.add(this);

        single = new Button("SinglePlayer", new Runnable() {public void run() { Game.getInstance().attachState(new GameState()); }}, Color.BLACK, Color.GRAY, Game.getInstance().getWidth() / 2 - 250 / 2, 100, 250, 60);
        multi = new Button("MultiPlayer", new Runnable() {public void run() { Game.getInstance().attachState(new MultiplayerMenuState()); }}, Color.BLACK, Color.GRAY, Game.getInstance().getWidth() / 2 - 250 / 2, 180, 250, 60);
        back = new Button("Back", new Runnable() {public void run() { Game.getInstance().attachState(new MenuState()); }}, Color.BLACK, Color.GRAY, Game.getInstance().getWidth() / 2 - 250 / 2, 260, 250, 60);
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

        for (Sprite s : Sprite.spritePool)
            s.paint(g2d);
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
