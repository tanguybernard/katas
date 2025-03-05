package org.example.v1;

public class RoverV1 extends Movement {
    private int x;
    private int y;
    private char direction;

    public RoverV1(int x, int y, char direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    @Override
    void moveForward() {
        switch (direction) {
            case 'N' -> y++;
            case 'E' -> x++;
            case 'S' -> y--;
            case 'W' -> x--;
        }
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

    public String getPosition() {
        return x + ":" + y + ":" + direction;
    }
}