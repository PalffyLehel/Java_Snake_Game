package listeners;

import events.GameEndEvent;
import events.ScoreIncreasedEvent;
import events.SpeedUpEvent;

public interface GamePickupListener {
    void speedUp(SpeedUpEvent event);

    void scoreIncrease(ScoreIncreasedEvent event);

    void gameEnded(GameEndEvent event);
}
