package fr.ft.avajlauncher.weather;

import java.util.Random;
import fr.ft.avajlauncher.aircraft.Coordinates;

public class WeatherProvider {
    private final String[] weather = {"RAIN", "FOG", "SUN", "SNOW"};

    //Start of singleton
    private static WeatherProvider instance = null;
    private WeatherProvider(){};
    public static WeatherProvider getInstance(){
        if (instance == null)
            instance = new WeatherProvider();
        return instance;
    }
    //End of singleton

    public String getCurrentWeather(Coordinates p_coordinates){
        double value, rand_x, rand_y, rand_z;
        double x, y, z;
        int type;
        PerlinNoise noise = PerlinNoise.getInstance();
        Random rand = new Random();

        rand_x = rand.nextDouble(0.1, 0.9);
        rand_y = rand.nextDouble(0.1, 0.9);
        rand_z = rand.nextDouble(0.1, 0.9);
        x = p_coordinates.getLatitude() + rand_x;
        y = p_coordinates.getLongitude() + rand_y;
        z = p_coordinates.getHeight() + rand_z;
        value = noise.generate(x, y, z);
        if (value < -0.5)
            type = 0;
        else if (value < 0)
            type = 1;
        else if (value < 0.5)
            type = 2;
        else
            type = 3;
        return weather[type];
    }
}
