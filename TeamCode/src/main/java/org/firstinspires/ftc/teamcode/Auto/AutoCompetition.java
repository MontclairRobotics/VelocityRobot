package org.firstinspires.ftc.teamcode.Auto;

import org.firstinspires.ftc.teamcode.Auto.States.State;
import org.firstinspires.ftc.teamcode.Auto.States.StateMachine;
import org.firstinspires.ftc.teamcode.Auto.States.StateObj;
import org.firstinspires.ftc.teamcode.Robot;

/**
 * Created by MHS Robotics on 12/6/2016.
 */

public class AutoCompetition extends Robot {


    public static final int
            AUTO_DRIVE_0=36,

            AUTO_DRIVE_TURN_SHOOT_0=27,
            AUTO_DRIVE_TURN_SHOOT_1_TURN=40,
            AUTO_DRIVE_TURN_SHOOT_2=-3,
            AUTO_DRIVE_TURN_SHOOT_3=33,

            AUTO_DRIVE_SHOOT_0=18,
            AUTO_DRIVE_SHOOT_1=33,
            AUTO_DRIVE_SHOOT_2=-12;

    StateMachine machine;
    /*
     * Code to run ONCE when the driver hits PLAY
     */

    public AutoCompetition(StateMachine m)
    {
        machine=m;
    }
    public AutoCompetition(State... m) {
        this(new StateMachine(m));
    }

    @Override
    public void user_start()
    {
        machine.start();
    }
    @Override
    public void update()
    {
        machine.update();
    }

    /*
    ================================================================

                               AUTO STATES

    ================================================================
     */

    public abstract class AutoState extends StateObj {
        private double secStart;
        public void start()
        {
            hardware.resetMotorOffset();
            hardware.setPower(0);
            hardware.shooter.setPower(0);
            secStart=secTotal;
        }
        public double secInState()
        {
            return secTotal-secStart;
        }
    }

    public class Drive extends AutoState
    {
        private double dist,diff;
        public Drive(double d)
        {
            dist=d;
        }
        public void update()
        {
            diff=hardware.setTgtPos(dist);
        }
        public boolean isDone(){return diff<TOLERANCE;}
    }

    public class Turn extends AutoState
    {
        private double dist,diff;
        public Turn(double d) { dist=d; }
        public void update() {
            diff = hardware.setTurnDegrees(dist);
        }
        public boolean isDone(){return diff<TOLERANCE;}
    }

    public class ShootUp extends AutoState
    {
        public void update(){shootUp();}
        public boolean isDone(){return shooterIsAtTgt();}
    }
    public class ShootDown extends AutoState
    {
        public void update(){shootDown();}
        public boolean isDone(){return shooterIsAtTgt();}
    }
    public class IntakeDown extends AutoState
    {
        public void update(){intakeDown();}
        public boolean isDone(){return secInState()>3;}
    }
    public class IntakeDownSlow extends AutoState
    {
        public void update(){intakeDownSlow();}
        public boolean isDone(){return secInState()>3;}
    }
    public class IntakeHalf extends AutoState
    {
        public void update(){intakeHalf();}
        public boolean isDone(){return secInState()>3;}
    }
    public class IntakeThird extends AutoState
    {
        public void update(){intakeThird();}
        public boolean isDone(){return secInState()>3;}
    }
    public class IntakeUp extends AutoState
    {
        public void update(){intakeUp();}
        public boolean isDone(){return secInState()>3;}
    }
    public class Delay extends AutoState
    {
        private double tgt;
        public Delay(double t)
        {
            tgt=t;
        }
        public boolean isDone(){return secInState()>tgt;}
    }
}
