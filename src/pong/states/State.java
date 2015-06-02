package pong.states;

public interface State {
    /**
     * Called when the state is first attached
     */
    void start();
    /**
     * Called on each iteration of the game loop
     */
    void update();
    /**
     * Called when the state is detached
     */
    void end();
    /**
     * Called when the network connections stream is closed
     */
    void close();
}
