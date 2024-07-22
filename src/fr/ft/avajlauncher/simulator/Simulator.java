package fr.ft.avajlauncher.simulator;

import java.io.File;
import java.util.Scanner;

import fr.ft.avajlauncher.aircraft.AircraftFactory;
import fr.ft.avajlauncher.aircraft.CoordinatesBuilder;
import fr.ft.avajlauncher.constant.AircraftType;
import fr.ft.avajlauncher.flyable.Flyable;
import fr.ft.avajlauncher.weather.WeatherTower;

public class Simulator {
    private int weatherChangeNb = 0;
    AircraftFactory ACFactory = AircraftFactory.getInstance();
    CoordinatesBuilder CoordBuilder = CoordinatesBuilder.getInstance();
    WeatherTower w_Tower = new WeatherTower();

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

    private Scanner initFile(String filename){
        File file = new File("../" + filename);
        Scanner readfile = null;

        try {
            readfile = new Scanner(file);
        } catch (Exception FileNotFoundException) {
            readfile.close();
            System.out.println("You won't see this error otherwise the previous code don't works ...");
            return null;
        }
        if (readfile.hasNextLine()){
            try {
                this.weatherChangeNb = readfile.nextInt();
                if (this.weatherChangeNb <= 0){
                    System.out.println("Invalid number of weather changes, should be higher than 0 !");
                    readfile.close();
                    return null;
                }
            } catch (Exception e) {
                readfile.close();
                System.out.println("Out of range number of weather changes ! or not an int ?");
                return null;
            }
        }
        readfile.nextLine();
        return readfile;
    }

    private void parseAndCreate(Scanner readfile){
        int lineNumber;
        String[] splitLine;
        int[] coords = new int[3];
        Flyable newFlyable;

        lineNumber = 1;
        while (readfile.hasNextLine()) {
            //now parse each line that should be like this: TYPE NAME LONGITUDE LATITUDE HEIGHT.
            splitLine = readfile.nextLine().split(" ");
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
            newFlyable = this.ACFactory.newAircraft(splitLine[0], splitLine[1], this.CoordBuilder.build(coords[0],coords[1],coords[2]));
            newFlyable.registerTower(this.w_Tower);
            this.w_Tower.register(newFlyable);
            lineNumber++;
        }
        readfile.close();
    }

    public void exec(String[] args){
        Scanner readfile = null;
        if (check_file(args) == false)
            return ;
        readfile = initFile(args[0]);
        if (readfile == null)
            return ;
        parseAndCreate(readfile);
        for (int i = 0; i < this.weatherChangeNb; i++){
            this.w_Tower.changeWeather();
        }
    }
}

