package pong.sprites;

import pong.Game;
import pong.sprites.players.Racquet;
import pong.states.game.ClientGameState;
import pong.states.game.GameState;

import java.awt.*;

/**
 * Created by 145413 on 28/04/2015.
 *
 * Used to tell when ball hits a players side
 */
public class DeadZone extends Sprite implements Collidable {

    public int width, height;
    private Racquet racquet;

    public DeadZone(Racquet racquet, int yPosition) {
        super(0, yPosition);
        this.racquet = racquet;

        width = Game.instance.getCurrentState(GameState.class).getWidth();
        height = 20;
    }

    @Override
    protected void move() {
    }

    @Override
    public void paint(Graphics2D g) {
        g.fillRect(xPosition, yPosition, width, height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(xPosition, yPosition, width, height);
    }

    public void onCollide(Sprite collision) {
        if (collision instanceof Ball) {
            GameState game = Game.instance.getCurrentState(GameState.class);
            if(game instanceof ClientGameState)
                return;
            racquet.getScore().increment();
            for (Racquet player : game.players)
                player.xPosition = game.getWidth() / 2 - Racquet.WIDTH;

            game.ball.xPosition = (Game.instance.getCurrentState(GameState.class).getWidth() / 2) - Ball.DIAMETER;
            game.ball.yPosition = (Game.instance.getCurrentState(GameState.class).getHeight() / 2) - Ball.DIAMETER;
            game.ball.speed = 1;
        }
    }
}
