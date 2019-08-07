package game_objects;

import java.util.ArrayList;
import java.util.Random;

import server.GameServer;

/**
 * SpawnPoint represents four locations on the screen where a character can spawn from:
 * The options are: top left, top right, bottom left, bottom right.
 * @author jeremypark
 *
 */
public class SpawnGenerator {
    static SpawnGenerator spawnGenerator = null;
    public static ArrayList<SpawnPoint> spawnPoints = new ArrayList<SpawnPoint>();

    public SpawnGenerator() {
        SpawnGenerator.addSpawn( new SpawnPoint(GameServer.newGUID(), 0, 0 ) );
        SpawnGenerator.addSpawn( new SpawnPoint(GameServer.newGUID(), 800, 0 ) );
        SpawnGenerator.addSpawn( new SpawnPoint(GameServer.newGUID(), 0, Terrain.GROUND_LEVEL) );
        SpawnGenerator.addSpawn( new SpawnPoint(GameServer.newGUID(), 800, Terrain.GROUND_LEVEL) );
    }

    /**
     * Set the x and y of a spawn point
     * @param x location of spawn point
     * @param y location of spawn point
     */
    public static void addSpawn(SpawnPoint spawn) {
        spawnPoints.add( spawn );
    }

    /**
     * Set sent game object's x and y equal to a random spawn location
     * @param obj game object to place on map
     */
    public static void spawn (GameObject obj) {
        Random rand = new Random();
        int spawnNumber = rand.nextInt(spawnPoints.size());
        SpawnPoint spawnSpot = spawnPoints.get(spawnNumber);

        if (spawnSpot.x <= obj.width) {
            obj.x = spawnSpot.x;
        } else {
            obj.x = spawnSpot.x - obj.width;
        }

        if (spawnSpot.y <= obj.height) {
            obj.y = spawnSpot.y;
        } else {
            obj.y = spawnSpot.y - obj.height;
        }
    }

    public static SpawnGenerator getSpawnGenerator() {
        if (spawnGenerator == null) {
            spawnGenerator = new SpawnGenerator();
        }

        return spawnGenerator;
    }
}