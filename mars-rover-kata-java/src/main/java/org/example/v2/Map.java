package org.example.v2;

import java.util.HashSet;
import java.util.Set;

public class Map {
    private final int width;
    private final int height;
    private final Set<Position> obstacles;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.obstacles = new HashSet<>();
    }

    public void addObstacle(int x, int y) {
        obstacles.add(new Position(x, y));
    }

    public boolean isObstacle(int x, int y) {
        return obstacles.contains(new Position(x, y));
    }

    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
