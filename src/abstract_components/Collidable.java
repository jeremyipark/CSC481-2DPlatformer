package abstract_components;

import java.io.Serializable;

import events.CollisionEvent;
import game_objects.GameObject;

/**
 * Collidable describes the ability to check if you are colliding with another game object.
 * @author jeremypark
 *
 * I read this Game Programming Patterns textbook chapter to help me design the abstract classes of each component:
 * http://gameprogrammingpatterns.com/component.html
 *
 * Specifically, the idea of having an abstract class is helpful to define different types of children behavior later.
 *
 */
public abstract class Collidable implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Checks if this game object collides with another
     * @param obj to check against
     * @return if collides
     */
    public abstract CollisionEvent checkCollision(GameObject mover, GameObject obstacle);
}
