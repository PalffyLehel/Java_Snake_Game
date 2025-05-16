package UI;

import collection.Direction;
import collection.Settings;
import collection.Tuple;
import core.Snake;
import core.SnakeController;
import events.GameEndEvent;
import events.GameStartEvent;
import exceptions.NoPanelException;
import listeners.GameButtonListener;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;

public class AppFrame extends JFrame implements GameButtonListener {
    private final PanelManager panelController;
    private SnakeController snakeController;
    private Snake player;

    public AppFrame() {
        setBounds(200, 0, 1000, 1000);
        setName("asd");
        setResizable(false);

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (player == null) {
                    return;
                }
                switch (e.getKeyChar()) {
                    case 'w':
                        if (player.getLastDirection() != Direction.DOWN) {
                            Settings.DIRECTION = Direction.UP;
                        }
                        break;
                    case 'a':
                        if (player.getLastDirection() != Direction.RIGHT) {
                            Settings.DIRECTION = Direction.LEFT;
                        }
                        break;
                    case 's':
                        if (player.getLastDirection() != Direction.UP) {
                            Settings.DIRECTION = Direction.DOWN;
                        }
                        break;
                    case 'd':
                        if (player.getLastDirection() != Direction.LEFT) {
                            Settings.DIRECTION = Direction.RIGHT;
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        try {
            BufferedReader bfr = new BufferedReader(new FileReader("src/Settings"));

            Settings.SPEED = Integer.parseInt(bfr.readLine());
            Settings.ORIGINAL_SPEED = Settings.SPEED;
            Settings.WIDTH = Integer.parseInt(bfr.readLine());
            Settings.HEIGHT = Integer.parseInt(bfr.readLine());
            Settings.MUSIC_VOLUME = Integer.parseInt(bfr.readLine());
            Settings.SOUND_VOLUME = Integer.parseInt(bfr.readLine());
            Settings.HIGH_SCORE = Integer.parseInt(bfr.readLine());

            bfr.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        panelController = new PanelManager(this);
        panelController.setListener(this);
        panelController.getPlayPanel().setListener(this);

        try {
            setContentPane(panelController.getPanel("menu"));
        } catch (NoPanelException e) {
            throw new RuntimeException(e);
        }

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void gameStarted(GameStartEvent event) {
        panelController.getGamePanel().setCollided(false);
        panelController.getPlayPanel().resetPanel();
        player = new Snake(new Tuple(0, 0));
        snakeController = new SnakeController(player, panelController.getGamePanel(), panelController.getPlayPanel());
        Settings.IS_RUNNING = true;
        Settings.DIRECTION = Direction.RIGHT;
        snakeController.start();
    }

    @Override
    public void gameEnded(GameEndEvent event) {
        snakeController.stopBackgroundMusic();
        Settings.IS_RUNNING = false;
        player = null;
    }
}
