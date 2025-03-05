package org.example.V4;

public class MapV4 {
    private final char[] grid;
    private final int width;
    private final int height;

    public MapV4(String mapString, int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = mapString.toCharArray();
        if (grid.length != width * height) {
            throw new IllegalArgumentException("Map string length does not match dimensions");
        }
    }

    public boolean isObstacle(int x, int y) {
        int wrappedX = wrapCoordinate(x, width);
        int wrappedY = wrapCoordinate(y, height);
        return grid[wrappedY * width + wrappedX] == 'X';
    }

    private int wrapCoordinate(int coord, int max) {
        return (coord + max) % max;
    }

    public void print(int roverX, int roverY) {
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                if (x == roverX && y == roverY) {
                    System.out.print("R ");
                } else {
                    System.out.print(grid[y * width + x] + " ");
                }
            }
            System.out.println();
        }
    }
}