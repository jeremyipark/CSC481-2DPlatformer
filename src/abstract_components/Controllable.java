package abstract_components;

import java.io.Serializable;

/**
 * Controllable provides methods to move a character using user input.
 * @author jeremypark
 *
 * I read this Game Programming Patterns textbook chapter to help me design the abstract classes of each component:
 * http://gameprogrammingpatterns.com/component.html
 *
 * Specifically, the idea of having an abstract class is helpful to define different types of children behavior later.
 *
 *
 */
public abstract class Controllable implements  Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Move the character around
     */
    public abstract void manipulate();
}
