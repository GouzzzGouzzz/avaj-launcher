package fr.ft.avajlauncher.weather;

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
        double value;
        int type;
        PerlinNoise noise = PerlinNoise.getInstance();

        value = noise.generate(p_coordinates.getLatitude(), p_coordinates.getLongitude(), p_coordinates.getHeight());
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
