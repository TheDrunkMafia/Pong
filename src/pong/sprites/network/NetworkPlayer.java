package pong.sprites.network;

import pong.sprites.Score;
import pong.sprites.players.Racquet;

public class NetworkPlayer extends Racquet {

    public NetworkPlayer(String name, Score score, int yPos) {
        super(name, score, yPos);
    }

    public void setXPos(int x) {
        xPosition = x;
    }

    public void setYPos(int y) {
        yPosition = y;
    }
}
