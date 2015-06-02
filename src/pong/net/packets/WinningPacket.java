package pong.net.packets;

import pong.Game;
import pong.net.IMessage;
import pong.sprites.Score;
import pong.sprites.Sprite;
import pong.states.menu.PlayMenuState;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by 145413 on 28/04/2015.
 *
 * Sent via the server to the client to show who has won the game
 */
public class WinningPacket implements IMessage {

    private String msg;

    public WinningPacket() {
    }

    public WinningPacket(String msg) {
        this.msg = msg;
    }

    @Override
    public void toBytes(ObjectOutputStream output) throws IOException {
        byte[] name = msg.getBytes();
        output.writeInt(name.length);
        for (byte c : name)
            output.writeByte(c);
    }

    @Override
    public void fromBytes(ObjectInputStream input) throws IOException {
        byte[] bytes = new byte[input.readInt()];
        for (int i = 0; i < bytes.length; i++)
            bytes[i] = input.readByte();
        msg = new String(bytes);
        JOptionPane.showMessageDialog(Game.instance.frame, msg);
        Game.instance.attachState(new PlayMenuState());
    }
}
