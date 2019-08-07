package game_objects;

import java.io.Serializable;

import abstract_components.Placeable;
import concrete_components.PlaceComponent;

/**
 * GameObject represents an object represented in the game.
 * Every GameObject has an x and y position, a length and height, and a globally unique identifier.
 *
 * I read this Game Programming Patterns chapter to help me make this class:
 * http://gameprogrammingpatterns.com/update-method.html
 *
 * Specifically, the update pattern is very helpful.
 *
 * @author jeremypark
 *
 */
public abstract class GameObject implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public int x;
    public int y;
    public int width;
    public int height;
    private int GUID;
    private Placeable placeComponent = new PlaceComponent();

    /**
     * GameObject constructor
     * Set the GameObject's GUID
     */
    public GameObject(int GUID) {
        this.GUID = GUID;
    }

    /**
     * Describe your own update method.
     *
     * This idea was inspired by the textbook chapter given in the header.
     */
    public abstract void update();

    // A game object has the potential to draw itself.
    public abstract void draw();

    /**
     * @return the GUID
     */
    public int getGUID () {
        return GUID;
    }

    /**
     * Set location of Game Object
     * @param x new x
     * @param y new y
     */
    public void setLocation(int x, int y) {
        placeComponent.setLocation( this, x, y );
    }
}
