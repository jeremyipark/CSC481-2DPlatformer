package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;

import events.Event;
import events.EventManager;
import events.ExitEvent;
import events.KeyPressEvent;

/**
 * ServerThread handles client reads and writes
 * Server delegates client connections to a ServerThread
 *
 * Inspired by: https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-one-server/19432300#19432300
 * Mainly, the idea of using separate threads to communicate with each client.
 *
 * @author jeremypark
 *
 */
public class ServerThread extends Thread {
    // Socket for communication with client
    private Socket socket;
    private ObjectOutputStream output = null;
    private ObjectInputStream input = null;
    private int GUID;

    // event queue
    private ArrayBlockingQueue<Event> eventQueue;

    /**
     * Server thread to handle client requests.
     * @author jeremypark
     *
     */
    public ServerThread (Socket socket, ObjectOutputStream output, ObjectInputStream input, ArrayBlockingQueue<Event> eventQueue, int GUID) {
        // server's socket used to communicate with the client
        this.socket = socket;
        this.output = output;
        this.input = input;
        this.eventQueue = eventQueue;
        this.GUID = GUID;
    }

    /**
     * Thread's run method, accept I/O from client
     */
    @Override
    public void run() {
        try {
            while (true) {
                // READ AN UPDATE FROM THE CLIENT
                try {
                    Event update = (KeyPressEvent) input.readObject();

                    // put that event on the event queue
                    eventQueue.put(update);
                }
                catch (SocketException e) {
                    // sometimes it messes up?
                }
            }

        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            try {
                System.out.println("Player " + (GUID - 11) + " left the game.");
                ExitEvent exitEvent = new ExitEvent(EventManager.nextFrame(), EventManager.offset(), GUID);
                eventQueue.add( exitEvent );

                //shut down
                Server.remove( socket );
                output.close();
                input.close();
                socket.close();
            }
            catch ( IOException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
