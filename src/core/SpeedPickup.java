package core;

import java.awt.*;

public class SpeedPickup extends Pickup {
    public SpeedPickup(int maxX, int maxY) {
        super(maxX, maxY);
    }

    public static Color getColor() {
        return Color.CYAN;
    }
}
