package time;

import java.io.Serializable;

/**
 * RealTimeline rpresents a Timeline anchored in real time.
 *
 * Meaning, it is anchored by System.currentTimeMillis().
 *
 * @author jeremypark
 *
 */
public class RealTimeline implements Timeline, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private long startTime;
    private long pausePoint = 0;
    private long totalPauseTime = 0;
    private int ticSize;
    private boolean paused = false;
    private boolean recording = false;

    /**
     * Start the timeline, anchored by real time
     */
    @Override
    public void start () {
        startTime = System.currentTimeMillis();
    }

    /**
     * Set the tic size
     */
    @Override
    public void setTicSize ( int ticSize ) {
        this.ticSize = ticSize;
    }

    public void changeTicSize(double multiplier) {
        ticSize *= multiplier;
    }

    /**
     * Get the tic size
     */
    @Override
    public int getTicSize () {
        return ticSize;
    }

    /**
     * Game time = real time - pause time.
     * @return game time
     */
    @Override
    public long getTime() {
        return getRealTime() - getTotalPauseTime();
    }

    /**
     * Get the current time
     */
    public long getRealTime () {
        return (System.currentTimeMillis() - startTime) / ticSize;
    }

    /**
     * Get current pause interval
     */
    public long getPauseInterval () {
        if (!paused) {
            return 0;
        }

        long currentTime = System.currentTimeMillis();
        return (currentTime - pausePoint) / ticSize;
    }

    /**
     * Get total pause time
     * @return total pause time
     */
    public long getTotalPauseTime() {
        if (paused) {
            return (totalPauseTime + getPauseInterval());
        }
        else {
            return (totalPauseTime);
        }
    }

    // check to see if paused
    public boolean isPaused() {
        return paused;
    }

    /**
     * Pause represents time that the game is paused.
     */
    @Override
    public void pause () {
        // if it is not already paused
        if (!paused) {
            pausePoint = System.currentTimeMillis();  // get the current time
            paused = true;
        }
    }

    /**
     * Play the game again
     * Set the total pause time.
     */
    @Override
    public void play () {
        // if you are paused
        if (paused) {
            totalPauseTime += getPauseInterval();
            paused = false;
        }
    }

    public void record() {
        if (!recording) {
            recording = true;
        }
    }

    public void replay() {
        if (recording) {
            recording = false;
        }
    }

    public boolean isRecording() {
        return recording;
    }

    // set half time
    public void halfSpeed() {
        // game server cannot create replays
    }

    // set normal time
    public void normalSpeed() {
        // game server cannot create replays
    }

    // set double time
    public void doubleSpeed() {
        // game server cannot create replays
    }
}
