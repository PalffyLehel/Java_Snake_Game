package events;

import java.util.EventObject;

public class ScoreIncreasedEvent extends EventObject {
    public ScoreIncreasedEvent(Object source) {
        super(source);
    }
}
