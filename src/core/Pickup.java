package core;

import java.awt.*;
import java.util.Random;

public class Pickup {
    private final Random rng;

    private final int maxX;
    private final int maxY;
    private int x;
    private int y;

    public Pickup(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;

        rng = new Random();

        x = rng.nextInt(maxX);
        y = rng.nextInt(maxY);
    }

    public void reposition() {
        x = rng.nextInt(maxX);
        y = rng.nextInt(maxY);
    }

    public static Color getColor() {
        return Color.yellow;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
