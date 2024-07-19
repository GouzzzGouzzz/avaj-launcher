package fr.ft.avajlauncher.aircraft;


public class CoordinatesBuilder {
    private static CoordinatesBuilder instance = null;
    private CoordinatesBuilder(){};

    public static CoordinatesBuilder getInstance() {
        if (instance == null) {
            instance = new CoordinatesBuilder();
        }
        return instance;
    }

    public Coordinates build(int p_longitude, int p_latitude, int p_height){
        return new Coordinates(p_longitude, p_latitude, p_height);
    }
}
