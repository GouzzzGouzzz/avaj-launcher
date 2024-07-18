package fr.ft.avajlauncher.aircraft;

import fr.ft.avajlauncher.flyable.Flyable;
import fr.ft.avajlauncher.constant.AircraftType;

public class AircraftFactory {

    //Start of singleton
    private static AircraftFactory instance = null;

    private AircraftFactory(){};

    public static AircraftFactory getInstance() {
        if (instance == null) {
            instance = new AircraftFactory();
        }
        return instance;
    }
    //End of singleton

    public Flyable newAircraft(String p_type, String p_name, Coordinates p_coordinates){
        AircraftType type;
        try {
            type = AircraftType.valueOf(p_type.toUpperCase());
        } catch (IllegalArgumentException  e) {
            return null;
        }
        switch (type) {
            case HELICOPTER:
                return new Helicopter(1, p_name, p_coordinates);
            case JETPLANE:
                return new JetPlane(1, p_name, p_coordinates);
            case BALOON:
                return new Baloon(1, p_name, p_coordinates);
            default:
                break;
        }
        return null;
    }
}
