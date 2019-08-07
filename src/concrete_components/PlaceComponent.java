package concrete_components;

import java.io.Serializable;

import abstract_components.Placeable;
import game_objects.GameObject;

/**
 * PlaceComponent allows game objects to be placed at a certain coordinate.
 *
 * I read this Game Programming Patterns textbook chapter to help me design each concrete component:
 * http://gameprogrammingpatterns.com/component.html
 *
 * Specifically, the idea of having a concrete component with methods to change game object data is very helpful.
 *
 * @author jeremypark
 *
 */
public class PlaceComponent extends Placeable implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Set location of game object
     * @param obj game object
     * @param x coordinate
     * @param y coordinate
     */
    public void setLocation ( GameObject obj, int x, int y ) {
        obj.x = x;
        obj.y = y;
    }
}
