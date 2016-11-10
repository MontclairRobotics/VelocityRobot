package org.firstinspires.ftc.teamcode;

/**
 * Created by Hymowitz on 11/9/2016.
 */
public class DriveTrain
{
    private Motor147[][] motors;

    public DriveTrain(Motor147[] left, Motor147[] right)
    {
        motors = new Motor147[2][];
        motors[0] = left;
        motors[1] = right;
    }

    public void setSpeedRotation(double speed, double rotation)
    {
        setTank(speed + rotation, speed - rotation);
    }

    public void setTank(double left, double right)
    {
        for(int i = 0; i<motors[0].length; i++)
        {
            motors[0][i].setSpeed(left);
            motors[1][i].setSpeed(right);
        }
     }
}
