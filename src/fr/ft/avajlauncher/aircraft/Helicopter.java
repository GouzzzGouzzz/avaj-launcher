package fr.ft.avajlauncher.aircraft;

import fr.ft.avajlauncher.constant.WeatherType;
import fr.ft.avajlauncher.weather.WeatherMessage;

public class Helicopter extends Aircraft{

    public  Helicopter(long p_id, String p_name, Coordinates p_coordinates){
        super(p_id, p_name, p_coordinates);
    }

    public void updateConditions(){
        String weather = weatherTower.getWeather(this.coordinates);
        WeatherType type;
        boolean output = false;
        try {
            type = WeatherType.valueOf(weather.toUpperCase());
        } catch (IllegalArgumentException  e) {
            return ;
        }
        if (!this.prevWeather.equals(type.toString())){
            output = true;
        }
        switch (type) {
            case SUN:
                this.coordinates.increaseLongitude(10);
                this.coordinates.increaseHeight(2);
            case RAIN:
                this.coordinates.increaseLongitude(5);
            case FOG:
                this.coordinates.increaseLongitude(1);
            case SNOW:
                this.coordinates.decreaseHeight(12);
                if (this.coordinates.getHeight() <= 0)
                    weatherTower.unregister(this);
            default:
                break;
        }
        if (output){
            String msg = WeatherMessage.getRandomMessage(type);
            System.out.println(this.getClass().getSimpleName() + "#" + this.getName() + "(" + this.getId() + "): " + msg);
        }
    }
}
