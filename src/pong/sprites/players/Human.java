package pong.sprites.players;

import pong.net.ChannelHandler;
import pong.net.packets.RacquetPacket;
import pong.sprites.Score;

import static pong.states.game.GameState.manager;

public class Human extends Racquet {

    private int left, right;


    public Human(String name, Score score, int yPos, int left, int right) {
        super(name, score, yPos);

        this.left = left;
        this.right = right;
    }

    @Override
    public void move() {
        if (manager == null)
            return;
        xVelocity = manager.isKeyDown(right) ? speed : manager.isKeyDown(left) ? -speed : 0;

        super.move();
        if (ChannelHandler.handler != null && (xPosition != lastXPosition || yPosition != lastYPosition || xVelocity != lastXVelocity || yVelocity != lastYVelocity))
            ChannelHandler.handler.sendPacket(new RacquetPacket(this));
    }
}
