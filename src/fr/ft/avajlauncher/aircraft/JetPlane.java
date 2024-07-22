package fr.ft.avajlauncher.aircraft;

import fr.ft.avajlauncher.constant.WeatherType;

public class JetPlane extends Aircraft{

    public JetPlane(long p_id, String p_name, Coordinates p_coordinates){
        super(p_id, p_name, p_coordinates);
    }

    public void updateConditions(){
        String weather = weatherTower.getWeather(this.coordinates);
        WeatherType type;
        try {
            type = WeatherType.valueOf(weather.toUpperCase());
        } catch (IllegalArgumentException  e) {
            return ;
        }
        switch (type) {
            case SUN:
                this.coordinates.increaseLatitude(10);
                this.coordinates.increaseHeight(2);
            case RAIN:
                this.coordinates.increaseLatitude(5);
            case FOG:
                this.coordinates.increaseLatitude(1);
            case SNOW:
                this.coordinates.decreaseHeight(7);
                if (this.coordinates.getHeight() <= 0)
                    weatherTower.unregister(this);
            default:
                break;
        }
        if (!this.prevWeather.equals(type.toString())){
            System.out.println("Weather change !");
        }
    }
}
