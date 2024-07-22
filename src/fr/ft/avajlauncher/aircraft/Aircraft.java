package fr.ft.avajlauncher.aircraft;

import fr.ft.avajlauncher.flyable.Flyable;
import fr.ft.avajlauncher.weather.WeatherProvider;

public class Aircraft extends Flyable{
    protected long id;
    protected String name;
    protected Coordinates coordinates;
    protected String prevWeather;

    protected Aircraft(long p_id, String p_name, Coordinates p_coordinates){
        this.id = p_id;
        this.name = p_name;
        this.coordinates = p_coordinates;
        this.prevWeather = WeatherProvider.getInstance().getCurrentWeather(p_coordinates);
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public long getId(){
        return this.id;
    }

    @Override
    public void updateConditions() {
        return ;
    }
}
