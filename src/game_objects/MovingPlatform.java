package game_objects;

import java.io.Serializable;

import abstract_components.Movable;
import abstract_components.Renderable;
import abstract_components.Sizeable;
import concrete_components.MoveComponent;

public class MovingPlatform extends Platform implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    MoveComponent moveComponent; // movement component

    public MovingPlatform(int GUID, Sizeable sizeComponent, Renderable renderComponent, Movable moveComponent) {
        super(GUID, sizeComponent, renderComponent);
        this.moveComponent = (MoveComponent) moveComponent;
    }

    public void update () {
        renderComponent.display( this );
        moveComponent.platformMove( this );
    }
}
