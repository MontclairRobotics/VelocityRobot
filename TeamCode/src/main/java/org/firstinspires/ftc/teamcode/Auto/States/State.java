package org.firstinspires.ftc.teamcode.auto.states;

/**
 * Created by MHS Robotics
 */

public interface State {
    public void start();
    public void stop();
    public void update();
    public boolean isDone();
}
