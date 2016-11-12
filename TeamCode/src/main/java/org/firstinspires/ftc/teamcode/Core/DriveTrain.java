package org.firstinspires.ftc.teamcode.Core;

import static org.firstinspires.ftc.teamcode.Core.Robot.robot;

/**
 * Created by Hymowitz on 11/9/2016.
 */
public class DriveTrain implements SubSystem
{
    double
            //drive configs
            MAX_ACCEL=20.0/1000,
            TURN_ACCEL=10.0/1000,
            HIGH_POWER=1.0,
            HALF_POWER=0.4,
            LOW_POWER=0.2,
            TURN_SPD=0.5,
            HIGH_TURN_SPD_FACTOR=2.0,
            SMALL_TURN_SPD=0.25;

    private Motor147[][] motors;

    double power,turn;
    double lastPower=0,lastTurn=0;

    public DriveTrain(String[]left, String[]right)
    {
        motors=new Motor147[2][left.length];
        for(int i=0;i<left.length;i++)
        {
            motors[0][i]=new Motor147(left[i]);
            motors[1][i]=new Motor147(right[i]);
        }
    }

    public void setSpeedJoystick()
    {
        power= robot.controller.getPower();
        turn= robot.controller.getTurn()*TURN_SPD;

        if(robot.controller.highSpeed()) {
            power *= HIGH_POWER;
            turn *= HIGH_TURN_SPD_FACTOR;
            turn += robot.controller.getSmallTurn() * SMALL_TURN_SPD;
        }
        else if(robot.controller.lowSpeed()) {
            power *= LOW_POWER;
        }
        else {
            power *= HALF_POWER;
        }
        power= Utils.constrain(power,lastPower-MAX_ACCEL* robot.getMs(),lastPower+MAX_ACCEL* robot.getMs());
        turn= Utils.constrain(turn,lastTurn-TURN_ACCEL*robot.getMs(),lastTurn+TURN_ACCEL*robot.getMs());
        lastPower=power;
        lastTurn=turn;
        setSpeedRotation(power,turn);
    }

    public void setSpeedRotation(double speed, double rotation)
    {
        setTank(speed + rotation, speed - rotation);
    }

    public void setTank(double left, double right)
    {
        for (int i = 0; i < motors[0].length; i++) {
            motors[0][i].setSpeed(left);
            motors[1][i].setSpeed(-right);
        }
    }

    public void setTargetPosition(double t)
    {
        setTargetPosition(t,t);
    }
    public void setTargetPosition(double left,double right)
    {
        for (int i = 0; i < motors[0].length; i++) {
            motors[0][i].setTargetPosition(left);
            motors[1][i].setTargetPosition(-right);
        }
    }
    public void resetOffset()
    {
        for (int i = 0; i < motors.length; i++) {
            for(int j=0;j<motors[0].length;j++) {
                motors[i][j].resetOffset();
            }
        }
    }
    public double getError()
    {
        double error=0.0;
        for (int i = 0; i < motors.length; i++) {
            for(int j=0;j<motors[0].length;j++) {
                error+=motors[i][j].getError();
            }
        }
        return error/motors.length/motors[0].length;
    }

    public void update()
    {
        for (int i = 0; i < motors.length; i++) {
            for(int j=0;j<motors[0].length;j++) {
                motors[i][j].update();
            }
        }
    }
}
