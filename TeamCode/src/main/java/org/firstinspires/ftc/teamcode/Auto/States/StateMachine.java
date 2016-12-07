package org.firstinspires.ftc.teamcode.Auto.States;

/**
 * Created by MHS Robotics
 */

public class StateMachine implements State{

    private int i;
    private State[] states;

    public StateMachine(State... s) {
        states = s;
    }

    public void start() {
        i=0;
        states[i].start();
    }
    public void stop() {
        i=-1;
    }
    public void update() {
        while(!isDone())
        {
            states[i].update();
            if(states[i].isDone())
            {
                states[i].stop();
                i++;
                if(!isDone())
                {
                    states[i].start();
                }
            }
        }
    }
    public boolean isDone() {
        return i>=0&&i<states.length;
    }
}
