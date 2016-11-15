package org.firstinspires.ftc.teamcode.Core;

import java.util.ArrayList;

/**
 * Created by rafibaum on 14/11/16.
 */
public class Updater {

    //list of updatable objects
    private static ArrayList<Updatable> updatables = new ArrayList<>();

    //Method to add an updatable (called obj) to updatables list
    public static void addUpdatable(Updatable obj) {
        updatables.add(obj);
    }

    //Method to remove an updatable (called obj) from updatables list
    public static void removeUpdatable(Updatable obj) {
        updatables.remove(obj);
    }

    public static void update() {
        //Goes through every updatable in list and runs update() on them
        for(Updatable obj : updatables) {
            obj.update();
        }
    }

    //Removing every object from updatables list
    public static void reset() {
        //Starts from the end of the list
        for(int i = updatables.size()-1; i >= 0; i--) {
            updatables.remove(i);
        }
    }

}
