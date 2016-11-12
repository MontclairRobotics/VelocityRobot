package org.firstinspires.ftc.teamcode.Auto;

import org.firstinspires.ftc.teamcode.Core.OpMode147;
import org.firstinspires.ftc.teamcode.StateMachine.State;
import org.firstinspires.ftc.teamcode.StateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.StateMachine.StateObj;

import static org.firstinspires.ftc.teamcode.Core.Robot.robot;

/**
 * Created by MONTCLAIR ROBOTICS on 11/12/2016.
 */

public class AutoOpMode extends OpMode147 {
    StateMachine stateMachine;


    public static final double TICKS_PER_INCH=10000.0/85;
    public static final double TOLERANCE=0.5*TICKS_PER_INCH;
    public static final double ROBOT_WIDTH=18;//center of wheel to center of wheel

    public AutoOpMode(State... states) {
        this.stateMachine = new StateMachine(states);
    }

    @Override
    public void start() {
        stateMachine.start();
    }

    @Override
    public void update()
    {
        stateMachine.update();
    }
    @Override
    public void stop()
    {
        stateMachine.stop();
    }





    //================================AUTO STATES=======================================
    public static class Drive extends StateObj
    {
        private double target;
        public Drive(double d)
        {
            target=d*TICKS_PER_INCH;
        }
        public void start()
        {
            robot.driveTrain.resetOffset();
        }
        public void update()
        {
            robot.driveTrain.setTargetPosition(target);
        }
        public boolean isDone()
        {
            return robot.driveTrain.getError()<TOLERANCE;
        }
    }

    public static class Turn extends StateObj
    {
        private double target;
        public Turn(double t)
        {
            this.target=(t/360.0)*(ROBOT_WIDTH*Math.PI)*TICKS_PER_INCH;
        }
        public void start()
        {
            robot.driveTrain.resetOffset();
        }
        public void update()
        {
            robot.driveTrain.setTargetPosition(target,-target);
        }
        public boolean isDone()
        {
            return robot.driveTrain.getError()<TOLERANCE;
        }
    }
    public static class Shoot extends StateMachine {

        public Shoot() {
            super(
                new StateObj() {
                    public void update() {
                        robot.shooter.setPositionButton(true);
                    }
                    public boolean isDone() {
                        return robot.shooter.getMotor().getError() < 100;
                    }
                },
                new StateObj() {
                    public void update() {
                        robot.shooter.setPositionButton(false);
                    }
                    public boolean isDone() {
                        return robot.shooter.getMotor().getError() < 100;
                    }
                }
            );
        }
    }
}