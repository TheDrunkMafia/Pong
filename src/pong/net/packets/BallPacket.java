package pong.net.packets;

import pong.net.IMessage;
import pong.sprites.Ball;
import pong.sprites.network.NetworkBall;
import pong.sprites.Sprite;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by 145413 on 10/03/2015.
 *
 * Syncs the balls current positions and its velocity, this is ONLY sent on changeVel to keep the stream clean and not bog down the handler
 */
public class BallPacket implements IMessage {

    private Ball ball;

    public BallPacket() {
    }

    public BallPacket(Ball ball) {
        this.ball = ball;
    }

    @Override
    public void toBytes(ObjectOutputStream output) throws IOException {
        output.writeInt(ball.xPosition);
        output.writeInt(ball.yPosition);
        output.writeFloat(ball.xVelocity);
        output.writeFloat(ball.yVelocity);
    }

    @Override
    public void fromBytes(ObjectInputStream input) throws IOException {
        int x = input.readInt(), y = input.readInt();
        float xVelocity = input.readFloat(), yVelocity = input.readFloat();

        for (int i = 0; i < Sprite.spritePool.size(); i++) {
            Sprite sprite = Sprite.spritePool.get(i);
            if (sprite != null && sprite instanceof NetworkBall) {
                sprite.xPosition = x;
                sprite.yPosition = y;
                sprite.xVelocity = xVelocity;
                sprite.yVelocity = yVelocity;
                return;
            }
        }
    }
}
