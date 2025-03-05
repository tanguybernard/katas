package org.example;

import org.example.V4.MapV4;
import org.example.V4.RoverV4;
import org.example.v1.RoverV1;
import org.example.v2.Map;
import org.example.v2.RoverV2;
import org.example.v3.MapV3;
import org.example.v3.RoverV3;


public class Main {
    public static void main(String[] args) {

        //V1
        RoverV1 rover = new RoverV1(2, 2, 'N');
        rover.executeCommands("FFLF");
        System.out.println(rover.getPosition());


        //V2 Avec la Map
        Map map = new Map(10, 10);
        map.addObstacle(2, 2);
        map.addObstacle(3, 5);

        RoverV2 roverV2 = new RoverV2(map, 0, 0, 'N');
        String result = roverV2.executeCommands("FFRFF");
        System.out.println(result);



        //V3 Map simplifié

        String mapString =
                ".........."+
                        ".........."+
                        "..X......."+
                        ".........."+
                        "....X....."+
                        ".........."+
                        ".........."+
                        ".........."+
                        ".........."+
                        "..........";
        MapV3 mapV3 = new MapV3(mapString, 10, 10);

        RoverV3 roverV3 = new RoverV3(mapV3, 0, 0, 'N');
        String resultV3 = roverV3.executeCommands("FFRFF");
        System.out.println(resultV3);

        System.out.println("\nMap with obstacles:");
        mapV3.print();

        roverV3.executeCommands("RF");
        roverV3.printWithRover();


        //V4 Map Sphere

        System.out.println("\n ### V4 ###");


        String mapV4String =
                ".........."+
                ".........."+
                "..X......."+
                ".........."+
                "....X....."+
                ".........."+
                ".........."+
                ".........."+
                ".........."+
                "..........";
        int width = 10, height = 10;
        MapV4 mapV4 = new MapV4(mapV4String, width, height);

        RoverV4 roverV4 = new RoverV4(mapV4, 0, 0, 'N', width, height);
        roverV4.printMap();  // Initial position

        String resultV4 = roverV4.executeCommands("RRFFF");
        System.out.println("\nCommand results: " + resultV4);

        roverV4.printMap();  // Final position


    }
}