package fr.ft.avajlauncher.weather;

import fr.ft.avajlauncher.aircraft.Coordinates;

public class WeatherTower extends Tower {

    public String getWeather(Coordinates p_coordinates)
    {
        return WeatherProvider.getInstance().getCurrentWeather(p_coordinates);
    }

    public void changeWeather()
    {
        PerlinNoise.getInstance().changeSeed();
        this.conditionChanged();
        return ;
    }
}
