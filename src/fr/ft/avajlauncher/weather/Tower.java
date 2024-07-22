package fr.ft.avajlauncher.weather;

import java.util.List;
import java.util.ArrayList;
import fr.ft.avajlauncher.flyable.Flyable;

public class Tower {
    private List<Flyable> observers = new ArrayList<>();

    public void register(Flyable p_flyable){
        observers.add(p_flyable);
        System.out.println("Tower says: " + p_flyable.getClass().getSimpleName() + "#" + p_flyable.getName() + "(" + p_flyable.getId() + ") " + "registered to weather tower.");
    };

    public void unregister(Flyable p_flyable){
        observers.remove(p_flyable);
        System.out.println("Tower says: " + p_flyable.getClass().getSimpleName() + "#"+ p_flyable.getName() + "(" + p_flyable.getId() + ") " + "unregistered from weather tower.");
    };

    protected void conditionChanged(){
        return;
    };
}
