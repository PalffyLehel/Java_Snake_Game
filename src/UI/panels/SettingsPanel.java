package UI.panels;

import listeners.ButtonListener;
import events.SettingsButtonEvent;
import collection.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SettingsPanel extends JPanel {
    private ButtonListener manager;

    private final JTextField speedTextField;
    private final JTextField widthTextField;
    private final JTextField heightTextField;
    private final JSlider musicPower;
    private final JSlider soundPower;

    public SettingsPanel() {
        JLabel speedLabel = new JLabel("Speed (ms): ");
        speedTextField = new JFormattedTextField();
        speedTextField.setPreferredSize(new Dimension(50, 30));
        speedTextField.setHorizontalAlignment(SwingConstants.CENTER);
        speedTextField.setText(Settings.SPEED.toString());

        JLabel widthLabel = new JLabel("Width: ");
        widthTextField = new JFormattedTextField();
        widthTextField.setPreferredSize(new Dimension(50, 30));
        widthTextField.setHorizontalAlignment(SwingConstants.CENTER);
        widthTextField.setText(Settings.WIDTH.toString());

        JLabel heightLabel = new JLabel("Height: ");
        heightTextField = new JFormattedTextField();
        heightTextField.setPreferredSize(new Dimension(50, 30));
        heightTextField.setHorizontalAlignment(SwingConstants.CENTER);
        heightTextField.setText(Settings.HEIGHT.toString());

        JLabel musicLabel = new JLabel("Music: (%)");
        musicPower = new JSlider();
        musicPower.setMaximum(100);
        musicPower.setMinimum(0);
        musicPower.setValue(Settings.MUSIC_VOLUME);

        JLabel soundLabel = new JLabel("Sound: (%)");
        soundPower = new JSlider();
        soundPower.setMaximum(100);
        soundPower.setMinimum(0);
        soundPower.setValue(Settings.SOUND_VOLUME);

        JButton applyChangesButton = new JButton("Apply");
        applyChangesButton.addActionListener(e -> {
            try {
                BufferedWriter bfw = new BufferedWriter(new FileWriter("src/Settings"));

                Settings.SPEED = Integer.parseInt(speedTextField.getText());
                bfw.append(speedTextField.getText());
                bfw.newLine();

                Settings.WIDTH = Integer.parseInt(widthTextField.getText());
                System.out.println(Settings.WIDTH);
                bfw.append(widthTextField.getText());
                bfw.newLine();

                Settings.HEIGHT = Integer.parseInt(heightTextField.getText());
                System.out.println(Settings.HEIGHT);
                bfw.append(heightTextField.getText());
                bfw.newLine();

                Settings.MUSIC_VOLUME = musicPower.getValue();
                System.out.println(Settings.HEIGHT);
                bfw.append(String.valueOf(musicPower.getValue()));
                bfw.newLine();

                Settings.SOUND_VOLUME = soundPower.getValue();
                System.out.println(Settings.HEIGHT);
                bfw.append(String.valueOf(soundPower.getValue()));
                bfw.newLine();

                bfw.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.settingsBack(new SettingsButtonEvent(this));
            }
        });

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(20, 20, 20,20);

        constraints.weightx = 0.0;
        constraints.weighty = 0.5;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(speedLabel, constraints);
        constraints.weightx = 1;
        constraints.gridx = 1;
        add(speedTextField, constraints);

        constraints.weightx = 0.0;
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(widthLabel, constraints);
        constraints.weightx = 1;
        constraints.gridx = 1;
        add(widthTextField, constraints);

        constraints.weightx = 0.0;
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(heightLabel, constraints);
        constraints.weightx = 1;
        constraints.gridx = 1;
        add(heightTextField, constraints);

        constraints.weightx = 0.0;
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(musicLabel, constraints);
        constraints.weightx = 1;
        constraints.gridx = 1;
        add(musicPower, constraints);

        constraints.weightx = 0.0;
        constraints.gridx = 0;
        constraints.gridy = 4;
        add(soundLabel, constraints);
        constraints.weightx = 1;
        constraints.gridx = 1;
        add(soundPower, constraints);


        constraints.weightx = 1;
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        constraints.gridx = 0;
        constraints.gridy = 5;
        add(applyChangesButton, constraints);

        constraints.gridx = 3;
        add(backButton, constraints);
    }

    public SettingsPanel(String name) {
        this();
        setName(name);
    }

     public void setButtonListener(ButtonListener listener) {
        manager = listener;
    }
}
