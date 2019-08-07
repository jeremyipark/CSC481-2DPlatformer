package events;

import java.io.Serializable;

/**
 * UpdateEvent represents an update made by the client, which is also received by the server.
 * UpdateEvent has a name, which is used to distinguish the action to be performed.
 * UpdateEvent also has the GUID of the box to move.
 *
 * This is serializable to send over the network.
 * @author jeremypark
 *
 */
public class KeyPressEvent extends Event implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * GUID of box
     */
    private int GUID;

    /**
     * Update event
     * @param name of event
     * @param GUID of box
     */
    public KeyPressEvent(String type, int GUID) {
        setType(type);
        this.GUID = GUID;
        setPriority(1);

    }

    /**
     * GUID of box
     * @return guid of box
     */
    public int getGUID() {
        return GUID;
    }
}