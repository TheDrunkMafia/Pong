package pong.sprites;

import pong.Game;
import pong.states.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Method;

/**
 * Created by 145413 on 28/04/2015.
 *
 * A button that uses Lambda/Runnable to run code dynamically
 */
public class Button extends Sprite implements MouseListener {

    private int xSize, ySize;

    public String text = "";
    public Runnable method;
    private Color base, hover;

    public Button(String text, Runnable method, Color base, Color hover, int xPosition, int yPosition, int xSize, int ySize) {
        super(xPosition, yPosition);
        this.text = text;
        this.method = method;
        this.base = base;
        this.hover = hover;
        this.xSize = xSize;
        this.ySize = ySize;

        Game.instance.frame.addMouseListener(this);
    }

    public Button(String text, Runnable method){
        this(text, method, Color.BLACK, Color.GRAY, 0, 0, 280, 60);
    }

    @Override
    public void paint(Graphics2D g) {
        JPanel pannel = Game.instance.getCurrentState(JPanel.class);

        Point point = new Point(MouseInfo.getPointerInfo().getLocation());
        SwingUtilities.convertPointFromScreen(point, pannel != null ? pannel : Game.instance.frame);

        Rectangle bounds = getBounds();
        g.setColor(bounds.contains(point) ? hover : base);
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", Font.BOLD, 30));
        g.drawString(String.valueOf(text), bounds.x + (bounds.width / 2 - text.length() * 8), bounds.y + (bounds.height / 2 + 10));
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(xPosition, yPosition, xSize, ySize);
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        Point point = new Point(MouseInfo.getPointerInfo().getLocation());
        SwingUtilities.convertPointFromScreen(point, Game.instance.frame);
        if (getBounds().contains(point))
            method.run();
    }

    public void mouseReleased(MouseEvent e) {
    }

    @Override
    protected void move() {
    }
}
