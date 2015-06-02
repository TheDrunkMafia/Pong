package pong.net.packets;

import pong.net.IMessage;
import pong.sprites.Score;
import pong.sprites.Sprite;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by 145413 on 10/03/2015.
 *
 * Syncs the score, scoring is done server side
 */
public class ScorePacket implements IMessage {

    private Score score;

    public ScorePacket() {
    }

    public ScorePacket(Score score) {
        this.score = score;
    }

    @Override
    public void toBytes(ObjectOutputStream output) throws IOException {
        byte[] name = score.getName().getBytes();
        output.writeInt(name.length);
        for (byte c : name)
            output.writeByte(c);
        output.writeInt(score.score);
    }

    @Override
    public void fromBytes(ObjectInputStream input) throws IOException {
        byte[] bytes = new byte[input.readInt()];
        for (int i = 0; i < bytes.length; i++)
            bytes[i] = input.readByte();

        String name = new String(bytes);
        int scoreValue = input.readInt();

        for (int i = 0; i < Sprite.spritePool.size(); i++) {
            Sprite sprite = Sprite.spritePool.get(i);
            if (sprite != null && sprite instanceof Score && ((Score) sprite).getName().equals(name)) {
                ((Score) sprite).score = scoreValue;
                return;
            }
        }
    }
}
