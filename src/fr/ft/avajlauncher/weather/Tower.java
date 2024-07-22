package fr.ft.avajlauncher.weather;

import java.util.List;
import java.util.ArrayList;
import fr.ft.avajlauncher.flyable.Flyable;

public class Tower {
    private List<Flyable> observers = new ArrayList<>();

    public void register(Flyable p_flyable){
        this.observers.add(p_flyable);
        System.out.println("Tower says: " + p_flyable.getClass().getSimpleName() + "#" + p_flyable.getName() + "(" + p_flyable.getId() + ") " + "registered to weather tower.");
    };

    public void unregister(Flyable p_flyable){
        this.observers.remove(p_flyable);
        System.out.println("Tower says: " + p_flyable.getClass().getSimpleName() + "#"+ p_flyable.getName() + "(" + p_flyable.getId() + ") " + "unregistered from weather tower.");
    };

    protected void conditionChanged(){
        List<Flyable> observersCopy = new ArrayList<>();
        for (int i = 0; i < observers.size(); i++){
            observersCopy.add(observers.get(i));
        }
        for (int i = 0; i < observersCopy.size(); i++){
            observersCopy.get(i).updateConditions();
        }
    };
}
