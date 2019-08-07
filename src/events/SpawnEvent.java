package events;

import game_objects.SpawnPoint;

/**
 * SpawnEvent represents any time that a character spawns.
 *
 * Holds the GUID of the game object and where it spawns.
 *
 * @author jeremypark
 *
 */
public class SpawnEvent extends Event {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int GUID;
    private SpawnPoint spawnPoint;

    public SpawnEvent(long timeStamp, long timeToHandle, int GUID, SpawnPoint spawnPoint) {
        setTimeStamp(timeStamp);
        setTimeToHandle(timeToHandle);
        setGUID( GUID );
        setSpawnPoint( spawnPoint );
        setType("SPAWN");
        setPriority(5);
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
     * @return the spawnPoint
     */
    public SpawnPoint getSpawnPoint () {
        return spawnPoint;
    }
    /**
     * @param spawnPoint the spawnPoint to set
     */
    private void setSpawnPoint ( SpawnPoint spawnPoint ) {
        this.spawnPoint = spawnPoint;
    }
}
