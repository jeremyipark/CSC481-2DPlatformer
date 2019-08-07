package concrete_components;

import java.io.Serializable;

import abstract_components.Sizeable;
import game_objects.GameObject;

/**
 * SizeComponent lets you set the dimensions of a game object.
 *
 * I read this Game Programming Patterns textbook chapter to help me design each concrete component:
 * http://gameprogrammingpatterns.com/component.html
 *
 * Specifically, the idea of having a concrete component with methods to change game object data is very helpful.
 *
 * @author jeremypark
 *
 */
public class SizeComponent extends Sizeable implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // width
    private int width;
    // height
    private int height;

    /**
     * Set size of game object
     * @param width of obj
     * @param height of obj
     */
    public SizeComponent(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Set dimensions of game object
     * @param obj game object
     */
    public void setDimensions (GameObject obj) {
        obj.width = width;
        obj.height = height;
    }
}
