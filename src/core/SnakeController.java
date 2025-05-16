package core;

import UI.panels.GamePanel;
import collection.Direction;
import collection.Settings;
import collection.Tuple;
import events.GameEndEvent;
import events.ScoreIncreasedEvent;
import events.SpeedUpEvent;
import listeners.GamePickupListener;

import java.util.Arrays;
import java.util.List;

public class SnakeController extends Thread {
    private final Snake player;
    private final GamePanel grid;

    private final List<Pickup> pickups;
    private final List<Pickup> speedPickups;

    private final int width_step;
    private final int height_step;

    private final GamePickupListener listener;

    private final SoundManager soundManager;

    public SnakeController(Snake player, GamePanel grid, GamePickupListener listener) {
        this.listener = listener;

        this.player = player;
        this.grid = grid;

        width_step = 31;
        height_step = 31;

        pickups = Arrays.stream(new Pickup[] {
                new Pickup(31, 31),
                new Pickup(31, 31),
                new Pickup(31, 31),
        }).toList();
        grid.setRegularPickups(pickups);

        speedPickups = Arrays.stream(new Pickup[] {
                new SpeedPickup(31, 31),
                new SpeedPickup(31, 31),
        }).toList();
        grid.setSpeedPickups(speedPickups);

        soundManager = new SoundManager();
    }

    @Override
    public void run() {
        while (Settings.IS_RUNNING) {
            grid.setPlayer(player.getPos());
            grid.repaint();

            try {
                Thread.sleep(Settings.SPEED);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            switch (Settings.DIRECTION) {
                case UP:
                    player.moveUp();
                    player.setLastDirection(Direction.UP);
                    break;
                case DOWN:
                    player.moveDown();
                    player.setLastDirection(Direction.DOWN);
                    break;
                case LEFT:
                    player.moveLeft();
                    player.setLastDirection(Direction.LEFT);
                    break;
                case RIGHT:
                    player.moveRight();
                    player.setLastDirection(Direction.RIGHT);
                    break;
            }

            Tuple head = player.getFirst();
            int x = head.getX();
            int y = head.getY();

            if (x < 0 || x >= width_step || y < 0 || y >= height_step || player.isColliding()) {
                grid.setCollided(true);
                grid.repaint();
                stopBackgroundMusic();
                Settings.IS_RUNNING = false;
            }

            Pickup aux;
            for (Pickup pickup : pickups) {
                aux = pickup;

                if (aux.getX() == x && aux.getY() == y) {
                    soundManager.playRegularSound();
                    replacePickup(aux, pickups);
                    listener.scoreIncrease(new ScoreIncreasedEvent(this));
                }
            }

            for (Pickup speedPickup : speedPickups) {
                aux = speedPickup;

                if (aux.getX() == x && aux.getY() == y) {
                    soundManager.playSpeedSound();
                    replacePickup(aux, speedPickups);
                    Settings.SPEED = Math.max(20, Settings.SPEED - 20);
                    listener.scoreIncrease(new ScoreIncreasedEvent(this));
                    listener.speedUp(new SpeedUpEvent(this));
                }
            }
        }

        listener.gameEnded(new GameEndEvent(this));
    }

    private void replacePickup(Pickup aux, List<Pickup> pickups) {
        player.increase();
        aux.reposition();
        while (!isUnique(aux, pickups)) {
            aux.reposition();
        }
    }

    private boolean isUnique(Pickup element, List<Pickup> container) {
        int count = 0;
        for (Pickup part : container) {
            if (element.equals(part)) {
                count++;
                if (count > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public void stopBackgroundMusic() {
        soundManager.stopBackgroundMusic();
    }
}
