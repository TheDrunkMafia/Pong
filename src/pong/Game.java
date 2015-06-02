package pong;

import pong.sprites.Sprite;
import pong.states.menu.MenuState;
import pong.states.State;

import javax.swing.*;

public class Game {
    public static Game instance;

    public State currentState;
    public JFrame frame;
    public boolean debug;
    private int xSize, ySize;

    public Game(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;

        frame = new JFrame("Ping Pong");
        frame.setSize(xSize, ySize);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to close this window?", "Really Closing?",  JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    close();
                    System.exit(0);
                }
            }
        });
        debug = false;
    }

    /**
     * Initial main method
     *
     * @param args Arguments passed in by the JVM
     */
    public static void main(String[] args) throws InterruptedException {
        instance = new Game(300, 400);
        instance.attachState(new MenuState());
        instance.update();

        System.out.println("END!");
    }

    /**
     * The update loop for any state that is attached
     */
    private void update() throws InterruptedException {
        while (currentState != null) {
            currentState.update();
        }
    }

    /**
     * Invoked when the close or quit button is pressed
     */
    private void close(){
        if(currentState != null)
            currentState.close();
    }

    /**
     * Attaching a state will put it into the update loop
     *
     * @param state
     */
    public void attachState(State state) {
        if (currentState == state)
            return;

        if (currentState != null)
            currentState.end();

        if (state instanceof JPanel) {
            frame.remove((JPanel) state);
        }

        System.out.println("Attaching: " + state.getClass().getName());

        Sprite[] sprites = Sprite.spritePool.toArray(new Sprite[Sprite.spritePool.size()]);
        for (int i = 0; i < sprites.length; i++)
            sprites[i].destroy();

        currentState = state;
        currentState.start();
    }

    /**
     * Returns the current state attached to the update loop
     *
     * @param type Class of which is attached
     */
    public <T> T getCurrentState(Class<T> type) {
        return type.isInstance(currentState) ? (T) currentState : null;
    }

    /**
     * @return Width of the frame
     */
    public int getWidth() {
        return xSize;
    }

    /**
     * @return Height of the frame
     */
    public int getHeight() {
        return ySize;
    }
}
