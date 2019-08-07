package events;

/**
 * Represents the beginning of a replay.
 *
 * This event will send a tic size for the replay speed.
 *
 * @author jeremypark
 *
 */
public class StartReplayEvent extends Event {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // tic size for the replay
    private int ticSize;

    public StartReplayEvent(long timeStamp, long timeToHandle, int ticSize) {
        setTimeStamp(timeStamp);
        setTimeToHandle(timeToHandle);
        setTicSize(ticSize);
        setType("START_REPLAY");
        setPriority(1);
    }

    /**
     * @return the ticSize
     */
    public int getTicSize () {
        return ticSize;
    }

    /**
     * @param ticSize the ticSize to set
     */
    public void setTicSize ( int ticSize ) {
        this.ticSize = ticSize;
    }
}
