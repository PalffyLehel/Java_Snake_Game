package events;

import java.util.EventObject;

public class GameEndEvent extends EventObject {
    public GameEndEvent(Object source) {
        super(source);
    }
}
