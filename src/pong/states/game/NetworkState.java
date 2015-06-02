package pong.states.game;

import pong.Game;
import pong.net.ChannelHandler;
import pong.net.packets.ClosePacket;
import pong.net.packets.PausePacket;
import pong.states.menu.MultiplayerMenuState;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 145413 on 28/04/2015.
 *
 * Base network state, contains code for seting the ip
 */
public class NetworkState extends GameState {
    public ServerSocket server;
    public Socket connection;
    public ObjectOutputStream output;
    public ObjectInputStream input;

    protected String ip = "localhost", port = "9999";

    public NetworkState() {
        super();
        ChannelHandler handler = new ChannelHandler(this);
        handler.update();
    }

    @Override
    public void start() {
        boolean details = true;
        while(details){
            ip = JOptionPane.showInputDialog(Game.getInstance().frame, "Please enter the Server IP", ip);
            port = JOptionPane.showInputDialog(Game.getInstance().frame, "Please enter the Port Number", port);
            details = JOptionPane.showConfirmDialog(this, "IP: " + ip + " PORT: " + port, "Details check",  JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.YES_OPTION;
        }

        if(ip == null || ip.isEmpty() || port == null || port.isEmpty()) {
            JOptionPane.showInternalMessageDialog(this, "Please enter some values");

            Game.getInstance().attachState(new MultiplayerMenuState());
            return;
        }

        countdown = false;
    }

    @Override
    public void update() {
        super.update();

        if (ChannelHandler.handler != null)
            ChannelHandler.handler.update();
    }

    @Override
    public void onPause() {
        super.onPause();
        ChannelHandler.handler.sendPacket(new PausePacket(true));
    }

    @Override
    public void onUnPause() {
        super.onUnPause();
        ChannelHandler.handler.sendPacket(new PausePacket(false));
    }

    @Override
    public void quit() {
        super.quit();
        ChannelHandler.handler.sendPacket(new ClosePacket());
    }

    @Override
    public void close() {
        ChannelHandler.handler.sendPacket(new ClosePacket());

        try {
            if(server != null) server.close();
            connection.close();
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
