package test;

public class ExampleServer implements Runnable
{

    @Override
    public void run () {
        // TODO Auto-generated method stub

    }
    //
    //    private static CopyOnWriteArrayList<ObjectInputStream> input_streams; // all of the clients
    //    private static CopyOnWriteArrayList<ObjectOutputStream> output_streams; // all of the client
    //    private static CopyOnWriteArrayList<Socket> sockets = new CopyOnWriteArrayList<Socket>(); // all of the client
    //    private static ArrayBlockingQueue<UpdateEvent> eventQueue = new ArrayBlockingQueue<UpdateEvent>(1024);
    //    private static ServerSocket serverSocket; //server socket accepting connections
    //    private static final int portNumber = 9001;
    //
    //    public ExampleServer() { }
    //
    //    /**
    //     * Thread's run method, which indefinitely accepts client connections
    //     */
    //    public void run () {
    //        while (true) {
    //            // Client Socket to communicate with the server
    //            Socket clientSocket = null;
    //
    //            // Wait for a client connection.
    //            try {
    //                clientSocket = serverSocket.accept();
    //                ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
    //                ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
    //                sockets.add( clientSocket );
    //                output_streams.add( output );
    //                input_streams.add( input );
    //                ServerThread serverThread = new ServerThread(clientSocket, output, input, eventQueue);
    //                serverThread.start();
    //
    //                System.out.println("Client connection made, streams added, and thread started");
    //            }
    //            catch ( Exception e ) {
    //                try {
    //                    clientSocket.close();
    //                }
    //                catch ( IOException e1 ) {
    //                    e1.printStackTrace();
    //                }
    //
    //                e.printStackTrace();
    //            }
    //        }
    //    }
    //
    //    public static void main(String[] args) throws IOException, InterruptedException
    //    {
    //
    //        // Create the server socket
    //        System.out.println("Welcome to Boxario! Please create a character.");
    //        serverSocket = new ServerSocket(9001);        // create the server socket
    //        input_streams = new CopyOnWriteArrayList<ObjectInputStream>();    // make the data structure for all of the streams
    //        output_streams = new CopyOnWriteArrayList<ObjectOutputStream>();
    //
    //        ExampleServer server = new ExampleServer(); // create a server
    //
    //        (new Thread(server)).start(); // start the server
    //
    //        while(true)
    //        {
    //            UpdateEvent update = eventQueue.take();
    //
    //            if (update.getName().equals("QUIT")) {
    //                System.out.println("Player quits");
    //            }
    //
    //            System.out.println("Server broadcasting event to all clients: " + update.getName());
    //
    //            for(ObjectOutputStream dout : output_streams)
    //            {
    //                try {
    //                    dout.writeObject(update);
    //                }
    //                catch (SocketException e) {
    //
    //                }
    //            }
    //        }
    //    }
    //
    //    public static void remove(Socket s) throws IOException {
    //        int outputSize = output_streams.size();
    //
    //        for (int i = 0; i < sockets.size(); i++) {
    //            if (sockets.get( i ) == s) {
    //                input_streams.remove( i );
    //                output_streams.remove( i );
    //                sockets.remove( i );
    //            }
    //        }
    //
    //        int newOutputSize = output_streams.size();
    //
    //        if (newOutputSize < outputSize) {
    //            System.out.println("Output stream removed");
    //        }
    //    }
}
