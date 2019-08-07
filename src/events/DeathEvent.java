package events;

/**
 * DeathEvent represents a death for a user.
 *
 * DeathEvent just needs a GUID of the character that died.
 *
 * @author jeremypark
 *
 */
public class DeathEvent extends Event {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int GUID;

    public DeathEvent(long timeStamp, long timeToHandle, int GUID) {
        setTimeStamp(timeStamp);
        setTimeToHandle(timeToHandle);
        this.GUID = GUID;
        setType("DEATH");
        setPriority(4);
    }

    /**
     * Get the GUID of the box that died
     * @return GUID of the box that died
     */
    public int getGUID() {
        return GUID;
    }
}
