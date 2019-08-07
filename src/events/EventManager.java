package events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import time.LocalTimeline;

/**
 * EventManager represents the manager of all of the events.
 * @author jeremypark
 *
 */
public class EventManager {
    //private static EventManager eventManager = null;

    /**
     * Different types of event handlers
     */
    private static ArrayList<EventHandler> collisionHandlers = new ArrayList<EventHandler>();
    private static ArrayList<EventHandler> spawnHandlers = new ArrayList<EventHandler>();
    private static ArrayList<EventHandler> deathHandlers = new ArrayList<EventHandler>();
    private static ArrayList<EventHandler> positionHandlers = new ArrayList<EventHandler>();
    private static ArrayList<EventHandler> startRecordingHandlers = new ArrayList<EventHandler>();
    private static ArrayList<EventHandler> endRecordingHandlers = new ArrayList<EventHandler>();
    private static ArrayList<EventHandler> startReplayHandlers = new ArrayList<EventHandler>();
    private static ArrayList<EventHandler> endReplayHandlers = new ArrayList<EventHandler>();
    private static ArrayList<EventHandler> newPlayerHandlers = new ArrayList<EventHandler>();
    private static ArrayList<EventHandler> exitHandlers = new ArrayList<EventHandler>();

    // priority queue of events
    public static PriorityQueue<Event> eventQueue = new PriorityQueue<Event>(1024);

    // event map of event types and handlers
    private static HashMap<String, ArrayList<EventHandler>> eventMap = new HashMap<String, ArrayList<EventHandler>>();

    // timeline for the event manager
    public static LocalTimeline eventTimeline = new LocalTimeline();

    // offset for the tic
    private final static long OFFSET = 1;

    /**
     * Register a game object
     * @param eventHandler game object
     * @param type of event
     */
    public static void register ( EventHandler eventHandler, String type ) {
        // general reference to the list of handlers you want to associate with a type
        ArrayList<EventHandler> handlers = null;

        // give event handler to proper list
        switch (type) {
            case "COLLISION":
                if (!collisionHandlers.contains( eventHandler )) {
                    handlers = collisionHandlers;
                }

                break;

            case "SPAWN":
                if (!spawnHandlers.contains( eventHandler )) {
                    handlers = spawnHandlers;
                }

                break;

            case "DEATH":
                if (!deathHandlers.contains( eventHandler )) {
                    handlers = deathHandlers;
                }

                break;

            case "POSITION":
                if (!positionHandlers.contains( eventHandler )) {
                    handlers = positionHandlers;
                }

                break;

            case "START_RECORDING":
                if (!startRecordingHandlers.contains( eventHandler )) {
                    handlers = startRecordingHandlers;
                }

                break;

            case "END_RECORDING":
                if (!endRecordingHandlers.contains( eventHandler )) {
                    handlers = endRecordingHandlers;
                }

                break;

            case "START_REPLAY":
                if (!startReplayHandlers.contains( eventHandler )) {
                    handlers = startReplayHandlers;
                }

                break;

            case "END_REPLAY":
                if (!endReplayHandlers.contains( eventHandler )) {
                    handlers = endReplayHandlers;
                }

                break;

            case "NEW_PLAYER":
                if (!newPlayerHandlers.contains( eventHandler )) {
                    handlers = newPlayerHandlers;
                }

                break;

            case "EXIT":
                if (!exitHandlers.contains( eventHandler )) {
                    handlers = exitHandlers;
                }

                break;
        }

        // add handler to list
        if (handlers != null) {
            handlers.add( eventHandler );
            eventMap.put( type, handlers );
        }
    }

    /**
     * Add an event to the queue
     * @param e
     */
    public synchronized static void addEvent(Event e) {
        eventQueue.add( e );
    }

    /**
     * Add an event to the queue
     * @param e
     */
    public synchronized static void addReplay(PriorityQueue<Event> replayQueue) {
        eventQueue.addAll( replayQueue );
    }

    /**
     * Empty the event queue and send the event to it's handler
     */
    public synchronized static void handleEvents() {
        // peek
        Event event;

        // if the queue is not empty
        // and if the time to handle is appropriate!
        while ((event = eventQueue.peek()) != null && event.getTimeToHandle() <= eventTimeline.getTime()) {
            //System.out.println( "Current event time: " + event.getTimeToHandle() );

            // glitch? the event seems normal to me, but NPE is thrown?
            try {
                eventQueue.remove();
            }
            catch (NullPointerException npe) {
            }

            // get the appropriate list of handlers
            ArrayList<EventHandler> handlers = eventMap.get( event.type );

            // give the event to each respective handler
            if (handlers != null) {
                // go through all of the handlers for that type
                for (int i = 0; i < handlers.size(); i++) {
                    EventHandler handler = handlers.get( i );

                    // call the proper event handling
                    handler.onEvent( event );
                }
            }
        }
    }

    /**
     * Get the next frame time
     * @return next frame
     */
    public static long nextFrame() {
        return eventTimeline.getTime();
    }

    /**
     * Get the next frame time
     * @return next frame
     */
    public static long offset() {
        return eventTimeline.getTime() + OFFSET;
    }
}
