package time;

import java.io.Serializable;

/**
 * LocalTimeline represents a local timeline.
 *
 * You can anchor a local timeline in any other timeline.
 *
 * @author jeremypark
 *
 */
public class LocalTimeline implements Timeline, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private long startTime;
    private long pausePoint = 0;
    private long totalPauseTime = 0;
    private int ticSize;
    private boolean paused = false;
    private Timeline anchor = null;
    private long currentTime = 0;

    private boolean recording = false;

    private boolean stopped = false;

    private long speed;

    // how do i set this?
    private int offset;

    private boolean normalSpeed = true;
    private boolean doubleSpeed = false;
    private boolean halfSpeed = false;

    private long originalStartTime;

    /**
     * Anchor the local timeline in another timeline
     * @param timeline to anchor to
     */
    public void anchorTimeline (Timeline timeline) {
        anchor = timeline;
    }

    /**
     * Start the timeline
     * Get the current anchor's time
     */
    @Override
    public void start () {
        if (anchor != null) {
            startTime = anchor.getTime();
            originalStartTime = startTime;
        }

        normalSpeed = true;
    }

    /**
     * Set the tic size
     */
    @Override
    public void setTicSize ( int ticSize ) {
        this.ticSize = ticSize;
    }

    /**
     * Get the tic size
     */
    @Override
    public int getTicSize () {
        return ticSize;
    }

    @Override
    public void pause () {
        // if it is not already paused
        if (!paused) {
            pausePoint = anchor.getTime();  // get the current time
            paused = true;
        }
    }

    @Override
    public void play () {
        // if you are paused
        if (paused) {
            totalPauseTime += getPauseInterval();
            paused = false;
        }
    }

    @Override
    public long getTime () {
        if (!stopped) {
            currentTime = getLocalTime() - getTotalPauseTime();
        }

        return currentTime;
    }

    private long getLocalTime() {
        return (anchor.getTime() - startTime) / ticSize;
    }

    // set half time
    public void halfSpeed() {
        // double the elapsed time
        startTime = startTime - ((anchor.getTime() - startTime));
    }

    // set normal time
    public void normalSpeed() {
        startTime = originalStartTime;
    }

    // set double time
    public void doubleSpeed() {
        // half the elapsed time
        startTime = ((anchor.getTime() - startTime) / 2) + startTime;
    }

    // compute tics elapsed

    /**
     * Get total pause time
     * @return total pause time
     */
    private long getTotalPauseTime() {
        if (paused) {
            return (totalPauseTime + getPauseInterval());
        }
        else {
            return (totalPauseTime);
        }
    }

    /**
     * Get the pause interval
     * @return pause interval
     */
    private long getPauseInterval () {
        if (!paused) {
            return 0;
        }

        long currentTime = anchor.getTime();
        return (currentTime - pausePoint) / ticSize;
    }

    public boolean isPaused() {
        return paused;
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

    public boolean isStopped() {
        return stopped;
    }

    /**
     * Adjust the tic size
     * @param multiplier
     */
    public void changeTicSize(double multiplier) {
        ticSize *= multiplier;
    }

    public void stop() {
        System.out.println( "**********STOP***********" );

        if (!stopped) {
            stopped = true;
        }
    }
}
