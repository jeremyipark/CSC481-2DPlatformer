package game_objects;

import java.io.Serializable;

import abstract_components.Sizeable;

/**
 * Death zone represents the non-rendered location in the map that,
 * if the user hits, they die :(
 * @author jeremypark
 *
 */
public class DeathZone extends GameObject implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // size component of death zone
    Sizeable sizeComponent;

    public DeathZone (int GUID, Sizeable sizeComponent) {
        super( GUID );
        this.sizeComponent = sizeComponent;
        sizeComponent.setDimensions( this );
    }
    public void update () {
        // update!
    }

    /**
     * Draw
     */
    @Override
    public void draw () {
        //renderComponent.display( this );
    }
}
