package org.firstinspires.ftc.teamcode.Auto;

import org.firstinspires.ftc.teamcode.Auto.States.State;
import org.firstinspires.ftc.teamcode.Auto.States.StateMachine;
import org.firstinspires.ftc.teamcode.Auto.States.StateObj;
import org.firstinspires.ftc.teamcode.Robot;

/**
 * Created by MHS Robotics
 */

public abstract class AutoCompetition extends Robot {


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

                           AUTO SEQUENCES

    ================================================================
     */
    public static class TurnShoot1 extends StateMachine
    {
        TurnShoot1(double drive0,double turn1,double drive2,double drive3) {
            super(
                    new DriveTurnDrive(drive0,turn1,drive2),
                    new Shoot(),
                    new Drive(drive3));
        }
    }
    public static class TurnShoot2 extends StateMachine
    {
        TurnShoot2(double drive0,double turn1,double drive2,double drive3) {
            super(
                    new LoadBallInIntake(),
                    new DriveTurnDrive(drive0,turn1,drive2),
                    new Shoot(),
                    new LoadBallInShooter(),
                    new Shoot(),
                    new Drive(drive3));
        }
    }


    /*
    ================================================================

                         COMPLEX AUTO STATES

    ================================================================
     */
    public static class DriveTurnDrive extends StateMachine
    {
        DriveTurnDrive(double drive0,double turn1,double drive2) {
            super(
                    new Drive(drive0),
                    new Turn(turn1),
                    new Drive(drive2));
        }
    }
    public static class LoadBallInIntake extends StateMachine
    {
        LoadBallInIntake()
        {
            super(
                    new IntakeDownSlow(),
                    new IntakeThird());
        }
    }
    public static class LoadBallInShooter extends StateMachine
    {
        LoadBallInShooter()
        {
            super(
                    new IntakeUp(),
                    new IntakeHalf()
            );
        }
    }
    public static class Shoot extends StateMachine
    {
        public Shoot()
        {
            super(new Delay(4),new ShootUp(),new ShootDown());
        }
    }

    /*
    ================================================================

                               AUTO STATES

    ================================================================
     */

    public static class Drive extends AutoState
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

    public static class Turn extends AutoState
    {
        private double dist,diff;
        public Turn(double d) { dist=d; }
        public void update() {
            diff = hardware.setTurnDegrees(dist);
        }
        public boolean isDone(){return diff<TOLERANCE;}
    }

    public static class ShootUp extends AutoState
    {
        public void update(){shootUp();}
        public boolean isDone(){return shooterIsAtTgt();}
    }
    public static class ShootDown extends AutoState
    {
        public void update(){shootDown();}
        public boolean isDone(){return shooterIsAtTgt();}
    }
    public static class IntakeDown extends AutoState
    {
        public void update(){intakeDown();}
        public boolean isDone(){return secInState()>3;}
    }
    public static class IntakeDownSlow extends AutoState
    {
        public void update(){intakeDownSlow();}
        public boolean isDone(){return secInState()>3;}
    }
    public static class IntakeHalf extends AutoState
    {
        public void update(){intakeHalf();}
        public boolean isDone(){return secInState()>3;}
    }
    public static class IntakeThird extends AutoState
    {
        public void update(){intakeThird();}
        public boolean isDone(){return secInState()>3;}
    }
    public static class IntakeUp extends AutoState
    {
        public void update(){intakeUp();}
        public boolean isDone(){return secInState()>3;}
    }
    public static class Delay extends AutoState
    {
        private double tgt;
        public Delay(double t)
        {
            tgt=t;
        }
        public boolean isDone(){return secInState()>tgt;}
    }

    public static abstract class AutoState extends StateObj {
        private double secStart;
        public void start()
        {
            hardware.resetMotorOffset();
            hardware.setPower(0);
            hardware.shooter.setPower(0);
            secStart=getSecTotal();
        }
        public double secInState()
        {
            return getSecTotal()-secStart;
        }
    }
}
