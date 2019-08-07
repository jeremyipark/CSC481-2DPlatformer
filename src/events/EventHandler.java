package events;

/**
 * Describes the general ability to handle an event.
 * @author jeremypark
 *
 */
public interface EventHandler {

    /**
     * Handling of an event
     * @param e event
     */
    public void onEvent (Event e);
}
