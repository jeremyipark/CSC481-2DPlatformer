package game_objects;

import java.io.Serializable;

import abstract_components.Collidable;
import abstract_components.Controllable;
import abstract_components.Movable;
import abstract_components.Renderable;
import abstract_components.Sizeable;
import concrete_components.CollisionComponent;
import concrete_components.ControlComponent;
import events.CollisionEvent;
import events.DeathEvent;
import events.Event;
import events.EventHandler;
import events.EventManager;
import events.SpawnEvent;
import scripting.ScriptManager;
import server.GameServer;

/**
 * Character represents a square on the screen that the user can manipulate.
 * @author jeremypark
 *
 */
public class Box extends GameObject implements Serializable, EventHandler {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // list of game objects, so that they can be checked for collisions
    private GameObjectList gameObjects;

    /**
     * Components
     */
    Sizeable sizeComponent;
    Renderable renderComponent;
    Movable moveComponent;
    CollisionComponent collisionComponent;
    ControlComponent controlComponent;

    // if the box is falling
    boolean falling;

    // how many times the box has died.
    private int deathCount = 0;

    private int spawn_x = 0;
    private int spawn_y = 0;

    /**
     * Make a box character
     * @param width
     * @param height
     */
    public Box (int GUID, Sizeable sizeComponent, Renderable renderComponent, Movable moveComponent, Collidable collisionComponent, Controllable controlComponent) {
        super(GUID);
        this.renderComponent = renderComponent;
        this.sizeComponent = sizeComponent;
        this.moveComponent = moveComponent;
        this.controlComponent = (ControlComponent) controlComponent;
        this.collisionComponent = (CollisionComponent) collisionComponent;
        sizeComponent.setDimensions( this );
    }

    /**
     * How the BoxCharacter updates
     */
    public void update () {
        // Get a record of all of the game objects
        gameObjects = GameServer.getGameObjects();

        //move
        moveComponent.move(this);

        // flag to tell us if the box is falling
        falling = true;

        // Check to see if you collide with any objects
        for (int i = 0; i < gameObjects.size(); i++) {
            // don't check against yourself
            if (i + 1 != this.getGUID()) {
                GameObject potentialObstacle = gameObjects.get( i );

                // If you just collided with a death zone, then respawn
                if (potentialObstacle instanceof SpawnPoint) {
                    break;
                }

                // create a collision event (it can be null though).
                Event collision = collisionComponent.checkCollision( this, potentialObstacle );

                // if you are colliding with something
                if (this != potentialObstacle && collision != null) {
                    // if it is colliding, add the collision event to the queue
                    EventManager.addEvent( collision );
                    falling = false;

                    // If you just collided with a death zone, then respawn
                    if (potentialObstacle instanceof DeathZone) {
                        // get random spawn point
                        SpawnPoint spawn = GameServer.getRandomSpawnPoint();

                        // raise death event
                        Event death = new DeathEvent(EventManager.nextFrame(), EventManager.offset(), this.getGUID());

                        // raise spawn event
                        Event spawnEvent = new SpawnEvent(EventManager.nextFrame(), EventManager.offset(), this.getGUID(), spawn);

                        // add spawn and death!
                        EventManager.addEvent( death );
                        EventManager.addEvent( spawnEvent );
                    }

                    break;
                }
            }
        }

        // if you are still falling
        if (falling) {
            moveComponent.increaseYSpeed();
        }

        //render
        renderComponent.display( this );
    }

    /**
     * Draw
     */
    @Override
    public void draw () {
        renderComponent.display( this );
    }

    /**
     * Keyboard input to go left
     */
    public void goLeft() {
        moveComponent.goLeft();
    }

    /**
     * Keyboard input to go right
     */
    public void goRight() {
        moveComponent.goRight();
    }

    /**
     * set x speed
     * @param xSpeed new x speed
     */
    public void setXSpeed(int xSpeed) {
        moveComponent.setXSpeed( xSpeed );
    }

    /**
     * set y speed
     * @param ySpeed new y speed
     */
    public void setYSpeed(int ySpeed) {
        moveComponent.setYSpeed( ySpeed );
    }

    /**
     * Get x speed
     * @return x speed
     */
    public int getXSpeed() {
        return moveComponent.xSpeed;
    }

    /**
     * Get y speed
     * @return y speed
     */
    public int getYSpeed() {
        return moveComponent.ySpeed;
    }

    /**
     * Jump method
     * Taken from https://happycoding.io/tutorials/processing/collision-detection#snapping-to-an-edge
     */
    public void jump() {
        if ( !falling ) {
            moveComponent.setYSpeed( ControlComponent.JUMP_VALUE );
            falling = true;
        }
    }

    /**
     * Keep a death count of the box
     */
    public void die() {
        ++deathCount;
    }

    /**
     * Indicate whether the box is falling
     * @return
     */
    public boolean isFalling() {
        return falling;
    }

    /**************** EVENT HANDLING ****************/

    /**
     * General purpose helper method for all event types
     */
    public void onEvent ( Event e ) {
        switch (e.type) {
            case "COLLISION":
                handleCollision(e);
                break;
            case "DEATH":
                handleDeath(e);
                break;
            case "SPAWN":
                handleSpawn(e);
                break;
            default:
                System.out.println( "Invalid event type." );
        }

    }

    /**
     * Upon collision
     * @param e event
     */
    public void handleCollision(Event e) {
        // take the collision event
        CollisionEvent collision = (CollisionEvent) e;

        // check if you are interested
        if (collision.getGUID() == this.getGUID()) {
            // handle vertical collision
            if (collision.getDirection().equals( "VERTICAL" )) {
                if (!falling) {
                    collisionComponent.collideVertically(this);
                }
            }
            // handle horizontal collision
            else if (collision.getDirection().equals( "HORIZONTAL" )) {
                collisionComponent.collideHorizontally(this);
            }
        }
    }

    /**
     * Spawn the character in a new place
     * @param e event
     */
    public void handleSpawn (Event e) {
        SpawnEvent spawn = (SpawnEvent) e;

        /**
         * Algorithm for loading, binding and executing taken from Dr. Roberts' ScriptIntegrationExample.java class.
         */

        // load the script
        ScriptManager.loadScript("src/scripting/spawn_handler.js");

        // give a Java object a Javascript name. Just renaming
        ScriptManager.bindArgument("spawn", spawn);
        ScriptManager.bindArgument("box", this);

        // you can send in parameters for your script
        ScriptManager.executeScript();
    }

    /**
     * The character dies :(
     * @param e event
     */
    public void handleDeath (Event e) {
        DeathEvent death = (DeathEvent) e;
        // death functionality doesn't really do anything
        if (death.getGUID() == this.getGUID()) {
            die();
        }
    }
}
