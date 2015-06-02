package pong.net.packets;

import pong.net.IMessage;
import pong.sprites.Sprite;
import pong.sprites.network.NetworkPlayer;
import pong.sprites.players.Racquet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by 145413 on 10/03/2015.
 *
 * Syncs the position of the network racquet in the pool
 */
public class RacquetPacket implements IMessage {

    private Racquet racquet;

    public RacquetPacket() {
    }

    public RacquetPacket(Racquet racquet) {
        this.racquet = racquet;
    }

    @Override
    public void toBytes(ObjectOutputStream output) throws IOException {
        output.writeInt(racquet.xPosition);
        output.writeInt(racquet.yPosition);
        output.writeFloat(racquet.xVelocity);
        output.writeFloat(racquet.yVelocity);
    }

    @Override
    public void fromBytes(ObjectInputStream input) throws IOException {
        int x = input.readInt(), y = input.readInt();
        float xVelocity = input.readFloat(), yVelocity = input.readFloat();

        for (int i = 0; i < Sprite.spritePool.size(); i++) {
            Sprite sprite = Sprite.spritePool.get(i);
            if (sprite != null && sprite instanceof NetworkPlayer) {
                sprite.xPosition = x;
                sprite.yPosition = y;
                sprite.xVelocity = xVelocity;
                sprite.yVelocity = yVelocity;
                return;
            }
        }
    }
}
