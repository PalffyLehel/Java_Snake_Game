package listeners;

import events.GameBackEvent;
import events.MenuButtonEvent;
import events.SettingsButtonEvent;
import exceptions.NoPanelException;

public interface ButtonListener {
    void menuButtonPressed(MenuButtonEvent menuEvent) throws NoPanelException;

    void settingsBack(SettingsButtonEvent settingsEvent);

    void gameBack(GameBackEvent backEvent);
}
