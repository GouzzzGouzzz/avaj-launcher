package fr.ft.avajlauncher.flyable;

import fr.ft.avajlauncher.weather.WeatherTower;

public abstract class Flyable {
    protected WeatherTower weatherTower = null;

    public abstract String getName();
    public abstract long getId();

    public abstract void updateConditions();

    public void registerTower(WeatherTower p_tower){
        this.weatherTower = p_tower;
    };

}
