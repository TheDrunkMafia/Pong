package pong.sprites;

import pong.Game;
import pong.net.packets.BallPacket;
import pong.net.ChannelHandler;
import pong.sprites.players.Racquet;
import pong.states.game.GameState;

import java.awt.*;

/**
 * Created by 145413 on 28/04/2015.
 *
 * The ball which bounces around in the game
 */
public class Ball extends Sprite implements Collidable {
    public static final int DIAMETER = 30;

    public Ball() {
        super((Game.getInstance().getCurrentState(GameState.class).getWidth() / 2) - DIAMETER, (Game.getInstance().getCurrentState(GameState.class).getHeight() / 2) - DIAMETER);

        xVelocity = rand.nextBoolean() ? 1 : -1;
        yVelocity = rand.nextBoolean() ? 1 : -1;
    }

    @Override
    public void move() {
        int tempX = (int) (xPosition + xVelocity);
        int tempY = (int) (yPosition + yVelocity);

        xVelocity = tempX < 0 ? speed : tempX > Game.getInstance().getCurrentState(GameState.class).getWidth() - DIAMETER ? -speed : xVelocity;
        yVelocity = tempY < 0 ? speed : tempY > Game.getInstance().getCurrentState(GameState.class).getHeight() - DIAMETER ? -speed : yVelocity;

        xPosition = tempX;
        yPosition = tempY;

        if (ChannelHandler.handler != null && (xPosition != lastXPosition || yPosition != lastYPosition || xVelocity != lastXVelocity || yVelocity != lastYVelocity))
            ChannelHandler.handler.sendPacket(new BallPacket(this));
    }

    public void paint(Graphics2D g) {
        g.fillOval(xPosition, yPosition, DIAMETER, DIAMETER);
    }

    public Rectangle getBounds() {
        return new Rectangle(xPosition, yPosition, DIAMETER, DIAMETER);
    }

    public void onCollide(Sprite collision) {
        if (collision instanceof Racquet)
            yVelocity = yPosition > collision.yPosition ? 1 : -1;
    }
}
