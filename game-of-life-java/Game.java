package com.example.sample_java;

public class Game {

    private final int rows;
    private final int cols;
    private boolean[][] grid;
    private boolean[][] nextGenerations;

    public Game(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid =new boolean[rows][cols];
        this.nextGenerations = new boolean[rows][cols];
    }

    public void init() {
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                grid[row][col] = Math.random() < 0.5;
            }
        }
    }

    void update() {
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                int liveNeighbours = countLiveNeighbours(row, col);
                //Je suis vivant, pour le moment...
                if(grid[row][col]) { //Vivant
                    //Pas assez de voisins
                    if(liveNeighbours <2 || liveNeighbours > 3) {
                        nextGenerations[row][col] = false;
                    }
                    else  {
                        nextGenerations[row][col] = true;
                    }
                }
                //Pas encore en vie, peut etre soon ?
                else {
                    if(liveNeighbours == 3 || liveNeighbours == 2 ) {
                        nextGenerations[row][col] = true;
                    }
                    else {
                        nextGenerations[row][col] = false; //reste dead
                    }

                }
            }
        }
        //update grid
        for (int i = 0; i < rows; i++) {
            System.arraycopy(nextGenerations[i], 0, grid[i], 0, cols);
        }
    }

    private int countLiveNeighbours(int row, int col) {
        int count = 0;

        /*
        if(this.grid[row][col+1]){
            count++;
        }
        if(this.grid[row][col-1]){
            count++;
        }

         */

        // Vérifier les voisins de la cellule (v, h)
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // Ne pas inclure la cellule elle-même
                if (i == 0 && j == 0) continue;

                int neighborV = row + i;
                int neighborH = col + j;

                // Vérifier si le voisin est dans les limites de la grille
                if (neighborV >= 0 && neighborV < rows && neighborH >= 0 && neighborH < cols) {
                    if (grid[neighborV][neighborH]) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public void display() {
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                System.out.print(grid[row][col] ? 'X' : ' ');
            }
            System.out.println();
        }
        System.out.println();
    }
}
