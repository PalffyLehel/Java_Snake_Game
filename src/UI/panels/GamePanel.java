package UI.panels;

import collection.Settings;
import collection.Tuple;
import core.Pickup;
import core.SpeedPickup;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel {
    private List<Tuple> playerPos;
    private List<Pickup> pickups;
    private List<Pickup> speedPickups;
    private boolean collided;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width;
        int height;
        int width_step;
        int height_step;

        width = getPreferredSize().width;
        height = getPreferredSize().height;

        width_step = width / 100 * 3;
        height_step = height / 100 * 3;

        for (int i = 1; i < width - width_step; i += width_step + 1) {
            for (int j = 1; j < height - height_step; j += height_step + 1) {
                g.fillRect(i + (getWidth() - width) / 2, j + (getHeight() - height) / 2, width_step, height_step);
            }
        }

        g.setColor(Pickup.getColor());
        pickups
                .stream()
                .forEach(p -> g.fillRect(
                        1 + p.getX() + p.getX() * width_step + (getWidth() - width) / 2,
                        1 + p.getY() + p.getY() * height_step + (getHeight() - height) / 2,
                        width_step, height_step));

        g.setColor(SpeedPickup.getColor());
        speedPickups
                .stream()
                .forEach(p -> g.fillRect(
                        1 + p.getX() + p.getX() * width_step + (getWidth() - width) / 2,
                        1 + p.getY() + p.getY() * height_step + (getHeight() - height) / 2,
                        width_step, height_step));

        g.setColor(Color.BLUE);
        if (collided) {
            g.setColor(Color.RED);
        }
        playerPos
                .stream()
                .forEach(p -> g.fillRect(
                        1 + p.getX() + p.getX() * width_step + (getWidth() - width) / 2,
                        1 + p.getY() + p.getY() * height_step + (getHeight() - height) / 2,
                        width_step, height_step));
    }

    public void setPlayer(List<Tuple> positions) {
        playerPos = positions;
    }
    public void setRegularPickups(List<Pickup> pickups) { this.pickups = pickups; }
    public void setSpeedPickups(List<Pickup> pickups) { speedPickups = pickups; }

    public void resizePanel() {
        setSize(new Dimension(Settings.WIDTH, Settings.HEIGHT));
        setPreferredSize(new Dimension(Settings.WIDTH, Settings.HEIGHT));
        setMinimumSize(new Dimension(Settings.WIDTH, Settings.HEIGHT));
        setMaximumSize(new Dimension(Settings.WIDTH, Settings.HEIGHT));
    }

    public void setCollided(boolean collided) {
        this.collided = collided;
    }
}
