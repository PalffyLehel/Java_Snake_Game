package events;

import java.util.EventObject;

public class GameStartEvent extends EventObject {
    public GameStartEvent(Object source) {
         super(source);
    }
}
