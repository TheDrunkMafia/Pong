package pong.sprites.network;

import pong.sprites.Score;

/**
 * Created by 145413 on 28/04/2015.
 *
 * Controlled via the server, it updates via the packets. Score is calculated server side along with collisions
 */
public class NetworkScore extends Score {
    public NetworkScore(String name, int xPos, int yPos) {
        super(name, xPos, yPos);
    }

    @Override
    public void increment() {}
}
