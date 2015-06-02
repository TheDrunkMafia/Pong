package pong;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class InputManager implements KeyEventDispatcher {

    private static InputManager instance = new InputManager();

    private Map<Integer, Integer> buttons = new HashMap<Integer, Integer>();

    private InputManager() {
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(this);
    }

    /**
     * Singletons theory, only one instance of this class is allowed
     * @return the class of this instance
     */
    public static InputManager getInstance() {
        return instance;
    }

    /**
     * Used internally by the event dispatcher, logs key presses
     * @param event key pressed
     */
    public boolean dispatchKeyEvent(KeyEvent event) {
        buttons.put(event.getKeyCode(), event.getID());
        return false;
    }

    /**
     * Checks if the button has been pressed during this this update of the JFrame
     * @param button to check
     * @return If the key has been pressed
     */
    public boolean isKeyDown(int button) {
        if (buttons.containsKey(button))
            return buttons.get(button) == KeyEvent.KEY_PRESSED;
        return false;
    }

    /**
     * Checks if the button has been pressed during this this update of the JFrame
     * @param button to check
     * @return If the key has not been pressed
     */
    public boolean isKeyUp(int button) {
        if (buttons.containsKey(button))
            return buttons.get(button) == KeyEvent.KEY_RELEASED;
        return false;
    }
}
