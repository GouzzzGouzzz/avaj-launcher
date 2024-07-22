package fr.ft.avajlauncher;

import fr.ft.avajlauncher.simulator.Simulator;

public class Main {
    public static void main(String[] args)
    {
        Simulator simu = new Simulator();
        simu.exec(args);
    }
}
