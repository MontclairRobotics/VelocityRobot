package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Rapoport on 11/23/2016.
 */

//@Autonomous(name="Auto Mode", group="147")
public class AutoMode extends OpMode {

    /* Declare OpMode members. */
    Hardware147CompetitionAuto1 robot = new Hardware147CompetitionAuto1();
    ElapsedTime timer = new ElapsedTime();

    public static final double DEGREES_PER_INCH = 10000/85;
    public static final double TOLERANCE = 0.5*DEGREES_PER_INCH;

    public static final int
            AUTO_DRIVE_0=36,

            AUTO_DRIVE_TURN_SHOOT_0=27,
            AUTO_DRIVE_TURN_SHOOT_1_TURN=40,
            AUTO_DRIVE_TURN_SHOOT_2=-3,
            AUTO_DRIVE_TURN_SHOOT_3=33,

            AUTO_DRIVE_SHOOT_0=18,
            AUTO_DRIVE_SHOOT_1=33,
            AUTO_DRIVE_SHOOT_2=-12;

    int state = 0;
    double diff = 0;
    boolean shot = false;
    public double timeStateStarted=0;

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

    }

    public void start()
    {
        timer.reset();
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

    public void turn(double degrees)
    {
        robot.setPower(0.8);
        diff = robot.setTurnDegrees(degrees*DEGREES_PER_INCH);
        telemetry.addData("TURN DIFF",diff);
        checkStateCompletion(diff < TOLERANCE);
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
}
