package pong.net;

import pong.Game;
import pong.sprites.players.Racquet;
import pong.states.game.NetworkState;

import javax.swing.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 145413 on 03/03/2015.
 *
 * Handles incoming and outgoing packets
 */
public class ChannelHandler {

    private int currentFrame;

    public static ChannelHandler handler;
    public NetworkState state;

    public ChannelHandler(NetworkState state) {
        this.state = state;
        handler = this;
    }

    /**
     * Prepares a packet and then flushes it though the socket
     * @param message which will be sent to the client/server
     */
    public void sendPacket(IMessage message) {
        try {
            byte[] className = message.getClass().getName().getBytes();
            state.output.writeInt(className.length);
            for (byte ch : className)
                state.output.writeByte(ch);
            message.toBytes(state.output);
            state.output.flush();
        } catch (Exception e) {
            System.exit(ImageObserver.ABORT);
        }
    }

    List<Long> times = new ArrayList<Long>();
    private int packets;

    /**
     * Reads the bytes that have arrived via the input stream and figures out which packet class
     * they ordinated from, then puts the stream into the class
     */
    public void update() {
        try {
            if (state == null || state.connection == null || !state.connection.isConnected() || state.input == null)
                return;

            long timeTaken = System.nanoTime();

            while (state.connection.isConnected() && state.input.available() != 0) {
                byte[] bytes = new byte[state.input.readInt()];
                for (int ch = 0; ch < bytes.length; ch++)
                    bytes[ch] = state.input.readByte();

                Class<IMessage> messageClass = loadClass(new String(bytes));
                if(messageClass != null) {
                    (messageClass.newInstance()).fromBytes(state.input);
                    packets++;
                }
            }

            times.add(System.nanoTime() - timeTaken);

            int maxFrames = 1000;
            if(currentFrame == maxFrames) {
                long mediumTime = 0;
                for(long time : times)
                    mediumTime += time;
                mediumTime /= times.size();
                System.out.println("Mean Time: " + mediumTime + " Packets Received: " + packets);
                currentFrame = 0;
                packets = 0;
                times = new ArrayList<>();
            }
            currentFrame ++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a class based on its name
     * @param className to load from the class loader
     * @param <T> Class Type
     * @return a class of the passed in generic type
     */
    public <T>Class<T> loadClass(String className){
        try {
            return (Class<T>) Class.forName(className);
        }catch (Exception e) {}
        return null;
    }
}
