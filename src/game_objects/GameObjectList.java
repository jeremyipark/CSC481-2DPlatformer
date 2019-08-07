package game_objects;

import java.io.Serializable;
import java.util.ArrayList;

public class GameObjectList extends ArrayList<GameObject> implements Serializable{
    //private ArrayList<GameObject> gameObjects;
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public GameObjectList() {
        //gameObjects = new ArrayList<GameObject>();
    }

    //    public void add(GameObject obj) {
    //        gameObjects.add(obj);
    //    }
    //
    //    public int size() {
    //        return gameObjects.size();
    //    }
    //
    //    public GameObject get(int i ) {
    //        return gameObjects.get(i);
    //    }

    /**
     * Get by GUID
     * @param GUID of box
     * @return box
     */
    public GameObject getByGUID(int GUID) {
        for (int i = 0; i < size(); i++) {
            if (get(i).getGUID() == GUID) {
                return get(i);
            }
        }

        return null;
    }

    /**
     * Remove
     * @param GUID
     */
    public void removeByGUID(int GUID) {
        for (int i = 0; i < size(); i++) {
            if (get(i).getGUID() == GUID) {
                remove(i);
            }
        }
    }

    //    private void readObject(ObjectInputStream input) throws ClassNotFoundException, IOException
    //    {
    //        Object readObj;
    //        while ((readObj = input.readObject()) != null) {
    //            GameObject obj = (GameObject) readObj;
    //            add( obj );
    //        }
    //    }

    //    private void writeObject(ObjectOutputStream output) throws IOException
    //    {
    //        for (int i = 0; i < size(); i++) {
    //            output.writeObject(get(i));
    //        }
    //    }
}
