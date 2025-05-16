package UI;

import UI.panels.GamePanel;
import UI.panels.MenuPanel;
import events.*;
import exceptions.NoPanelException;
import UI.panels.PlayPanel;
import UI.panels.SettingsPanel;
import listeners.ButtonListener;
import listeners.GameButtonListener;

import javax.swing.*;
import java.awt.*;

public class PanelManager implements ButtonListener {
    private final PlayPanel playPanel;
    private final SettingsPanel settingsPanel;
    private final MenuPanel menuPanel;

    private final JFrame frame;

    private GameButtonListener listener;

    public PanelManager(JFrame frame) {
        this.frame = frame;
        this.frame.setBackground(Color.BLACK);

        menuPanel = new MenuPanel("menu");
        menuPanel.setButtonListener(this);
        frame.setContentPane(menuPanel);

        settingsPanel = new SettingsPanel("settings");
        settingsPanel.setButtonListener(this);

        playPanel = new PlayPanel("play");
        playPanel.setButtonListener(this);
    }

    public JPanel getPanel(String name) throws NoPanelException {
        return switch (name) {
            case "menu" -> menuPanel;
            case "settings" -> settingsPanel;
            case "play" -> playPanel;
            default -> throw new NoPanelException();
        };
    }

    @Override
    public void menuButtonPressed(MenuButtonEvent menuEvent) throws NoPanelException {
        String panelType = menuEvent.getPanelType();

        JPanel panelToShow = switch (panelType) {
            case "menu" -> menuPanel;
            case "settings" -> settingsPanel;
            case "play" -> playPanel;
            default -> throw new NoPanelException();
        };

        if (panelType.equals("play")) {
            playPanel.resizePanel();
        }

        if (menuEvent.getPanelType().equals("play")) {
            GameStartEvent event = new GameStartEvent(menuEvent.getSource());
            listener.gameStarted(event);
        }

        frame.setContentPane(panelToShow);
        frame.setVisible(true);
    }

    public void setListener(GameButtonListener listener) {
        this.listener = listener;
    }

    @Override
    public void settingsBack(SettingsButtonEvent settingsEvent) {
        frame.setContentPane(menuPanel);
        frame.setVisible(true);
    }

    @Override
    public void gameBack(GameBackEvent backEvent) {
        frame.setContentPane(menuPanel);
        frame.setVisible(true);
    }

    public GamePanel getGamePanel() {
        return playPanel.getGamePanel();
    }
    public PlayPanel getPlayPanel() {
        return playPanel;
    }
}
