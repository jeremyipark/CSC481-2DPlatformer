package events;

import game_objects.Box;

/**
 * Represents the entrance of a new player to the system.
 * @author jeremypark
 *
 */
public class NewPlayerEvent extends Event {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // GUID of the box
    private int GUID;

    // box
    private Box box;

    public NewPlayerEvent(long timeStamp, long timeToHandle, int GUID, Box box) {
        setTimeStamp(timeStamp);
        setTimeToHandle(timeToHandle);
        setGUID( GUID );
        setBox( box );
        setType("NEW_PLAYER");
        setPriority(1);
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
     * @return the box
     */
    public Box getBox () {
        return box;
    }
    /**
     * @param box the box to set
     */
    private void setBox ( Box box ) {
        this.box = box;
    }
}
