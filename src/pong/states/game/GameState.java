package pong.states.game;

import pong.sprites.Collidable;
import pong.Game;
import pong.InputManager;
import pong.sprites.Ball;
import pong.sprites.Score;
import pong.sprites.Sprite;
import pong.sprites.players.AI;
import pong.sprites.players.Human;
import pong.sprites.players.Racquet;
import pong.states.State;
import pong.sprites.Button;
import pong.states.menu.PlayMenuState;
import pong.states.menu.SettingState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by 145413 on 28/04/2015.
 *
 * The singleplayer game state
 */
public class GameState extends JPanel implements State {

    public ArrayList<Racquet> players = new ArrayList<Racquet>();
    public Ball ball;

    public int count = 4;
    public boolean countdown, paused;
    private Button back;

    public static InputManager manager = InputManager.getInstance();

    public void start() {
        countdown = true;

        setFocusable(true);
        setVisible(true);

        ball = new Ball();
        players.add(new AI("Player 1", new Score("Player 1", 0, getHeight() / 2 - 20), 330));
        players.add(new Human("Player 2", new Score("Player 2", getWidth() - 30, getHeight() / 2 - 20), 30, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT));

        Game.instance.frame.add(this);
    }

    boolean keyDown;

    public void update() {
        if(manager.isKeyDown(KeyEvent.VK_ESCAPE) || manager.isKeyDown(KeyEvent.VK_PAUSE)) {
            if(!keyDown) {
                paused = !paused;
                keyDown = true;
                if(paused) onPause();
                else onUnPause();
            }
        }else keyDown = false;

        if (countdown) {
            repaint();
            try {
                Thread.sleep(1000);
                count--;
                if (count == 0)
                    countdown = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            if(!paused) {
                move();
                checkScore();
            }
            revalidate();
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void checkScore(){
        for(Racquet player : players){
            Score score = player.getScore();
            if(score.score >= SettingState.scoreLimit){
                JOptionPane.showMessageDialog(Game.instance.frame, score.getName() + " has won by reaching: " + SettingState.scoreLimit);
                Game.instance.attachState(new PlayMenuState());
            }
        }
    }

    private void move() {
        for (int i = 0; i < Sprite.spritePool.size(); i++) {
            Sprite s = Sprite.spritePool.get(i);
            s.update();

            if (s instanceof Collidable)
                for (int ix = 0; ix < Sprite.spritePool.size(); ix++) {
                    if (ix == i)
                        continue;

                    Sprite c = Sprite.spritePool.get(ix);
                    if (c instanceof Collidable && s.collision(c))
                        ((Collidable) s).onCollide(c);
                }
        }
    }

    /**
     * Called when ESC is pressed
     */
   public void onPause(){
       back = new Button("Quit", new Runnable() {public void run() { Game.instance.getCurrentState(GameState.class).quit(); }}, Color.BLACK, Color.GRAY, Game.instance.getWidth() / 2 - 250 / 2, 300, 250, 30);
   }

    public void onUnPause(){
        back.destroy();
    }

    /**
     * Called on each iteration of the game loop while paused
     */
    public void paused(Graphics g){
        if(back != null)
            back.paint((Graphics2D) g);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (countdown || paused) {
            g.setColor(Color.GRAY);
            g.setFont(new Font("Verdana", Font.BOLD, 30));
            String text = paused ? "Paused!" : count == 1 ? "Play!" : String.valueOf(count - 1);

            g.drawString(text, Game.instance.getWidth() / 2 - text.length() * 8, Game.instance.getHeight() / 2 + 10);
            if(paused)
                paused(g);
        } else {
            try {
                g2d.setColor(Color.GRAY);
                g2d.fillRect(40, getHeight() / 2 - 40, 60, 20);
                g2d.fillRect(120, getHeight() / 2 - 40, 60, 20);
                g2d.fillRect(200, getHeight() / 2 - 40, 60, 20);

                g2d.setColor(Color.BLACK);
                for (Sprite s : Sprite.spritePool)
                    s.paint(g2d);
            } catch (Exception e) {
            }
        }
    }

    public void quit(){
        Game.instance.attachState(new PlayMenuState());
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
