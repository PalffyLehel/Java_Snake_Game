package listeners;

import events.GameEndEvent;
import events.GameStartEvent;

public interface GameButtonListener {
    void gameStarted(GameStartEvent event);

    void gameEnded(GameEndEvent event);
}
