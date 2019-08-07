package client;

import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import display.Screen;
import events.EndRecordingEvent;
import events.Event;
import events.EventManager;
import events.KeyPressEvent;
import events.StartRecordingEvent;
import events.StartReplayEvent;
import game_objects.GameObject;
import processing.core.PApplet;
import time.Timeline;

/**
 * ClientWorld represents the screen that the user will see and control.
 * @author jeremypark
 *
 */
public class ClientWorld extends Screen {
    // Client class to represent the client network connection
    static Client client;

    // queue of events created by user input
    private static ArrayBlockingQueue<KeyPressEvent> eventQueue = new ArrayBlockingQueue<KeyPressEvent>(1024);

    // GUID of box user controls
    private static int GUID = 0;

    // replay queue
    public static LinkedList<Event> replayQueue = new LinkedList<Event>();

    // player's frame delta
    private static final int PLAYER_FRAME_DELTA = 1;

    // start time
    private long startTime = 0;

    // end time
    private long elapsedTime = 0;


    /**
     * Draw screen
     * @param args
     */
    public static void main(String[] args) {
        PApplet.main("client.ClientWorld");

        // make a new client, give it the event queue
        client = new Client(eventQueue);
    }

    public void setup() {
        super.setup();
    }

    /**
     * Draws every box on the screen.
     *
     * Inspired by: https://www.youtube.com/watch?v=GY-c2HO2liA&t=450s
     */
    @Override
    public void draw() {
        // Calculate the time elapsed since the last game loop began.
        if (timeline != null) {
            startTime = timeline.getTime();
            elapsedTime = startTime - lastIterationTime;
            lastIterationTime = startTime;
        }

        // handle the events
        EventManager.handleEvents();

        // draw
        if (timeline != null && !timeline.isPaused()) {
            drawBackground();

            for (int i = 0; i < gameObjects.size(); i++) {
                GameObject obj = gameObjects.get( i );

                obj.draw();
            }
        }

        // Frame Rate Governing: Sleep for the remainder of the frame.
        if ( elapsedTime < PLAYER_FRAME_DELTA ) {
            try {
                TimeUnit.MILLISECONDS.sleep( (PLAYER_FRAME_DELTA - elapsedTime) );
            }
            catch ( InterruptedException e ) {
            }
        }
    }

    /**
     * Get GUID of box user controls
     * @return GUID of box user controls
     */
    public static int getGUID() {
        return GUID;
    }

    /**
     * Set GUID of box user controls
     * @param newGuid of box user controls
     */
    public static void setGUID(int newGuid) {
        GUID = newGuid;
    }

    /**
     * Set the timeline!
     * @param tl timeline
     */
    public static void setTimeline (Timeline tl) {
        // start up the client's timeline
        timeline = tl;
        timeline.start();

        // start up the event manager's timeline
        // a tic is a frame
        EventManager.eventTimeline.anchorTimeline(timeline);
        EventManager.eventTimeline.setTicSize( 1 );
        EventManager.eventTimeline.start();

    }

    /**
     * Interpret user keyboard input
     *
     * Let Processing keyPressed be signal to create an UpdateEvent
     * Put UpdateEvent on event queue
     *
     */
    public void keyPressed() {
        if (key == CODED) {
            if (keyCode == LEFT) {
                KeyPressEvent left = new KeyPressEvent("LEFT", GUID);
                try {
                    // if (!replaying)
                    if (!timeline.isPaused()) {
                        eventQueue.put(left);
                    }
                }
                catch ( Exception e ) {
                    e.printStackTrace();
                }

            } else if (keyCode == RIGHT) {
                KeyPressEvent right = new KeyPressEvent("RIGHT", GUID);

                try {
                    if (!timeline.isPaused()) {
                        eventQueue.put(right);
                    }
                }
                catch ( Exception e ) {
                    e.printStackTrace();
                }
            }

        } else if (keyCode == 32) { // if space
            KeyPressEvent up = new KeyPressEvent("JUMP", GUID);

            try {
                if (!timeline.isPaused()) {
                    eventQueue.put(up);
                }
            }
            catch ( Exception e ) {
                e.printStackTrace();
            }
        }
        else if (keyCode == 65) { // pause
            timeline.pause();
        }
        else if (keyCode == 68) { // start recording
            // raise a start recording event
            StartRecordingEvent startRecording = new StartRecordingEvent(EventManager.nextFrame(), EventManager.offset());

            // put the start on the queue!
            EventManager.addEvent( startRecording );
        }
        else if (keyCode == 70) { // end recording
            EndRecordingEvent endRecording = new EndRecordingEvent(EventManager.nextFrame(), EventManager.offset());

            EventManager.addEvent( endRecording );
        }
        else if (keyCode == 83) { // if PLAY
            timeline.play();
        }
        else if (keyCode == 49) { // 0.5x speed
            StartReplayEvent startReplay = new StartReplayEvent(EventManager.nextFrame(), 1, 66);
            EventManager.addEvent( startReplay );
        }
        else if (keyCode == 50) { // 1.0x speed
            StartReplayEvent startReplay = new StartReplayEvent(EventManager.nextFrame(), 1, 33);
            EventManager.addEvent( startReplay );
        }
        else if (keyCode == 51) { // 2.0x speed
            StartReplayEvent startReplay = new StartReplayEvent(EventManager.nextFrame(), 1, 17);
            EventManager.addEvent( startReplay );
        }
    }
}
