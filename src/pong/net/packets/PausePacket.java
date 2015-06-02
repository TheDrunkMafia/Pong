package pong.net.packets;

import pong.Game;
import pong.net.IMessage;
import pong.sprites.Score;
import pong.sprites.Sprite;
import pong.states.game.GameState;
import pong.states.game.NetworkState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by 145413 on 28/04/2015.
 *
 * Sends the packet to the other client, to either pause or unpause their game
 */
public class PausePacket implements IMessage {
    private byte pause;

    public PausePacket() {
    }

    public PausePacket(boolean pause) {
        this.pause = (byte) (pause ? 1 : 0);
    }

    @Override
    public void toBytes(ObjectOutputStream output) throws IOException {
        output.writeByte(pause);
    }

    @Override
    public void fromBytes(ObjectInputStream input) throws IOException {
        boolean isPaused = input.readByte() == 1;
        NetworkState state = Game.instance.getCurrentState(NetworkState.class);
        if(state != null) state.paused = isPaused;
    }
}
