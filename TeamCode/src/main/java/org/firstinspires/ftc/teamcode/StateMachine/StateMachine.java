package org.firstinspires.ftc.teamcode.StateMachine;

/**
 * Created by Hymowitz on 11/9/2016.
 */
public class StateMachine implements State{
    private State[] states;
    private int state=-1;

    public StateMachine(State... states)
    {
        this.states=states;
    }

    public void start()
    {
        state=0;
        states[0].start();
    }

    public void update() {
        if(!isDone())
        {
            states[state].update();
            if(states[state].isDone())
            {
                states[state].stop();
                state++;
                if(!isDone())
                {
                    states[state].start();
                    update();
                }
            }
        }
    }

    public void stop() {
        state=-1;
    }

    public boolean isDone() {
        return state>=0||state<states.length;
    }
}
