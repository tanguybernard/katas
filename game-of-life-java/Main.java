package com.example.sample_java;

public class Main {

    public static void main(String[] args) {

        Game game = new Game(5,5);
        game.init();

        for(int gen=1; gen<=10; gen++) {
            System.out.println("Generation: " + gen);
            game.display();
            game.update();
        }
    }

}
