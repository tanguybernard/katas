package org.example.v2;


abstract class Movement {
    protected Map map;

    public Movement(Map map) {
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
                    if (!moveForward()) {
                        result.append("#STOP#");
                    }
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
}