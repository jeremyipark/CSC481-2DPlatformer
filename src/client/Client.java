package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.PriorityQueue;
import java.util.concurrent.ArrayBlockingQueue;

import display.Screen;
import events.EndReplayEvent;
import events.Event;
import events.EventHandler;
import events.EventManager;
import events.ExitEvent;
import events.KeyPressEvent;
import events.NewPlayerEvent;
import events.PositionUpdateEvent;
import events.Replay;
import events.StartRecordingEvent;
import events.StartReplayEvent;
import game_objects.GameObject;
import game_objects.GameObjectList;
import time.LocalTimeline;
import time.Timeline;


/**
 * This class represents a client of the server.
 * Communication occurs via Sockets and a ServerSocket.
 *
 * The client code was inspired by Dr. Roberts' Client.java class.
 *
 * @author jeremypark
 *
 */
public class Client implements Runnable, EventHandler {
    // localhost
    private static final String ipAddress = "127.0.0.1";

    // port number to connect to
    private static final int portNumber = 9001;

    // list of all the game objects in the system.
    private GameObjectList gameObjects;

    // object i/o streams
    private static ObjectInputStream input = null;
    private static ObjectOutputStream output = null;

    // socket connection
    private static Socket s = null;

    // queue of events raised by client
    private static ArrayBlockingQueue<KeyPressEvent> eventQueue;

    // local variable to hold the replay
    private volatile Replay replay = null;

    // flag for recording
    private volatile boolean recording = false;

    // flag for replaying
    private volatile boolean replaying = false;

    /**
     * Client class to handle client information
     * @param events queue
     */
    public Client (ArrayBlockingQueue<KeyPressEvent> events) {
        eventQueue = events;

        try {
            // new socket connection
            s = new Socket(ipAddress, portNumber);

            // get i/o streams
            output = new ObjectOutputStream(s.getOutputStream());  // create output stream
            input = new ObjectInputStream(s.getInputStream()); // create input stream

            // I'm gonna try to get stuff from the server
            gameObjects = (GameObjectList) input.readObject();
            Screen.newGameWorld( gameObjects );

            // get the GUID from the server
            int guid = (int) input.readObject();
            ClientWorld.setGUID( guid );

            // get the local timeline
            Timeline localTimeline = (LocalTimeline) input.readObject();
            ClientWorld.setTimeline(localTimeline);

            // register client with these events!
            EventManager.register( this, "POSITION" );
            EventManager.register( this, "START_RECORDING" );
            EventManager.register( this, "END_RECORDING" );
            EventManager.register( this, "START_REPLAY" );
            EventManager.register( this, "END_REPLAY" );
            EventManager.register( this, "NEW_PLAYER" );
            EventManager.register( this, "EXIT" );

            // start the client thread to accept user input.
            (new Thread(this)).start();

            // continuously send user input to the server
            while(true)
            {
                // get an event from local event queue
                KeyPressEvent update = eventQueue.take();

                // send it out to the server
                output.writeObject( update );
            }
        } catch (Exception e) {
            System.out.println(e.toString());

        } finally {
            // upon exit
            try {
                KeyPressEvent update = new KeyPressEvent("QUIT", ClientWorld.getGUID());
                output.writeObject( update );
                input.close();
                output.close();
                s.close();
            }
            catch (Exception e) {
            }
        }
    }

    /**
     * Thread to accept position updates from the server
     */
    @Override
    public void run () {
        while ( true ) {
            try {
                // get an event from the server
                Event event = (Event) input.readObject();

                if (event.type.equals( "POSITION" )) {
                    // get the position update
                    PositionUpdateEvent positionUpdate = (PositionUpdateEvent) event;

                    // add the update to the queue
                    addToQueue(positionUpdate);
                } else if (event.type.equals( "NEW_PLAYER" )) {
                    // if there is a new player
                    NewPlayerEvent newPlayer = (NewPlayerEvent) event;

                    // add the new player
                    addNewPlayer(newPlayer);
                }
                else if (event.type.equals( "EXIT" )) {
                    // if a player quits
                    ExitEvent exit = (ExitEvent) event;

                    // remove the player
                    removePlayer(exit);
                }
            }
            catch ( Exception e ) {
                System.out.println( "I'M BREAKING" );
            }
        }
    }

    /**
     * Private helper method to properly add position update to
     * @param positionUpdate
     */
    private void addToQueue (PositionUpdateEvent positionUpdate) {
        // time stamp it with the local time
        positionUpdate.setTimeStamp( EventManager.nextFrame());

        // give it the time stamp of the next frame
        positionUpdate.setTimeToHandle( EventManager.offset());

        // put it on the event queue if you're not replaying
        if (!replaying) {
            EventManager.addEvent( positionUpdate );
        }

        // if you are recording
        if (recording) {
            // stamp it with the replay timeline's time
            PositionUpdateEvent newPositionUpdate = new PositionUpdateEvent(replay.getTimeline().getTime(), replay.getTimeline().getTime() + 1, positionUpdate.getX(), positionUpdate.getY(), positionUpdate.getGUID());

            // add it to the replay queue
            replay.getReplayQueue().add( newPositionUpdate );
        }
    }

    /**
     * Private helper method to add new players to the game
     * @param newPlayerEvent
     */
    private void addNewPlayer(NewPlayerEvent newPlayerEvent) {
        System.out.println( "Player " + (newPlayerEvent.getGUID() - 11) + " joins the game." );

        // if you're not getting your own log in
        if (newPlayerEvent.getGUID() != ClientWorld.getGUID()) {
            // get the box
            GameObject newPlayer = newPlayerEvent.getBox();

            // add it to your local list
            gameObjects.add( newPlayer );

            // update your local list
            Screen.newGameWorld( gameObjects );
        }
    }

    /**
     * Private helper method to remove players
     * @param exitEvent event with player's GUID to remove
     */
    private void removePlayer(ExitEvent exitEvent) {
        System.out.println( "Player " + (exitEvent.getGUID() - 11) + " left the game." );

        // if you're not getting your own log in
        if (exitEvent.getGUID() != ClientWorld.getGUID()) {

            // remove it to your local list
            gameObjects.removeByGUID( exitEvent.getGUID()  );

            // update your local list
            Screen.newGameWorld( gameObjects );
        }
    }

    /**************** EVENT HANDLING ****************/

    /**
     * General purpose helper method for all event types
     */
    public void onEvent ( Event e ) {
        switch (e.type) {
            case "POSITION":
                handlePositionUpdate(e);
                break;
            case "START_RECORDING":
                startRecording(e);
                break;
            case "END_RECORDING":
                endRecording(e);
                break;
            case "START_REPLAY":
                startReplay(e);
                break;
            case "END_REPLAY":
                endReplay(e);
                break;
            default:
                System.out.println( "Invalid event type." );
        }

    }

    /**
     * Update the position by getting the GUID of the game object and its new position
     * @param e position update event
     */
    public void handlePositionUpdate(Event e) {
        // take the collision event
        PositionUpdateEvent positionUpdate = (PositionUpdateEvent) e;

        if (positionUpdate != null) {
            GameObject obj = gameObjects.getByGUID( positionUpdate.getGUID() );

            // update position
            if (obj != null) {
                obj.x = positionUpdate.getX();
                obj.y = positionUpdate.getY();
            }
        }
    }

    /**
     * Start the recording!
     * @param e position update event
     */
    public void startRecording(Event e) {
        // set recording = true
        if (!recording) {
            recording = true;
        }

        // take the collision event
        StartRecordingEvent startRecording = (StartRecordingEvent) e;

        // create a new timeline
        LocalTimeline replayTimeline = new LocalTimeline();
        replayTimeline.anchorTimeline(Screen.timeline);
        replayTimeline.setTicSize( 1 );

        // clear the event queue
        PriorityQueue<Event> replayQueue = new PriorityQueue<Event>(1024);

        replay = new Replay(replayTimeline, replayQueue);
    }

    /**
     * End the recording
     * @param e position update event
     */
    public void endRecording(Event e) {
        if (recording) {
            recording = false;
        }
    }

    /**
     * Start the replay
     * @param e position update event
     */
    public void startReplay(Event e) {
        // take the start replay event
        StartReplayEvent startReplay = (StartReplayEvent) e;

        // add the replayed events to the queue
        if (!replaying && replay != null) {
            replaying = true;

            long eventTime = EventManager.eventTimeline.getTime();
            Event replayedEvent = null;

            while (!replay.getReplayQueue().isEmpty()) {
                // take an event off the replay queue
                replayedEvent = replay.getReplayQueue().remove();

                // remake the timestamp
                replayedEvent.setTimeToHandle( replayedEvent.getTimeStamp() + eventTime);

                // raise the event again!
                EventManager.addEvent( replayedEvent );
            }

            // event to designate the restoration of the original tic size
            EndReplayEvent endReplay = new EndReplayEvent(replayedEvent.getTimeStamp(), replayedEvent.getTimeToHandle());
            EventManager.addEvent( endReplay );
        }

        // set the tic size
        Screen.timeline.setTicSize( startReplay.getTicSize() );

        // make the timeline standard
        Screen.timeline.normalSpeed();

        // change timeline based on tic size
        if (startReplay.getTicSize() == 17) {
            Screen.timeline.doubleSpeed();
        } else if (startReplay.getTicSize() == 66) {
            Screen.timeline.halfSpeed();
        }
    }

    /**
     * End the replay
     * @param e position update event
     */
    public void endReplay(Event e) {
        if (replaying) {
            EndReplayEvent endReplay = (EndReplayEvent) e;

            // change the tic size back to original version
            Screen.timeline.setTicSize( 33 );
            Screen.timeline.normalSpeed();

            replaying = false;
            replay = null;
        }
    }
}
