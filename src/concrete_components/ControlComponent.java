package concrete_components;

import java.io.Serializable;

import abstract_components.Controllable;


/**
 * ControlComponent has defined behavior for controlling the box.
 *
 * This class helps define the behavior for jumps too.
 *
 * I read this Game Programming Patterns textbook chapter to help me design each concrete component:
 * http://gameprogrammingpatterns.com/component.html
 *
 * Specifically, the idea of having a concrete component with methods to change game object data is very helpful.
 *
 * @author jeremypark
 *
 */
public class ControlComponent extends Controllable implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static final int JUMP_VALUE = -18;    //jump value
    boolean jumping = false;    //if user box is jumping or not

    /**
     * Generate move character around
     */
    public void manipulate () {
        // chose not to implement because the screen handles all of this.
    }
}
