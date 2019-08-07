package display;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import events.EventManager;
import game_objects.GameObject;
import game_objects.GameObjectList;
import game_objects.SpawnPoint;
import processing.core.PApplet;
import time.Timeline;

/**
 * Screen represents the window that shows all moving boxes.
 * Screen extends PApplet and so makes use of the Processing API.
 *
 * The Processing Eclipse tutorial was referenced for this class: https://processing.org/tutorials/eclipse/
 * Specifically, the use of PApplet and setting up parameters was utilized.
 *
 * Also, the component pattern chapter of the Game Programming Patterns book was helpful.
 * Specifically, wherever game objects are made, I send in components to the constructor.
 * That came from this chapter: http://gameprogrammingpatterns.com/component.html
 *
 * @author jeremypark
 *
 */
public class Screen extends PApplet {
    // screen that is drawn onto
    protected static Screen masterScreen = null;

    // timeline
    public static Timeline timeline = null;

    // list of all the game objects in the system.
    public static GameObjectList gameObjects = new GameObjectList();

    // colors
    protected final int BACKGROUND_COLOR = color(155,196,250);

    // keep track of the game objects in the system.
    protected static int guidCount = 0;

    // list of spawn points to choose from
    public static ArrayList<SpawnPoint> spawnPoints = new ArrayList<SpawnPoint>();

    // time elapsed since the last iteration
    protected long lastIterationTime = 0;

    // for 30 FPS, the frame delta is around 33.
    public static final int FRAME_DELTA = 33;

    /**
     * Create the PApplet
     * Start up the server to accept clients
     * @param args console
     */
    public static void main(String[] args) {
        PApplet.main("display.Screen");
    }

    /**
     * Settings such as window size and setting masterScreen to this instance of Screen
     */
    public void settings(){
        size(800,800);
        smooth(10);
    }

    /**
     * Set up environment
     */
    public void setup() {
        // Set up master screen (Singleton pattern)
        masterScreen = this;
    }


    /**
     * Server's draw:
     * The difference between the client is that the client's draw only renders and doesn't update.
     *
     * Inspired by: https://www.youtube.com/watch?v=GY-c2HO2liA&t=450s
     */
    public void draw() {
        // Calculate the time elapsed since the last game loop began.
        long startTime = timeline.getTime();
        long elapsedTime = startTime - lastIterationTime;
        lastIterationTime = startTime;

        drawBackground();

        // dequeue all events
        EventManager.handleEvents();

        // draw all objects and update them!
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject obj = gameObjects.get( i );
            obj.update();
        }

        // Frame Rate Governing: Sleep for the remainder of the frame.
        if ( elapsedTime < FRAME_DELTA ) {
            try {
                TimeUnit.MILLISECONDS.sleep( (FRAME_DELTA - elapsedTime) );
            }
            catch ( InterruptedException e ) {
            }
        }
    }

    /**
     * Draw the background
     */
    public void drawBackground() {
        background(BACKGROUND_COLOR);
    }

    /**
     * Send this instance of Screen using Singleton pattern
     * @return this screen
     */
    public static Screen getScreen() {
        return masterScreen;
    }

    /**
     * Get a new set of game objects
     * @param list of new game objects
     */
    public static void newGameWorld(GameObjectList list) {
        gameObjects = list;
    }
}