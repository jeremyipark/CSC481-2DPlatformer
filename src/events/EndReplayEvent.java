package events;

/**
 * Represents the beginning of a replay.
 *
 * This event will restore the original tic size.
 *
 * @author jeremypark
 *
 */
public class EndReplayEvent extends Event {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // tic size for the replay
    private final int TIC_SIZE = 1;

    public EndReplayEvent(long timeStamp, long timeToHandle) {
        setTimeStamp(timeStamp);
        setTimeToHandle(timeToHandle);
        setType("END_REPLAY");
        setPriority(1);
    }

    /**
     * @return the ticSize
     */
    public int getTicSize () {
        return TIC_SIZE;
    }
}
