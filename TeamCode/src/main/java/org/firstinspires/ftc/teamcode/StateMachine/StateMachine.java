package org.firstinspires.ftc.teamcode.StateMachine;

/**
 * Created by Hymowitz on 11/9/2016.
 */
public class StateMachine implements State{
    private State[] states;
    private int state;

    public StateMachine(State... states)
    {
        this.states=states;
    }

    public void start()
    {
        state=0;
    }

    public void update() {

    }

    public void stop() {

    }

    public boolean isDone() {
        return false;
    }
}
