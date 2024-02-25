import java.util.EventObject;

public class MusicChangeEvent extends EventObject{

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public MusicChangeEvent(Object source) {
        super(source);
    }
}