package abstract_components;

import java.io.Serializable;

import game_objects.GameObject;

/**
 * Movable is an abstract class that allows children to implement moving behavior
 *
 *
 * I read this Game Programming Patterns textbook chapter to help me design the abstract classes of each component:
 * http://gameprogrammingpatterns.com/component.html
 *
 * Specifically, the idea of having an abstract class is helpful to define different types of children behavior later.
 * @author jeremypark
 *
 */
public abstract class Movable implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // x speed
    public int xSpeed;
    // y speed
    public int ySpeed;

    // move game object
    public abstract void move(GameObject obj);

    // go left
    public abstract void goLeft();

    // go right
    public abstract void goRight();

    /**
     * Set x speed
     * @param xSpeed to set to
     */
    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    /**
     * Set y speed
     * @param ySpeed to set to
     */
    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    /**
     * Speed up in x direction
     */
    public void increaseXSpeed() {
        xSpeed++;
    }

    /**
     * Slow down in x direction
     */
    public void decreaseXSpeed() {
        xSpeed--;
    }

    /**
     * Speed up in y direction
     */
    public void increaseYSpeed() {
        ySpeed++;
    }

    /**
     * Slow down in x direction
     */
    public void decreaseYSpeed() {
        ySpeed--;
    }
}
