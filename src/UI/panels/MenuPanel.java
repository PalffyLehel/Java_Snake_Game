package UI.panels;

import exceptions.NoPanelException;
import listeners.ButtonListener;
import events.MenuButtonEvent;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private final JButton gameButton;
    private final JButton settingsButton;

    private ButtonListener manager;

    public MenuPanel() {
        gameButton = new JButton("Start game");
        gameButton.addActionListener(e -> {
            MenuButtonEvent event = new MenuButtonEvent(gameButton,"play");
            try {
                manager.menuButtonPressed(event);
            } catch (NoPanelException ex) {
                throw new RuntimeException(ex);
            }
        });
        int WIDTH = 350;
        int HEIGHT = 100;
        gameButton.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        settingsButton = new JButton("Settings");
        settingsButton.addActionListener(e -> {
            MenuButtonEvent event = new MenuButtonEvent(settingsButton,"settings");
            try {
                manager.menuButtonPressed(event);
            } catch (NoPanelException ex) {
                throw new RuntimeException(ex);
            }
        });
        settingsButton.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        exitButton.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 0,0, 0);

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(gameButton, constraints);

        constraints.gridy = 1;
        add(settingsButton, constraints);

        constraints.gridy = 2;
        add(exitButton, constraints);
    }

    public MenuPanel(String name) {
        this();
        setName(name);
    }

    public void setButtonListener(ButtonListener listener) {
        manager = listener;
    }
}
