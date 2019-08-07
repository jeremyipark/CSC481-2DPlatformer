package events;

/**
 * Represents the end of the recording
 * @author jeremypark
 *
 */
public class EndRecordingEvent extends Event {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public EndRecordingEvent(long timeStamp, long timeToHandle) {
        setTimeStamp(timeStamp);
        setTimeToHandle(timeToHandle);
        setType("END_RECORDING");
        setPriority(1);
    }
}
