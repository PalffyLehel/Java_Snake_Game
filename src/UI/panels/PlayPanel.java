package UI.panels;

import collection.Settings;
import events.GameBackEvent;
import events.GameEndEvent;
import events.ScoreIncreasedEvent;
import events.SpeedUpEvent;
import listeners.ButtonListener;
import listeners.GameButtonListener;
import listeners.GamePickupListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayPanel extends JPanel implements GamePickupListener {
    private GameButtonListener gameManager;
    private ButtonListener manager;

    private final JButton backButton;

    private final JLabel highScoreLabel;
    private final JLabel scoreLabel;
    private final JLabel speedLabel;
    private int points;

    private final GamePanel gamePanel;

    public PlayPanel() {
        gamePanel = new GamePanel();

        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int localHighScore = Integer.parseInt(scoreLabel.getText().split(" ")[1]);
                if (Settings.HIGH_SCORE < localHighScore) {
                    try {
                        Scanner fr = new Scanner(new File("src/Settings"));
                        List<String> lines = new ArrayList<>();
                        while (fr.hasNextLine()) {
                            String line = fr.nextLine();
                            if (!fr.hasNextLine()) {
                                break;
                            }
                            lines.add(line);
                        }
                        fr.close();

                        BufferedWriter bfw  = new BufferedWriter(new FileWriter("src/Settings"));
                        lines.forEach(l -> {
                            try {
                                bfw.append(l);
                                bfw.newLine();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        });
                        Settings.HIGH_SCORE = localHighScore;
                        bfw.append(String.valueOf(localHighScore));
                        bfw.newLine();

                        bfw.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }


                manager.gameBack(new GameBackEvent(backButton));
            }
        });
        backButton.setEnabled(false);

        highScoreLabel = new JLabel();
        highScoreLabel.setText("High score: " + Settings.HIGH_SCORE);
        scoreLabel = new JLabel("Score: 0");
        points = 0;
        speedLabel = new JLabel();
        speedLabel.setText("Speed " + Settings.SPEED + " ms");

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        dataPanel.add(highScoreLabel);
        dataPanel.add(scoreLabel);
        dataPanel.add(speedLabel);
        dataPanel.add(backButton);

        setLayout(new BorderLayout());
        add(gamePanel, BorderLayout.CENTER);
        add(dataPanel, BorderLayout.WEST);
    }

    public PlayPanel(String name) {
        this();
        setName(name);
    }

    public void setListener(GameButtonListener listener) {
        gameManager = listener;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void resizePanel() {
        gamePanel.resizePanel();
    }

    @Override
    public void speedUp(SpeedUpEvent event) {
        speedLabel.setText("Speed " + Settings.SPEED + " ms");
    }

    @Override
    public void scoreIncrease(ScoreIncreasedEvent event) {
        scoreLabel.setText("Score: " + ++points);
        if (points > Settings.HIGH_SCORE) {
            highScoreLabel.setText("High score: " + points);
        }
    }

    @Override
    public void gameEnded(GameEndEvent event) {
        enableBackButton(true);
        gameManager.gameEnded(event);
    }

    public void enableBackButton(boolean enabled) {
        backButton.setEnabled(enabled);
    }

    public void setButtonListener(ButtonListener listener) {
        manager = listener;
    }

    public void resetPanel() {
        enableBackButton(false);
        Settings.SPEED = Settings.ORIGINAL_SPEED;
        speedLabel.setText("Speed: " + Settings.SPEED + " ms");
        scoreLabel.setText("Score: 0");
        highScoreLabel.setText("High score: " + Settings.HIGH_SCORE);
        points = 0;
    }
}
