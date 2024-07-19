package fr.ft.avajlauncher.aircraft;

import fr.ft.avajlauncher.flyable.Flyable;

public class Aircraft extends Flyable{
    protected long id;
    protected String name;
    protected Coordinates coordinates;

    protected Aircraft(long p_id, String p_name, Coordinates p_coordinates){
        id = p_id;
        name = p_name;
        coordinates = p_coordinates;
    }
    @Override
    public String getName(){
        return name;
    }
    @Override
    public long getId(){
        return id;
    }

    @Override
    public void updateConditions() {
        return ;
    }
}
