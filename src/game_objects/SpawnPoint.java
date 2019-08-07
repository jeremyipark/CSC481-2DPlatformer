package game_objects;

public class SpawnPoint extends GameObject {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public SpawnPoint (int GUID, int x, int y) {
        super( GUID );
        this.x = x;
        this.y = y;
    }

    public void update () {
        // don't do anything
    }

    /**
     * Draw
     */
    @Override
    public void draw () {
        //renderComponent.display( this );
    }

    /**
     * Get x value
     * @return x value
     */
    public int getX() {
        return x;
    }

    /**
     * Get y value
     * @return y value
     */
    public int getY() {
        return y;
    }


}
