package pong.net.packets;

import pong.Game;
import pong.net.IMessage;
import pong.states.menu.MenuState;
import pong.states.game.NetworkState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by 145413 on 31/03/2015.
 *
 * Attempts to send a close packet to stop an IOException when one of the clients closes the connection
 */
public class ClosePacket implements IMessage {

    public ClosePacket() {
    }

    @Override
    public void toBytes(ObjectOutputStream output) throws IOException {
    }

    @Override
    public void fromBytes(ObjectInputStream input) throws IOException {
        NetworkState state = Game.instance.getCurrentState(NetworkState.class);
        if(state != null){
            state.connection.close();
            Game.instance.attachState(new MenuState());
        }
    }
}
