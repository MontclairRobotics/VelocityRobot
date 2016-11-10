package org.firstinspires.ftc.teamcode.StateMachine;

/**
 * Created by Hymowitz on 11/9/2016.
 */
public interface State {
    public void start();
    public void update();
    public void stop();
    public boolean isDone();
}
