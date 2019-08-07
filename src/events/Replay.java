package events;

import java.util.PriorityQueue;

import time.LocalTimeline;

/**
 * Replay represents a replay of events.
 *
 * This involves a new timeline with an adjustable timeline,
 * as well as a queue of events that occurred during that time.
 *
 * @author jeremypark
 *
 */
public class Replay extends Event {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // time that replay lasts
    private volatile LocalTimeline replayTimeline;

    // queue of events that occur!
    private volatile PriorityQueue<Event> replayQueue;

    public Replay(LocalTimeline replayTimeline, PriorityQueue<Event> replayQueue) {
        this.replayTimeline = replayTimeline;
        this.replayTimeline.start();
        this.replayQueue = replayQueue;
    }

    public LocalTimeline getTimeline() {
        return replayTimeline;
    }

    public PriorityQueue<Event> getReplayQueue() {
        return replayQueue;
    }
}
