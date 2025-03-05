package org.example.v3;

public class MapV3 {
    private final char[] grid;
    private final int width;
    private final int height;

    public MapV3(String mapString, int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = mapString.toCharArray();
        if (grid.length != width * height) {
            throw new IllegalArgumentException("Map string length does not match dimensions");
        }
    }

    public boolean isObstacle(int x, int y) {
        return isWithinBounds(x, y) && grid[y * width + x] == 'X';
    }

    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public void print() {
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                System.out.print(grid[y * width + x] + " ");
            }
            System.out.println();
        }
    }

    public void printWithRover(int roverX, int roverY) {
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