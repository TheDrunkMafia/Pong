package pong.states.game;

import pong.Game;
import pong.sprites.network.NetworkBall;
import pong.sprites.network.NetworkScore;
import pong.sprites.Score;
import pong.sprites.players.Human;
import pong.sprites.network.NetworkPlayer;

import java.awt.event.KeyEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by 145413 on 28/04/2015.
 *
 * The client version of the game state, instantiates packet dependent sprites for the server to control
 */
public class ClientGameState extends NetworkState {

    @Override
    public void start() {
        super.start();
        try {
            connection = new Socket(InetAddress.getByName(ip), Integer.valueOf(port));
            output = new ObjectOutputStream(connection.getOutputStream());
            input = new ObjectInputStream(connection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(ABORT);
        }

        setFocusable(true);
        setVisible(true);

        ball = new NetworkBall();
        players.add(new Human("Player 2", new Score("Player 2", getWidth() - 30, getHeight() / 2 - 20), 30, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT));
        players.add(new NetworkPlayer("Player 1", new NetworkScore("Player 1", 0, getHeight() / 2 - 20), 330));

        Game.instance.frame.setName("Client");
        Game.instance.frame.add(this);
    }

    @Override
    public void checkScore(){
    }
}
