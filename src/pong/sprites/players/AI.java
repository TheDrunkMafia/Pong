package pong.sprites.players;

import pong.sprites.Ball;
import pong.sprites.Score;

public class AI extends Racquet {

    public AI(String name, Score score, int posY) {
        super(name, score, posY);
    }

    public static int clamp(int val, int maxClamp, int minClamp) {
        return Math.max(minClamp, Math.min(val, maxClamp));
    }

    @Override
    public void move() {
        Ball b = getBall();
        if (b == null)
            return;

        xVelocity = clamp(b.xPosition - xPosition, speed, -speed);
        super.move();
    }
}
