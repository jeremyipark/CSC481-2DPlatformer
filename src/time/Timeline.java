package time;

/**
 * This interface defines the key methods for a Timeline.
 *
 * This interface gives the road map for other timelines to make operations on time.
 * @author jeremypark
 *
 */
public interface Timeline {
    // start the timeline
    public void start();

    // get current time
    public long getTime();

    // pause the timeline
    public void pause();

    // play the timeline
    public void play();

    // set the tic size of the timeline
    public void setTicSize( int ticSize );

    // adjust the tic size
    public void changeTicSize(double multiplier);

    // get the tic size
    public int getTicSize();

    // see if the timeline is paused
    public boolean isPaused();

    // set half time
    public void halfSpeed();

    // set normal time
    public void normalSpeed();

    // set double time
    public void doubleSpeed();
}
