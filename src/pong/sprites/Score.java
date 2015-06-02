package pong.sprites;

import pong.net.ChannelHandler;
import pong.net.packets.ScorePacket;

import java.awt.*;

/**
 * Created by 145413 on 28/04/2015.
 *
 * A score sprite, shows the score for single player and the server
 */
public class Score extends Sprite {

    public int score;
    private String name;

    public Score(String name, int xPos, int yPos) {
        super(xPos, yPos);
        this.name = name;
    }

    @Override
    protected void move() {
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.GRAY);
        g.setFont(new Font("Verdana", Font.BOLD, 30));
        g.drawString(String.valueOf(score), xPosition, yPosition);
    }

    public void increment() {
        if (ChannelHandler.handler != null) {
            score++;
            if (ChannelHandler.handler.state.server != null)
                ChannelHandler.handler.sendPacket(new ScorePacket(this));
        } else
            score++;
    }

    public String getName() {
        return name;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle();
    }
}
