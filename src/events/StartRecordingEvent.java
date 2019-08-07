package events;

/**
 * Represents the beginning of the recording.
 * @author jeremypark
 *
 */
public class StartRecordingEvent extends Event {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public StartRecordingEvent(long timeStamp, long timeToHandle) {
        setTimeStamp(timeStamp);
        setTimeToHandle(timeToHandle);
        setType("START_RECORDING");
        setPriority(1);
    }
}
