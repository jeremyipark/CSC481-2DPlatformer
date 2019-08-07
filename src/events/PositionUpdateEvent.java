package events;

import java.io.Serializable;

/**
 * Represents where a game object currently is on the map.
 *
 * Contains x and y and the GUID of the game object.
 *
 * @author jeremypark
 *
 */
public class PositionUpdateEvent extends Event implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int x;
    private int y;
    private int GUID;

    public PositionUpdateEvent (long timeStamp, long timeToHandle, int x, int y, int GUID) {
        setTimeStamp(timeStamp);
        setTimeToHandle(timeToHandle);
        setType("POSITION");
        this.setX( x );
        this.setY( y );
        this.setGUID( GUID );
        setPriority(2);
    }

    /**
     * @return the x
     */
    public int getX () {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX ( int x ) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY () {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY ( int y ) {
        this.y = y;
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
    public void setGUID ( int gUID ) {
        GUID = gUID;
    }

}
