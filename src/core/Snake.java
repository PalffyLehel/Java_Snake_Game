package core;

import collection.Direction;
import collection.Tuple;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Snake {
    private final LinkedList<Tuple> parts;
    private Tuple lastPart;
    private Direction lastDirection;

    public Snake() {
        parts = new LinkedList<>();
    }

    public Snake(Tuple part) {
        this();
        parts.add(part);
    }

    public Snake(LinkedList<Tuple> parts) {
        this.parts = parts;
    }

    public void addPart(Tuple part) {
        parts.addLast(part);
    }

    public void moveUp() {
        Tuple head = parts.get(0);
        parts.addFirst(new Tuple(head.getX(), head.getY() - 1));
        lastPart = parts.getLast();
        parts.removeLast();
    }

    public void moveDown() {
        Tuple head = parts.get(0);
        parts.addFirst(new Tuple(head.getX(), head.getY() + 1));
        lastPart = parts.getLast();
        parts.removeLast();
    }

    public void moveLeft() {
        Tuple head = parts.get(0);
        parts.addFirst(new Tuple(head.getX() - 1, head.getY()));
        lastPart = parts.getLast();
        parts.removeLast();
    }
    public void moveRight() {
        Tuple head = parts.get(0);
        parts.addFirst(new Tuple(head.getX() + 1, head.getY()));
        lastPart = parts.getLast();
        parts.removeLast();
    }

    public void increase() {
        addPart(lastPart);
    }

    public List<Tuple> getPos() {
        return new ArrayList<>(parts);
    }

    public Tuple getFirst() {
        return parts.getFirst();
    }

    public boolean isColliding() {
        Tuple head = parts.getFirst();

        return parts
                .stream()
                .filter(p -> p.equals(head))
                .count() > 1;
    }

    public Direction getLastDirection() {
        return  lastDirection;
    }

    public void setLastDirection(Direction direction) {
        lastDirection = direction;
    }
}
