package pong.sprites.network;

import pong.sprites.Ball;

import java.awt.*;

/**
 * Created by 145413 on 28/04/2015.
 *
 * The network version of the ball, it is controlled via the packets sent from the server
 * the collisions are calculated server side and sent to the client. If the stream is closed
 * and the client's version of the game does not close, the ball will clip though the wall
 */
public class NetworkBall extends Ball {
    @Override
    public void move() {
        xPosition = (int) (xPosition + xVelocity);
        yPosition = (int) (yPosition + yVelocity);
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.blue);
        super.paint(g);
    }
}
