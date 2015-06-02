package pong.states.menu;

import pong.Game;
import pong.sprites.Button;
import pong.sprites.Sprite;
import pong.states.State;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 145413 on 12/05/2015.
 */
public class HelpMenuState extends JPanel implements State {

    public static String[][] help = {
            {       "Welcome to Ping pong, one of the",
                    "most complicated game known to",
                    "man kind!",
                    "",
                    "If you are ready to try and ",
                    "cheat death, but watching a ",
                    "ball bounce around your screen",
                    "then read on, if not then....",
                    "You need to re think your life",
                    "choices and accept your inner ",
                    "pong!"
            },
            {       "--- SINGLEPLAYER ---",
                    "So in single player you play",
                    "against the AI, you control",
                    "a Racquet wither either the",
                    "A & D keys or Left & right ",
                    "arrow keys.",
                    "",
                    "To set the score of the game",
                    "go into the settings and set",
                    "the limit to whatever you ",
                    "want!",
            },
            {       "--- Multiplayer ---",
                    "If you are creating a game",
                    "select server and set your ip",
                    "if you dont know what your ip",
                    "is, then just leave it as ",
                    "localhost.",
                    "",
                    "Then set the port it needs to ",
                    "be open on the computer and",
                    "if you are playing over the",
                    " internet, then it needs to be",
                    " open in your router as well!"
            },
            {       "Continue... If you a joining a",
                    "game then you need two to know",
                    "two things, the server IP & port",
                    "If you know these two addresses",
                    "then go to network play and ",
                    "join a game, put the server ip",
                    "in first, then the port that ",
                    "the server is listening to.",
                    "Then you just have to confirm ",
                    "the setting and happy gaming!",
            }

    };

    int helpIndex;

    Button exit, back, forward;

    public void start() {
        setFocusable(true);
        setVisible(true);
        Game.instance.frame.add(this);

        exit = new Button("Back", new Runnable() {public void run() { Game.instance.attachState(new MenuState()); }}, Color.BLACK, Color.GRAY, Game.instance.getWidth() / 2 - 100 / 2, Game.instance.getHeight() - 90, 100, 60);

        back = new Button("<--", new Runnable() {public void run() { helpIndex += helpIndex > 0 ? -1 : 0; }}, Color.BLACK, Color.GRAY, 0, Game.instance.getHeight() - 90, 100, 60);
        forward = new Button("-->", new Runnable() {public void run() { helpIndex += helpIndex < help.length - 1 ? 1 : 0; }}, Color.BLACK, Color.GRAY, Game.instance.getWidth() - 100, Game.instance.getHeight() - 90, 100, 60);
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
        String text = "Help";

        g.drawString(text, Game.instance.getWidth() / 2 - text.length() * 15, 60);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Verdana", Font.BOLD, 15));
        String[] currentHelp = help[helpIndex];
        for(int i = 0; i < currentHelp.length; i++)
            g.drawString(currentHelp[i], 10, 110 + (18 * i));

        for (int i = 0; i < Sprite.spritePool.size(); i++) {
            Sprite sprite = Sprite.spritePool.get(i);
            if((sprite == back && helpIndex > 0) || (sprite == forward && helpIndex < help.length - 1) || (sprite != back && sprite != forward))
                sprite.paint(g2d);
        }
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
