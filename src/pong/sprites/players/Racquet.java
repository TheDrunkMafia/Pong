package pong.sprites.players;

import pong.sprites.Collidable;
import pong.Game;
import pong.sprites.Ball;
import pong.sprites.DeadZone;
import pong.sprites.Score;
import pong.sprites.Sprite;
import pong.states.game.GameState;

import java.awt.*;

public class Racquet extends Sprite implements Collidable {

    public static final int WIDTH = 60, HEIGHT = 10;

    private String name;
    private Score score;

    private boolean ballCollided;

    public Racquet() {
    }

    public Racquet(String name, Score score, int yPos) {
        super(Game.getInstance().getCurrentState(GameState.class).getWidth() / 2 - WIDTH, yPos);

        this.name = name;
        this.score = score;
        new DeadZone(this, yPos > (Game.getInstance().getCurrentState(GameState.class).getHeight() / 2) ? yPos + 15 : 0);
    }

    @Override
    public void move() {
        speed = getBall().speed;
        if (speed > 1)
            speed /= 2;

        int tempX = (int) (xPosition + xVelocity);
        xPosition = tempX > 0 && tempX < Game.getInstance().getCurrentState(GameState.class).getWidth() - WIDTH ? tempX : xPosition;

    }

    @Override
    public void paint(Graphics2D g) {
        g.fillRect(xPosition, yPosition, WIDTH, HEIGHT);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(xPosition, yPosition, WIDTH, HEIGHT);
    }

    public void onCollide(Sprite collision) {
        if (collision instanceof Ball) {
            yVelocity = yPosition > collision.yPosition ? 1 : -1;
            ballCollided = true;
        }
    }

    public void velocityHasChanged() {
        if (ballCollided) {
            getBall().speed += 1;
            ballCollided = false;
        }
    }

    public String getName() {
        return name;
    }

    public Score getScore() {
        return score;
    }

    public Ball getBall() {
        for (Sprite b : spritePool)
            if (b instanceof Ball)
                return (Ball) b;
        return null;
    }
}
