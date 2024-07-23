package fr.ft.avajlauncher.simulator;

import java.io.File;
import java.io.PrintStream;
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
            System.err.println("Need atleast one args !");
            return false;
        }
        if (!args[0].endsWith(".txt")){
            System.err.println("Invalid file extension !");
            return false;
        }
        File file = new File("../" + args[0]);
        if (!file.exists() || !file.canRead()){
            System.err.println("Couldn't read or open this file !");
            return false;
        }
        Scanner readfile = null;
        try {
            readfile = new Scanner(file);
        } catch (Exception FileNotFoundException) {
            readfile.close();
            System.err.println("You won't see this error otherwise the previous code don't works ...");
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
            System.err.println("You won't see this error otherwise the previous code don't works ...");
            return null;
        }
        if (readfile.hasNextLine()){
            try {
                this.weatherChangeNb = readfile.nextInt();
                if (this.weatherChangeNb <= 0){
                    System.err.println("Invalid number of weather changes, should be higher than 0 !");
                    readfile.close();
                    return null;
                }
            } catch (Exception e) {
                readfile.close();
                System.err.println("Out of range number of weather changes ! or not an int ?");
                return null;
            }
        }
        readfile.nextLine();
        return readfile;
    }

    private boolean parseAndCreate(Scanner readfile){
        int lineNumber;
        String[] splitLine;
        int[] coords = new int[3];
        Flyable newFlyable;

        lineNumber = 2;
        while (readfile.hasNextLine()) {
            splitLine = readfile.nextLine().split(" ");
            if (splitLine.length != 5){
                System.err.println("Invalid number of args, line number :" + lineNumber);
                readfile.close();
                return false;
            }
            try {
                AircraftType.valueOf(splitLine[0].toUpperCase());
            } catch (IllegalArgumentException  e) {
                System.err.println("Invalid type of Aircraft, line number :" + lineNumber);
                readfile.close();
                return false;
            }
            try {
                if (Integer.valueOf(splitLine[4]) < 0 || Integer.valueOf(splitLine[4]) > 100 || Integer.valueOf(splitLine[2]) < 0 || Integer.valueOf(splitLine[3]) < 0){
                    System.err.println("Invalid coordinates value should be positive, line number :" + lineNumber);
					readfile.close();
                    return false;
                }
            } catch (Exception e) {
                readfile.close();
                return false;
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
		return true;
    }

    public void exec(String[] args){
        Scanner readfile = null;
        PrintStream fileStream = null;
        File outputFile;
        PrintStream out;

        outputFile = new File("../simulation.txt");
        out = System.out;

        if (!outputFile.canWrite() && outputFile.exists()){
            System.out.println("Can't write on the output file !");
            return ;
        }
        if (check_file(args) == false)
            return ;
        readfile = initFile(args[0]);
        if (readfile == null)
            return ;
        try {
            fileStream = new PrintStream(outputFile);
        } catch (Exception e) {
            fileStream.close();
            return ;
        }
        //Redirecting the output of "out" to the simulation.txt file
        System.setOut(fileStream);
        if (!parseAndCreate(readfile))
			return ;
        for (int i = 0; i < this.weatherChangeNb; i++){
            this.w_Tower.changeWeather();
        }
        //Close the fileStream and set out back to the default value
        fileStream.close();
        System.setOut(out);
    }
}

