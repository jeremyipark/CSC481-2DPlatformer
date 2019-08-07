package events;

/**
 * Represents a collision event.
 *
 * Holds the GUID of the game object that collided and on which side.
 *
 * @author jeremypark
 *
 */
public class CollisionEvent extends Event {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int GUID;
    private String direction;

    public CollisionEvent(long timeStamp, long timeToHandle, int GUID, String direction) {
        setTimeStamp(timeStamp);
        setTimeToHandle(timeToHandle);
        setGUID(GUID);
        setDirection(direction);
        setType("COLLISION");
        setPriority(3);
    }
    /**
     * @return the gUID
     */
    public int getGUID () {
        return GUID;
    }
    /**
     * @param gUID the gUID to set
     */
    private void setGUID ( int gUID ) {
        GUID = gUID;
    }
    /**
     * @return the direction
     */
    public String getDirection () {
        return direction;
    }
    /**
     * @param direction the direction to set
     */
    private void setDirection ( String direction ) {
        this.direction = direction;
    }
}
