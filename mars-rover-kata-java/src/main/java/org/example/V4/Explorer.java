package org.example.V4;

abstract class Explorer {
    protected MapV4 map;

    public Explorer(MapV4 map) {
        this.map = map;
    }

    abstract boolean moveForward();
    abstract void turnLeft();
    abstract void turnRight();

    public String executeCommands(String commands) {
        StringBuilder result = new StringBuilder();
        for (char c : commands.toCharArray()) {
            switch (c) {
                case 'F':
                    moveForward();
                    break;
                case 'L':
                    turnLeft();
                    break;
                case 'R':
                    turnRight();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid command: " + c);
            }
            result.append(getPosition()).append(",");
        }
        return result.toString();
    }

    abstract String getPosition();
    abstract void printMap();
}