package game_objects;

import java.io.Serializable;

import abstract_components.Renderable;
import abstract_components.Sizeable;

/**
 * Platform represents a general platform that a character can jump onto.
 *
 * @author jeremypark
 *
 */
public class Platform extends GameObject implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Sizeable sizeComponent; // size of platform
    Renderable renderComponent; // platform rendering.

    public Platform(int GUID, Sizeable sizeComponent, Renderable renderComponent) {
        super( GUID );
        this.renderComponent = renderComponent;
        this.sizeComponent = sizeComponent;
        sizeComponent.setDimensions( this );
    }

    public void update () {
        renderComponent.display( this );
    }

    /**
     * Draw
     */
    @Override
    public void draw () {
        renderComponent.display( this );
    }
}
