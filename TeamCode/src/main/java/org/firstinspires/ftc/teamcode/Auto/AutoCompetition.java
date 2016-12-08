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

    StateMachine machine=new StateMachine();
    /*
     * Code to run ONCE when the driver hits PLAY
     */

    public AutoCompetition(){}
    public void setMachine(State... m)
    {
        setMachine(new StateMachine(m));
    }
    public void setMachine(StateMachine m)
    {
        this.machine=m;
    }

    public final void user_init()
    {
        setMachine(getMachine());
    }

    public abstract StateMachine getMachine();

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

    public class NoTurnShoot1 extends StateMachine
    {
        public NoTurnShoot1(double drive0,double drive3,double turn4,double drive5)
        {
            super(
                    new Drive(drive0),
                    new Shoot(),
                    new IntakeUp(),
                    new Drive(drive3),
                    new Turn(turn4),
                    new Drive(drive5));
        }
    }
    public class NoTurnShoot2 extends StateMachine
    {
        public NoTurnShoot2(double drive0,double drive3,double turn4,double drive5)
        {
            super(
                    new LoadBallInIntake(),
                    new Drive(drive0),
                    new Shoot(),
                    new LoadBallInShooter(),
                    new Shoot(),
                    new IntakeUp(),
                    new Drive(drive3),
                    new Turn(turn4),
                    new Drive(drive5));
        }
    }
    public class TurnShoot1 extends StateMachine
    {
        public TurnShoot1(double drive0,double turn1,double drive2,double drive3) {
            super(
                    new DriveTurnDrive(drive0,turn1,drive2),
                    new Shoot(),
                    new IntakeUp(),
                    new Drive(drive3));
        }
    }
    public class TurnShoot2 extends StateMachine
    {
        public TurnShoot2(double drive0,double turn1,double drive2,double drive3) {
            super(
                    new LoadBallInIntake(),
                    new DriveTurnDrive(drive0,turn1,drive2),
                    new Shoot(),
                    new LoadBallInShooter(),
                    new Shoot(),
                    new IntakeUp(),
                    new Drive(drive3));
        }
    }


    /*
    ================================================================

                         COMPLEX AUTO STATES

    ================================================================
     */
    public class DriveTurnDrive extends StateMachine
    {
        public DriveTurnDrive(double drive0,double turn1,double drive2) {
            super(
                    new Drive(drive0),
                    new Turn(turn1),
                    new Drive(drive2));
        }
    }
    public class LoadBallInIntake extends StateMachine
    {
        public LoadBallInIntake()
        {
            super(
                    new IntakeDownSlow(),
                    new IntakeThird());
        }
    }
    public class LoadBallInShooter extends StateMachine
    {
        public LoadBallInShooter()
        {
            super(
                    new IntakeUp(),
                    new IntakeHalf()
            );
        }
    }
    public class Shoot extends StateMachine
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

    public abstract class AutoState extends StateObj {
        private double secStart;
        public void start()
        {
            secStart=getSecTotal();
        }
        public void stop()
        {
            hardware.resetMotorOffset();
            hardware.setPower(0);
            hardware.shooter.setPower(0);
        }
        public double secInState()
        {
            return getSecTotal()-secStart;
        }
    }
}
