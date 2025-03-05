package org.example.v1;

abstract class Movement {
    abstract void moveForward();
    abstract void turnLeft();
    abstract void turnRight();

    public void executeCommands(String commands) {
        for (char c : commands.toCharArray()) {
            switch (c) {
                case 'F' -> moveForward();
                case 'L' -> turnLeft();
                case 'R' -> turnRight();
                default -> throw new IllegalArgumentException("Invalid command: " + c);
            }
        }
    }
}
