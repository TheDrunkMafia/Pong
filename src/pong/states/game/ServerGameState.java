package pong.states.game;

import pong.Game;
import pong.net.ChannelHandler;
import pong.net.packets.WinningPacket;
import pong.sprites.Ball;
import pong.sprites.network.NetworkScore;
import pong.sprites.Score;
import pong.sprites.players.Human;
import pong.sprites.network.NetworkPlayer;
import pong.sprites.players.Racquet;
import pong.states.menu.PlayMenuState;
import pong.states.menu.SettingState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * Created by 145413 on 28/04/2015.
 *
 * The server version of the game state, setups the game to send packets to the client and keep it synced
 */
public class ServerGameState extends NetworkState {

    @Override
    public void start() {
        super.start();
        try {
            server = new ServerSocket(Integer.valueOf(port), 200, InetAddress.getByName(ip));
            JOptionPane.showMessageDialog(Game.instance.frame, "Server has been started on: " + ip + ":" + port + " waiting for a client...", "Server Message", 0);

            connection = server.accept();

            output = new ObjectOutputStream(connection.getOutputStream());
            input = new ObjectInputStream(connection.getInputStream());
        } catch (IOException e) {
            if(e instanceof BindException)
                JOptionPane.showMessageDialog(Game.instance.frame, "The port: " + port + " has already been bind to another application, please use another free port");

            e.printStackTrace();
            Game.instance.attachState(new PlayMenuState());
        }

        countdown = false;

        setFocusable(true);
        setVisible(true);

        ball = new Ball();
        players.add(new Human("Player 1", new Score("Player 1", 0, getHeight() / 2 - 20), 330, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT));
        players.add(new NetworkPlayer("Player 2", new Score("Player 2", getWidth() - 30, getHeight() / 2 - 20), 30));

        Game.instance.frame.setName("Server");
        Game.instance.frame.add(this);
    }

    @Override
    public void checkScore(){
        for(int i = 0; i < players.size(); i++){
            Score score = players.get(i).getScore();
            if(score.score >= SettingState.scoreLimit){
                String msg = score.getName() + " has won by reaching: " + SettingState.scoreLimit;
                System.out.println(msg);
                ChannelHandler.handler.sendPacket(new WinningPacket(msg));
                JOptionPane.showMessageDialog(Game.instance.frame, msg);
                Game.instance.attachState(new PlayMenuState());
                return;
            }
        }
    }
}
