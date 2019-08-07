package abstract_components;

import java.io.Serializable;

import game_objects.GameObject;

/**
 * Sizeable allows children components to define their own dimension setting behavior.
 *
 * I read this Game Programming Patterns textbook chapter to help me design the abstract classes of each component:
 * http://gameprogrammingpatterns.com/component.html
 *
 * Specifically, the idea of having an abstract class is helpful to define different types of children behavior later.
 *
 * @author jeremypark
 *
 */
public abstract class Sizeable implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public abstract void setDimensions(GameObject obj);
}
