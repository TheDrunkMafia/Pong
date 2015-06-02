package pong.sprites;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 145413 on 28/04/2015.
 *
 * Contains all the base code of a single sprite and allows things to run more dynamically where sprites are concerned
 */
public class Sprite {

    public static final Random rand = new Random();
    public static List<Sprite> spritePool = new ArrayList<Sprite>();
    public int speed = 1;
    public int xPosition = 0, yPosition = 0, lastXPosition, lastYPosition;
    public boolean velocityHasChanged;
    public float lastXVelocity = 0, lastYVelocity = 0, xVelocity = 0, yVelocity = 0;

    public Sprite() {
    }

    public Sprite(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;

        spritePool.add(this);
    }

    /**
     * Updates while sprite is in the pool
     */
    public void update() {
        if (lastXVelocity != xVelocity || lastYVelocity != yVelocity) {
            velocityHasChanged = true;
            velocityHasChanged();
        }

        lastXVelocity = xVelocity;
        lastYVelocity = yVelocity;

        lastXPosition = xPosition;
        lastYPosition = yPosition;
        move();
    }

    /**
     * Called if the motion variables change
     */
    public void velocityHasChanged() {
    }

    /**
     * Called after motion has been calculated
     * Used to calculate next position based on motion
     */
    protected void move() {
    }

    /**
     * Called via the JFrame to paint objects on screen
     */
    public void paint(Graphics2D g) {
    }

    /**
     * @return Rectangle used to calulcate collisions
     */
    public Rectangle getBounds() {
        return new Rectangle();
    }

    /**
     * @param sprite to check if colliding
     * @return true if this sprite and the other have intersecting bounds
     */
    public boolean collision(Sprite sprite) {
        return sprite.getBounds().intersects(getBounds());
    }

    /**
     * Removes the sprite from the pool and frame
     */
    public void destroy() {
        xPosition = -100;
        yPosition = -100;
        xVelocity = 0;
        yVelocity = 0;

        spritePool.remove(this);
    }
}
