package fr.ft.avajlauncher.aircraft;

public class Coordinates {

    private int longitude;
    private int latitude;
    private int height;

    Coordinates(int p_longitude, int p_latitude, int p_height){
        this.longitude = p_longitude;
        this.latitude = p_latitude;
        this.height = p_height;
    }

    public int getLongitude(){
        return this.longitude;
    }
    public int getLatitude(){
        return this.latitude;
    }
    public int getHeight(){
        return this.height;
    }

    public void increaseLongitude(int value){
        this.longitude += value;
    }
    public void increaseLatitude(int value){
        this.latitude += value;
    }
    public void increaseHeight(int value){
        this.height += value;
        if (this.height > 100)
            this.height = 100;
    }

    public void decreaseLongitude(int value){
        this.longitude -= value;
    }
    public void decreaseLatitude(int value){
        this.latitude -= value;
    }
    public void decreaseHeight(int value){
        this.height -= value;
    }
}
