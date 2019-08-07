package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import display.Screen;
import events.Event;
import events.EventManager;
import events.ExitEvent;
import events.KeyPressEvent;
import events.NewPlayerEvent;
import events.PositionUpdateEvent;
import game_objects.Box;
import game_objects.GameObject;
import game_objects.GameObjectList;
import time.LocalTimeline;

/**
 * This class represents a server.
 * Communication occurs via Sockets and a ServerSocket.
 *
 * This code was inspired by Dr. Roberts' SampleServer.java class.
 * Specifically, the idea of accepting clients asynchronously in its own thread.
 *
 * This code was also inspired by https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-one-server/19432300#19432300
 * Specifically, the code to start up new threads for different connections.
 *
 * @author jeremypark
 *
 */
public class Server implements Runnable {

    private volatile static CopyOnWriteArrayList<ObjectInputStream> input_streams; // all of the clients
    private volatile static CopyOnWriteArrayList<ObjectOutputStream> output_streams; // all of the client
    private static CopyOnWriteArrayList<Socket> sockets = new CopyOnWriteArrayList<Socket>(); // all of the client
    private static ArrayBlockingQueue<Event> eventQueue = new ArrayBlockingQueue<Event>(1024);
    private static ServerSocket serverSocket; //server socket accepting connections
    private static final int portNumber = 9001;
    protected static GameObjectList gameObjects = GameServer.getGameObjects();
    private ArrayList<GameObject> movers;
    private boolean connectionEstablished = false;

    private final static int CLIENT_TIC_SIZE = 33;

    private Object mutex = new Object();

    /**
     * Start up the server: start a thread to handle client connections, continuously get events from the client, and send them back.
     */
    public Server() {
        System.out.println("Welcome to Boxario! Please create a character.");

        // Create the server socket
        try {
            serverSocket = new ServerSocket(portNumber);
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }

        input_streams = new CopyOnWriteArrayList<ObjectInputStream>();    // make the data structure for all of the streams
        output_streams = new CopyOnWriteArrayList<ObjectOutputStream>();

        movers = new ArrayList<GameObject>();

        /// Add the moving platforms
        movers.add( gameObjects.getByGUID( 6 ) );
        movers.add( gameObjects.getByGUID( 7 ) );

        (new Thread(this)).start(); // start the server

        /**
         * Accept events from the client, send the event to all clients
         */
        try {
            while(true)
            {
                // Get an event on the queue
                if (eventQueue.isEmpty() && connectionEstablished) {
                    TimeUnit.MILLISECONDS.sleep(10);

                    // iterate through all of the movers
                    // send a position update of all the movers
                    synchronized ( mutex ) {

                        for ( int i = 0; i < movers.size(); i++ ) {
                            // get a moving game object
                            GameObject mover = movers.get( i );

                            // get its current position
                            PositionUpdateEvent newPosition = new PositionUpdateEvent(EventManager.nextFrame(), EventManager.offset(), mover.x, mover.y, mover.getGUID());

                            // write out event to all clients

                            for(ObjectOutputStream dout : output_streams)
                            {
                                try {
                                    dout.writeObject(newPosition);
                                } catch (SocketException se) {
                                    //in the case where it doesn't update in time!
                                }
                            }
                        }
                    }
                } else if (!eventQueue.isEmpty()){
                    // get an event
                    Event event = eventQueue.take();

                    if (event instanceof KeyPressEvent) {
                        KeyPressEvent update = (KeyPressEvent) event;
                        handleKeyPress(update);
                    } else if (event instanceof NewPlayerEvent) {
                        synchronized ( mutex ) {
                            // get its current position
                            NewPlayerEvent newPlayer = (NewPlayerEvent) event;

                            // write out event to all clients
                            for(ObjectOutputStream dout : output_streams)
                            {
                                dout.writeObject(newPlayer);
                            }
                        }
                    }
                    else if (event instanceof ExitEvent) {
                        synchronized ( mutex ) {
                            // get its current position
                            ExitEvent exit = (ExitEvent) event;

                            gameObjects.removeByGUID( exit.getGUID() );

                            Screen.newGameWorld( gameObjects );

                            // write out event to all clients
                            for(ObjectOutputStream dout : output_streams)
                            {
                                dout.writeObject(exit);
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            if (e instanceof EOFException ) {
                System.out.println("Player left the game.");
            }

            e.printStackTrace();
        }
    }

    /**
     * Private helper method to handle key presses
     * @param update
     */
    private void handleKeyPress (KeyPressEvent update) {
        // Get the box controlled by that user
        Box box = (Box) gameObjects.getByGUID( update.getGUID() );

        // move box based on user input from the client
        if (box != null && update != null) {
            if (update.getType().equals( "LEFT" )) {
                box.goLeft();
            } else if (update.getType().equals( "RIGHT" )) {
                box.goRight();
            } else if (update.getType().equals( "JUMP" )) {
                box.jump();
            }
        }
    }


    /**
     * Thread's run method, which indefinitely accepts client connections
     */
    public void run () {
        Socket clientSocket = null;
        try {
            while (true) {
                // Wait for a client connection.
                clientSocket = serverSocket.accept();

                // Add the character to the game
                Box newCharacter = GameServer.createUserBox();

                // Add the character to the list of movers.
                movers.add( newCharacter );

                // create the object i/o streams
                ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());

                // add client information
                sockets.add( clientSocket );
                output_streams.add( output );
                input_streams.add( input );

                // get list of game objects to send to client
                GameObjectList currentWorld = GameServer.getGameObjects();

                // get GUID of box the user controls
                int GUID = GameServer.getCurrentGUID();

                // create a local timeline and send it to the client
                LocalTimeline localTimeline = new LocalTimeline();
                localTimeline.anchorTimeline(Screen.timeline);
                localTimeline.setTicSize( CLIENT_TIC_SIZE );

                // I will attempt to send stuff to the client!
                output.writeObject( currentWorld );
                output.writeObject( GUID );
                output.writeObject( localTimeline );

                connectionEstablished = true;

                // start thread to handle client connection
                ServerThread serverThread = new ServerThread(clientSocket, output, input, eventQueue, GUID);
                serverThread.start();

                NewPlayerEvent newPlayerEvent = new NewPlayerEvent(EventManager.nextFrame(), EventManager.offset(), GUID, newCharacter);
                eventQueue.add( newPlayerEvent );
            }
        }
        catch (Exception e) {
            try {
                clientSocket.close();
            }
            catch ( IOException e2 ) {
                e2.printStackTrace();
            }
        }
    }

    // Remove that socket's streams
    public static void remove(Socket s) throws IOException {
        for (int i = 0; i < sockets.size(); i++) {
            if (sockets.get( i ) == s) {
                input_streams.remove( i );
                output_streams.remove( i );
                sockets.remove( i );
            }
        }
    }
}
