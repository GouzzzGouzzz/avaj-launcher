package fr.ft.avajlauncher.simulator;

import java.io.File;
import java.util.Scanner;

import fr.ft.avajlauncher.aircraft.AircraftFactory;
import fr.ft.avajlauncher.aircraft.CoordinatesBuilder;
import fr.ft.avajlauncher.constant.AircraftType;
import fr.ft.avajlauncher.flyable.Flyable;
import fr.ft.avajlauncher.weather.WeatherTower;

public class Simulator {

    private static boolean check_file(String[] args){
        if (args.length == 0){
            System.out.println("Need atleast one args !");
            return false;
        }
        if (!args[0].endsWith(".txt")){
            System.out.println("Invalid file extension !");
            return false;
        }
        File file = new File("../" + args[0]);
        if (!file.exists() || !file.canRead()){
            System.out.println("Couldn't read or open this file !");
            return false;
        }
        Scanner readfile = null;
        try {
            readfile = new Scanner(file);
        } catch (Exception FileNotFoundException) {
            readfile.close();
            System.out.println("You won't see this error otherwise the previous code don't works ...");
            return false;
        }
        readfile.close();
        return true;
    }

    public void exec(String[] args){
        if (check_file(args) == false)
            return ;

        File file = new File("../" + args[0]);
        Scanner readfile = null;
        int weatherChangeNb, lineNumber;
        String buffer;
        String[] splitLine;
        int[] coords = new int[3];
        AircraftFactory ACFactory = AircraftFactory.getInstance();
        CoordinatesBuilder CoordBuilder = CoordinatesBuilder.getInstance();
        WeatherTower w_Tower = new WeatherTower();
        Flyable newFlyable;

        try {
            readfile = new Scanner(file);
        } catch (Exception FileNotFoundException) {
            readfile.close();
            System.out.println("You won't see this error otherwise the previous code don't works ...");
            return ;
        }
        if (readfile.hasNextLine()){
            try {
                weatherChangeNb = readfile.nextInt();
                if (weatherChangeNb <= 0){
                    System.out.println("Invalid number of weather changes, should be higher than 0 !");
                    readfile.close();
                    return;
                }
            } catch (Exception e) {
                readfile.close();
                System.out.println("Out of range number of weather changes ! or not an int ?");
                return ;
            }
        }
        readfile.nextLine();

        lineNumber = 1;
        while (readfile.hasNextLine()) {
            //now parse each line that should be like this: TYPE NAME LONGITUDE LATITUDE HEIGHT.
            buffer = readfile.nextLine();
            splitLine = buffer.split(" ");
            if (splitLine.length != 5){
                System.out.println("Invalid number of args, line number :" + lineNumber);
                readfile.close();
                return ;
            }
            try {
                AircraftType.valueOf(splitLine[0].toUpperCase());
            } catch (IllegalArgumentException  e) {
                System.out.println("Invalid type of Aircraft, line number :" + lineNumber);
                readfile.close();
                return ;
            }
            try {
                if (Integer.valueOf(splitLine[4]) < 0){
                    System.out.println("Invalid height value should be positive line number :" + lineNumber);
                    readfile.close();
                    return ;
                }
            } catch (Exception e) {
                System.out.println("Invalid coordinates, line number :" + lineNumber);
                readfile.close();
                return ;
            }
            coords[0] = Integer.valueOf(splitLine[2]);
            coords[1] = Integer.valueOf(splitLine[3]);
            coords[2] = Integer.valueOf(splitLine[4]);
            newFlyable = ACFactory.newAircraft(splitLine[0], splitLine[1], CoordBuilder.build(coords[0],coords[1],coords[2]));
            w_Tower.register(newFlyable);
            lineNumber++;
        }
        readfile.close();
    }
}

