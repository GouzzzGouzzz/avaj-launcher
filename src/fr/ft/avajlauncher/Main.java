package fr.ft.avajlauncher;

import fr.ft.avajlauncher.aircraft.CoordinatesBuilder;
import fr.ft.avajlauncher.simulator.Simulator;
import fr.ft.avajlauncher.weather.PerlinNoise;
import fr.ft.avajlauncher.weather.WeatherProvider;

public class Main {
    public static void main(String[] args)
    {
        Simulator simu = new Simulator();
        simu.exec(args);
    }
}
