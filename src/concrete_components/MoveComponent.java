package concrete_components;

import java.io.Serializable;

import abstract_components.Movable;
import display.Screen;
import game_objects.GameObject;
import scripting.ScriptManager;

/**
 * MoveComponent allows the GameObject to move.
 *
 * I read this Game Programming Patterns textbook chapter to help me design each concrete component:
 * http://gameprogrammingpatterns.com/component.html
 *
 * Specifically, the idea of having a concrete component with methods to change game object data is very helpful.
 *
 * @author jeremypark
 *
 */
public class MoveComponent extends Movable implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // Screen to move on
    private transient Screen screen = null;

    // default box speed
    private static final int DEFAULT_BOX_SPEED = 7;

    // upper and lower bounds for the moving platforms
    private int upper_bound = 150;
    private int lower_bound = 450;

    /**
     * Set X speed
     * @param xSpeed in x direction
     * @param ySpeed in y direction
     */
    public MoveComponent(int xSpeed, int ySpeed) {
        setXSpeed(xSpeed);
        setYSpeed(ySpeed);
    }

    /**
     * Move behavior for box
     * @param box to move
     */
    public void move (GameObject obj) {
        if (screen == null) {
            screen = Screen.getScreen();
        }

        if (obj.x <= 0) {
            obj.x = 0;
        } else if (obj.x + obj.width>= screen.width) {
            obj.x = screen.width - obj.width;
        }

        obj.x += xSpeed;
        obj.y += ySpeed;
    }

    /**
     * Movement behavior for moving platforms.
     *
     * Scripted movements to change the bounds of the moving platforms.
     *
     * @param obj platform to move
     */
    public void platformMove (GameObject obj) {

        /**
         * Algorithm for loading, binding and executing taken from Dr. Roberts' ScriptIntegrationExample.java class.
         */

        // load the script for moving platforms
        ScriptManager.loadScript("src/scripting/move_platform.js");

        // bind arguments: platform, moveComponent, and the bounds
        ScriptManager.bindArgument("obj", obj);
        ScriptManager.bindArgument("moveComponent", this);
        ScriptManager.bindArgument("upper_bound", upper_bound);
        ScriptManager.bindArgument("lower_bound", lower_bound);

        // execute platform movement script
        ScriptManager.executeScript();
    }

    /**
     * Keyboard input to go left
     */
    public void goLeft() {
        xSpeed = Math.abs( DEFAULT_BOX_SPEED ) * (-1);  //go left
    }

    /**
     * Keyboard input to go right
     */
    public void goRight() {
        xSpeed = Math.abs( DEFAULT_BOX_SPEED );  //go right
    }
}
