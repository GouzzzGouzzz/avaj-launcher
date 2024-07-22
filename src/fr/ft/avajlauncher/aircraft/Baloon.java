package fr.ft.avajlauncher.aircraft;

import fr.ft.avajlauncher.constant.WeatherType;
import fr.ft.avajlauncher.weather.WeatherMessage;

public class Baloon extends Aircraft{

    public Baloon(long p_id, String p_name, Coordinates p_coordinates){
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
            this.coordinates.increaseLongitude(2);
                this.coordinates.increaseHeight(4);
            case RAIN:
                this.coordinates.decreaseHeight(5);
                if (this.coordinates.getHeight() <= 0)
                    weatherTower.unregister(this);
            case FOG:
                this.coordinates.decreaseHeight(3);
                if (this.coordinates.getHeight() <= 0)
                    weatherTower.unregister(this);
            case SNOW:
                this.coordinates.decreaseHeight(15);
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
