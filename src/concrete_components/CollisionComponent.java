package concrete_components;

import java.io.Serializable;

import abstract_components.Collidable;
import events.CollisionEvent;
import events.EventManager;
import game_objects.Box;
import game_objects.GameObject;

/**
 * CollisionComponent has defined behavior for the box to stop moving upon collisions.
 *
 * I read this Game Programming Patterns textbook chapter to help me design each concrete component:
 * http://gameprogrammingpatterns.com/component.html
 *
 * Specifically, the idea of having a concrete component with methods to change game object data is very helpful.
 *
 * @author jeremypark
 *
 */
public class CollisionComponent extends Collidable implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    boolean collidingVertically = false;
    boolean collidingHorizontally = false;

    /**
     * Collision detection between the user box and any potential obstacle
     *
     * Inspired by: https://happycoding.io/tutorials/processing/collision-detection
     */
    public CollisionEvent checkCollision ( GameObject mvr, GameObject obstacle ) {
        Box mover = (Box) mvr;

        // boolean flag
        CollisionEvent collision = null;

        /**
         * If moving vertically will cause collision
         */
        if (mover.y + mover.height + mover.getYSpeed() >= obstacle.y && mover.y + mover.getYSpeed() < obstacle.y + obstacle.height && mover.x + mover.width > obstacle.x && mover.x < obstacle.x + obstacle.width) {
            mover.y = obstacle.y - mover.height;

            //collideVertically(mover);

            collision = new CollisionEvent(EventManager.nextFrame(), EventManager.offset(), mvr.getGUID(), "VERTICAL");
            //Event collisionEvent = new Event(Screen.timeline.getTime(), Screen.offset);
            collidingVertically = true;
        }

        /**
         * If moving horizontally will cause collision
         */
        if (mover.x + mover.width + mover.getXSpeed() > obstacle.x && mover.x + mover.getXSpeed() < obstacle.x + obstacle.width && mover.y + mover.height > obstacle.y && mover.y < obstacle.y + obstacle.height) {
            //collideHorizontally(mover);

            collision = new CollisionEvent(EventManager.nextFrame(), EventManager.offset(), mvr.getGUID(), "HORIZONTAL");
            collidingHorizontally = true;
        }

        return collision;
    }

    /**
     * If the game object collides vertically, stop the vertical speed.
     */
    public void collideVertically(GameObject mvr) {
        Box mover = (Box) mvr;
        if (collidingVertically) {
            mover.setYSpeed( 0 );
            collidingVertically = false;
        }
    }

    /**
     * If the game object collides horizontally, stop the horizontal speed.
     */
    public void collideHorizontally(GameObject mvr) {
        Box mover = (Box) mvr;

        if (collidingHorizontally) {
            mover.setXSpeed( 0 );
            collidingHorizontally = false;
        }
    }
}
