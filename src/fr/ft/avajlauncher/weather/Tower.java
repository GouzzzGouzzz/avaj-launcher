package fr.ft.avajlauncher.weather;

import java.util.List;
import java.util.ArrayList;
import fr.ft.avajlauncher.flyable.Flyable;

public class Tower {
    private List<Flyable> observers = new ArrayList<>();

    public void register(Flyable p_flyable){
        observers.add(p_flyable);
    };

    public void unregister(Flyable p_flyable){
        observers.remove(p_flyable);
    };

    protected void conditionChanged(){
        return;
    };
}
