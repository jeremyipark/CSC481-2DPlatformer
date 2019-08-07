package test;

import processing.core.PApplet;

public class SampleClient extends PApplet implements Runnable
{

    @Override
    public void run () {
        // TODO Auto-generated method stub

    }
    //    protected final int BACKGROUND_COLOR = color(155,196,250);
    //
    //    private static ObjectInputStream input;
    //    private static ObjectOutputStream output;
    //    private static ArrayBlockingQueue<UpdateEvent> eventQueue = new ArrayBlockingQueue<UpdateEvent>(1024);
    //    private static int GUID = 7;
    //    public static void main(String[] args)
    //    {
    //        PApplet.main("test.SampleClient");
    //        handleUserInput();
    //    }
    //
    //    public static void handleUserInput() {
    //        Socket s = null;
    //        try {
    //            s = new Socket("127.0.0.1", 9001);
    //            output = new ObjectOutputStream(s.getOutputStream());  // create output stream
    //            input = new ObjectInputStream(s.getInputStream()); // create input stream
    //        }
    //        catch ( Exception e1 ) {
    //
    //        }   // create a socket
    //
    //
    //        SampleClient sampleClient = new SampleClient();
    //
    //        (new Thread(sampleClient)).start(); // start the server
    //
    //        try {
    //            while(true)
    //            {
    //                UpdateEvent update = eventQueue.take();
    //
    //                output.writeObject( update );
    //            }
    //        } catch (Exception e) {
    //
    //        } finally {
    //            try {
    //                UpdateEvent update = new UpdateEvent("QUIT", GUID);
    //                output.writeObject( update );
    //                input.close();
    //                output.close();
    //                s.close();
    //            }
    //            catch (Exception e) {
    //
    //            }
    //        }
    //    }
    //
    //    @Override
    //    public void run () {
    //        while (true ) {
    //            UpdateEvent event = null;
    //
    //            try {
    //                event = (UpdateEvent) input.readObject();
    //            }
    //            catch ( Exception e ) {
    //                // howdy
    //            }
    //
    //            if (event != null) {
    //                System.out.println(event.getName());
    //            }
    //        }
    //    }
    //
    //    /**
    //     * Settings such as window size and setting masterScreen to this instance of Screen
    //     */
    //    public void settings(){
    //        size(800,800);
    //        smooth(10);
    //    }
    //
    //    public void draw() {
    //        background(BACKGROUND_COLOR);    //draw background
    //    }
    //
    //    /**
    //     * Interpret user keyboard input
    //     */
    //    public void keyPressed() {
    //        if (key == CODED) {
    //            if (keyCode == LEFT) {
    //                UpdateEvent left = new UpdateEvent("LEFT", GUID);
    //                try {
    //                    eventQueue.put(left);
    //                }
    //                catch ( Exception e ) {
    //                    // TODO Auto-generated catch block
    //                    e.printStackTrace();
    //                }
    //
    //            } else if (keyCode == RIGHT) {
    //                UpdateEvent right = new UpdateEvent("RIGHT", GUID);
    //
    //                try {
    //                    eventQueue.put(right);
    //                }
    //                catch ( Exception e ) {
    //                    // TODO Auto-generated catch block
    //                    e.printStackTrace();
    //                }
    //            }
    //        } else if (keyCode == 32) { // if space
    //            UpdateEvent up = new UpdateEvent("JUMP", GUID);
    //
    //            try {
    //                eventQueue.put(up);
    //            }
    //            catch ( Exception e ) {
    //                // TODO Auto-generated catch block
    //                e.printStackTrace();
    //            }
    //        }
    //    }
}
