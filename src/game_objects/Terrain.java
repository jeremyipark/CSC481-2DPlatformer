package game_objects;

import java.io.Serializable;

import abstract_components.Renderable;
import abstract_components.Sizeable;
import concrete_components.DrawComponent;

/**
 * Terrain represents the ground that characters will move on.
 * @author jeremypark
 *
 */
public class Terrain extends GameObject implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Renderable renderComponent = new DrawComponent();
    Sizeable sizeComponent;
    public static int GROUND_LEVEL = 600;


    /**
     * Make a box character
     * @param width
     * @param height
     */
    public Terrain (int GUID, Sizeable sizeComponent, Renderable renderComponent) {
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
        update();
    }
}
