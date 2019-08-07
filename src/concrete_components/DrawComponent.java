package concrete_components;

import java.io.Serializable;

import abstract_components.Renderable;
import display.Screen;
import game_objects.GameObject;

/**
 * DrawComponent allows the GameObject to be rendered onto the screen.
 *
 * I read this Game Programming Patterns textbook chapter to help me design each concrete component:
 * http://gameprogrammingpatterns.com/component.html
 *
 * Specifically, the idea of having a concrete component with methods to change game object data is very helpful.
 *
 * @author jeremypark
 *
 */
public class DrawComponent extends Renderable implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // color of game object
    protected int color;

    // Screen to draw onto
    private transient Screen screen;

    /**
     * Component to allow game object drawing on screen.
     */
    public DrawComponent() {
        if (screen == null) {
            screen = Screen.getScreen();
        }

        // generate a random color
        color = screen.color(screen.random(255), screen.random(255), screen.random(255));   //random color
    }

    /**
     * Define your own color
     * @param color of box
     */
    public DrawComponent(int color) {
        this.color = color;
    }

    /**
     * Display the game object!
     * Get the screen to draw onto and then draw it.
     */
    public void display ( GameObject obj ) {
        Screen tempScreen = Screen.getScreen();
        tempScreen.fill(color); //set color
        tempScreen.noStroke();  //no stroke
        tempScreen.rect(obj.x, obj.y, obj.width, obj.height);  //make a rectangle
    }
}
