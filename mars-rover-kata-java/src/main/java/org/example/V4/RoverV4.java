package org.example.V4;

public class RoverV4 extends Explorer {
    private int x;
    private int y;
    private char direction;
    private final int mapWidth;
    private final int mapHeight;

    public RoverV4(MapV4 map, int x, int y, char direction, int mapWidth, int mapHeight) {
        super(map);
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    @Override
    boolean moveForward() {
        int newX = x, newY = y;
        switch (direction) {
            case 'N' -> newY = (newY + 1) % mapHeight;
            case 'E' -> newX = (newX + 1) % mapWidth;
            case 'S' -> newY = (newY - 1 + mapHeight) % mapHeight;
            case 'W' -> newX = (newX - 1 + mapWidth) % mapWidth;
        }
        if (!map.isObstacle(newX, newY)) {
            x = newX;
            y = newY;
            return true;
        }
        return false;
    }

    @Override
    void turnLeft() {
        direction = switch (direction) {
            case 'N' -> 'W';
            case 'W' -> 'S';
            case 'S' -> 'E';
            case 'E' -> 'N';
            default -> direction;
        };
    }

    @Override
    void turnRight() {
        direction = switch (direction) {
            case 'N' -> 'E';
            case 'E' -> 'S';
            case 'S' -> 'W';
            case 'W' -> 'N';
            default -> direction;
        };
    }

    @Override
    public String getPosition() {
        return x + ":" + y + ":" + direction;
    }

    @Override
    public void printMap() {
        System.out.println("\nMap with Rover (R) and obstacles (X):");
        map.print(x, y);
    }
}