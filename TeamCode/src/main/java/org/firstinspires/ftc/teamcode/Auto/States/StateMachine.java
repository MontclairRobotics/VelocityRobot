package org.firstinspires.ftc.teamcode.Auto.States;

/**
 * Created by MHS Robotics
 */

public class StateMachine implements State{

    private int i;
    private State[] states;

    public StateMachine() {
    }

    public StateMachine(State... s)
    {
        init(s);
    }

    public void init(State... s)
    {
        this.states=s;
        i=-1;
    }

    public void start() {
        i=0;
        if(states.length>0)
            states[0].start();
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
