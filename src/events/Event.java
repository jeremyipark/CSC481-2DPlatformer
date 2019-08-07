package events;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Event represents a general event in the game.
 * @author jeremypark
 *
 */
public class Event implements Serializable, Comparable<Event>, Comparator<Event> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public String type;
    public Object args;

    private long timeStamp;
    private long timeToHandle;

    public int priority;

    public Event () {};

    // stamp the time
    public Event (long timeStamp, long offset) {
        this.timeStamp = timeStamp;
        timeToHandle = timeStamp + offset;
    }

    public String getType () {
        return type;
    }
    public void setType ( String type ) {
        this.type = type;
    }
    public Object getArgs () {
        return args;
    }
    public void setArgs ( Object args ) {
        this.args = args;
    }

    public void setTimeStamp (long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public long getTimeToHandle() {
        return timeToHandle;
    }

    public void setTimeToHandle(long timeToHandle) {
        this.timeToHandle = timeToHandle;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    /**
     * We want to organize the priority queue based on lowest timestamp
     */
    @Override
    public int compare ( Event e1, Event e2 ) {
        // organize based on lowest timestamp!
        if (e1.getTimeToHandle() < e2.getTimeToHandle()) {
            return -1;
        } else if (e1.getTimeToHandle() > e2.getTimeToHandle()) {
            return 1;
        } else if (e1.getTimeToHandle() == e2.getTimeToHandle()) {

            // if you have the same timestamp, go based off priority number
            if (e1.priority < e2.priority) {
                return -1;
            }
            else if (e1.priority > e2.priority) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public int compareTo ( Event e2 ) {
        // organize based on lowest timestamp!
        if (this.getTimeToHandle() < e2.getTimeToHandle()) {
            return -1;
        } else if (this.getTimeToHandle() > e2.getTimeToHandle()) {
            return 1;
        } else if (this.getTimeToHandle() == e2.getTimeToHandle()) {

            // if you have the same timestamp, go based off priority number
            if (this.priority < e2.priority) {
                return -1;
            }
            else if (this.priority > e2.priority) {
                return 1;
            }
        }
        return 0;
    }
}
