package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Rapoport on 11/23/2016.
 */

//@Autonomous(name="Auto Mode", group="147")
public class AutoMode extends OpMode {

    /* Declare OpMode members. */
    Hardware147CompetitionAutoSensors robot = new Hardware147CompetitionAutoSensors();
    ElapsedTime timer = new ElapsedTime();

    public static final double DEGREES_PER_INCH = 10000/85;
    public static final double TOLERANCE = 0.5*DEGREES_PER_INCH;

    public static final int
            AUTO_DRIVE_0=36,

            AUTO_DRIVE_TURN_SHOOT_0=10,
            AUTO_DRIVE_TURN_SHOOT_1_TURN=30,
            AUTO_DRIVE_TURN_SHOOT_2=0,
            AUTO_DRIVE_TURN_SHOOT_3=46,
            //BEACON_SPLIT=4,

            AUTO_DRIVE_SHOOT_0=0,
            AUTO_DRIVE_SHOOT_1=40,
            AUTO_DRIVE_SHOOT_2=-13;

    public static final double
            BEACON_LEFT=9.5,
            BEACON_RIGHT=2.5;

    public static final double
            AUTO_BEACON_DRIVE_0=18,
            AUTO_BEACON_TURN_1=40,
            AUTO_BEACON_DRIVE_2=64.5,
            AUTO_BEACON_TURN_3=40,
            AUTO_BEACON_DRIVE_4=4;

    public static final double
            BEACON_SUB_TIME_0=3,
            BEACON_SUB_DRIVE_1=-7,
            BEACON_SUB_TURN_2=-87.5, //-87.5
            BEACON_SUB_DRIVE_3=-10,
            BEACON_SUB_TURN_4 = 4,   //7
            BEACON_SUB_DRIVE_5=19,
            BEACON_SUB_TURN_6=-20,
            BEACON_SUB_DRIVE_7=30;

    int state = 0;
    double diff = 0;
    boolean shot = false;

    boolean beaconColor;
    public double timeStateStarted=0;

    public double lastLoopTime = 0.0;
    public double timeInLoop = 0.0;

    PID turnPID=new PID(0.3,0,0.0003);

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Setup complete: Auto Mode selected");
        updateTelemetry(telemetry);

        robot.resetMotorOffset();
        robot.setPower(0.5);
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        robot.loop();
        timeInLoop = timer.seconds() - lastLoopTime;
        lastLoopTime = timer.seconds();
    }

    public void start()
    {
        timer.reset();
        robot.resetMotorOffset();
    }

    public void stop()
    {
        robot.stop();
    }

    public void drive(double distance)
    {
        diff = robot.setTgtPos(distance*DEGREES_PER_INCH);
        if(diff > 5*DEGREES_PER_INCH) {
            robot.setPower(0.8);
        } else if(diff > 3*DEGREES_PER_INCH) {
            robot.setPower(0.45);
        } else if(diff > 1*DEGREES_PER_INCH) {
            robot.setPower(0.2);
        } else {
            robot.setPower(0.1);
        }
        checkStateCompletion(diff < TOLERANCE);
    }

    public void driveToWall(double time) {
        robot.setDriveTank(0.1,0.1);
        checkStateCompletion(timeInState()>=time);
    }

    public void turn(double degrees)
    {
        robot.setPower(0.4);
        diff = robot.setTurnDegrees(degrees*DEGREES_PER_INCH);
        telemetry.addData("TURN DIFF",diff);
        checkStateCompletion(diff < TOLERANCE);
    }

    public void shootUpAndIntakeThird()
    {
        robot.intake.setTargetPosition(TeleopCompetition.INTAKE_THIRD_POS);
        setShoot(TeleopCompetition.SHOOTER_UP_POS);//end when shooter is up
    }
    public void shootDownAndIntakeThird()
    {
        robot.intake.setTargetPosition(TeleopCompetition.INTAKE_THIRD_POS);
        robot.shooter.setPower(1);
        robot.shooter.setTargetPosition((int) (TeleopCompetition.SHOOTER_DOWN_POS + 0.5));
        checkStateCompletion(intakeIsAt(TeleopCompetition.INTAKE_THIRD_POS)&&shooterIsAt(TeleopCompetition.SHOOTER_DOWN_POS));
    }

    public void shootUp()
    {
        setShoot(TeleopCompetition.SHOOTER_UP_POS);
    }
    public void shootDown()
    {
        setShoot(TeleopCompetition.SHOOTER_DOWN_POS);
    }
    public void intakeDown()
    {
        robot.intake.setPower(1);
        setIntake(TeleopCompetition.INTAKE_DOWN_POS);
        checkStateCompletion(timeInState()>3);
    }
    public void intakeDownSlow()
    {
        robot.intake.setPower(0.35);
        setIntake(TeleopCompetition.INTAKE_DOWN_POS);
        checkStateCompletion(timeInState()>3);
        telemetry.addData("TIME IN STATE",timeInState());
    }
    public void intakeHalf()
    {
        robot.intake.setPower(0.25);
        setIntake(TeleopCompetition.INTAKE_HALF_POS);
        checkStateCompletion(intakeIsAt(TeleopCompetition.INTAKE_HALF_POS));
    }
    public void intakeThird()
    {
        robot.intake.setPower(0.25);
        setIntake(TeleopCompetition.INTAKE_THIRD_POS);
        checkStateCompletion(intakeIsAt(TeleopCompetition.INTAKE_THIRD_POS));
    }
    public void intakeUp()
    {
        robot.intake.setPower(0.3);
        setIntake(TeleopCompetition.INTAKE_UP_POS);
        checkStateCompletion(intakeIsAt(TeleopCompetition.INTAKE_UP_POS));//todo: flip
    }

    public void setShoot(double tgt)
    {
        if(robot.intake.getCurrentPosition()>TeleopCompetition.INTAKE_HALF_POS-TeleopCompetition.INTAKE_AWAY_TOLERANCE)//todo: flip
        {
            robot.shooter.setPower(1);
            robot.shooter.setTargetPosition((int) (tgt + 0.5));
            telemetry.addData("Shoot TGT", tgt);
            telemetry.addData("Shoot ACT", robot.shooter.getCurrentPosition());
            checkStateCompletion(shooterIsAt(tgt));
        }
        else
        {
            robot.intake.setTargetPosition(TeleopCompetition.INTAKE_HALF_POS);
        }
    }
    public void setIntake(double tgt)
    {
        robot.intake.setTargetPosition((int)(tgt+0.5));
        telemetry.addData("Intake TGT",tgt);
        telemetry.addData("Intake ACT",robot.shooter.getCurrentPosition());
    }
    public void checkStateCompletion(boolean didEnd) {
        if (didEnd) {
            state++;
            robot.resetMotorOffset();
            robot.runWithPosition();
            robot.setPower(0);
            robot.shooter.setPower(0);
            timeStateStarted=timer.time();
        }
    }

    public void delay(double sec)
    {
        checkStateCompletion(sec<timeInState());
    }

    public double timeInState()
    {
        return timer.time()-timeStateStarted;
    }

    boolean intakeIsAt(double pos) {
        return isCloseTo(pos, robot.intake.getCurrentPosition(), TeleopCompetition.INTAKE_AWAY_TOLERANCE);
    }

    boolean shooterIsAt(double pos) {
        return isCloseTo(pos, robot.shooter.getCurrentPosition(), TeleopCompetition.SHOOTER_AWAY_TOLERANCE);
    }

    boolean isCloseTo(double d1, double d2, double tolerance) {
        return Math.abs(d1-d2) < tolerance;
    }

    /*public void driveToWall() {
        robot.setPower(0.5);
        diff = robot.setTgtPos(getDistanceFromWall());
        checkStateCompletion(diff < TOLERANCE);
    }*/

    public void driveToBeacon() {
        driveToBeacon(false);
    }

    public void driveToBeaconBackwards() {
        driveToBeacon(true);
    }

    private void driveToBeacon(boolean reverse)
    {
        if(reverse) {
            robot.setDriveTank(-0.3, -0.3);
        } else {
            robot.setDriveTank(0.3, 0.3);
        }
        checkStateCompletion(getGroundSensor()>1.21);
    }

    public void driveSlowToBeacon(boolean reverse) {
        if(reverse) {
            robot.setDriveTank(-0.2, -0.2);
        } else {
            robot.setDriveTank(0.2, 0.2);
        }
        checkStateCompletion(getGroundSensor()>1.21);
    }

    public void turnToWall() {
        robot.setPower(0.3);
        turnPID.setTarget(0);
        turnPID.update(robot.ultrasonics.getAngle(), lastLoopTime);
        double power = turnPID.get();
        robot.setDriveTank(power, -power);
        //checkStateCompletion(Math.abs(robot.ultrasonics.getAngle()) < 5);
    }

    public void pressBeacon() {
        robot.beaconPusher.setTargetPosition(1250);
        checkStateCompletion(isCloseTo(1250, robot.beaconPusher.getCurrentPosition(), TOLERANCE) || timeInState() > 2);
    }

    public void unpressBeacon() {
        robot.beaconPusher.setTargetPosition(0);
        checkStateCompletion(isCloseTo(0, robot.beaconPusher.getCurrentPosition(), TOLERANCE));
    }

    public static class BEACON {
        public static final boolean RED = true;
        public static final boolean BLUE = false;
    }

    public void getBeaconColor() {
        /*if(getBeaconA() > getBeaconB()) {
            beaconColor= BEACON.BLUE;
        } else {
            beaconColor= BEACON.RED;
        }*/
        beaconColor = getBeaconA()>getBeaconB();
    }

    public double getBeaconDriveDist(boolean target)
    {
        if(beaconColor==target)
        {
            return BEACON_LEFT;
        }
        return BEACON_RIGHT;
    }

    public double getDistanceFromWall()
    {
        return robot.ultrasonics.getDist();
    }
    public double getAngleFromWall()
    {
        return robot.ultrasonics.getAngle();
    }
    public double getGroundSensor()
    {
        return robot.lightGround.getRawLightDetected();
    }
    public double getBeaconA()
    {
        return robot.lightA.getRawLightDetected();
    }
    public double getBeaconB()
    {
        return robot.lightB.getRawLightDetected();
    }

}
