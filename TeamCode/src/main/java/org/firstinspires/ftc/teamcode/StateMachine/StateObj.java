package org.firstinspires.ftc.teamcode.StateMachine;

/**
 * Created by Hymowitz on 11/9/2016.
 */
public abstract class StateObj implements State {
    public void start(){}
    public void update(){}
    public void stop(){}
    public abstract boolean isDone();
}
