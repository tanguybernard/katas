package org.example.v3;

public class RoverV3 extends Movement {
    private int x;
    private int y;
    private char direction;

    public RoverV3(MapV3 map, int x, int y, char direction) {
        super(map);
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    @Override
    boolean moveForward() {
        int newX = x, newY = y;
        switch (direction) {
            case 'N' -> newY++;
            case 'E' -> newX++;
            case 'S' -> newY--;
            case 'W' -> newX--;
        }
        if (map.isWithinBounds(newX, newY) && !map.isObstacle(newX, newY)) {
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

    public void printWithRover(){
        System.out.println("\nMap with Rover (R) and obstacles (X):");
        map.printWithRover(x, y);
    }
}