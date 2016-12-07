package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by MHS Robotics on 12/4/2016.
 */

public abstract class Robot extends OpMode {
    /* Declare OpMode members. */
    public static Hardware147 hardware = new Hardware147(); // use the class created to define a Pushbot's hardware
    public static Controller147 ctrl = new Controller147();
    private ElapsedTime time = new ElapsedTime();

    //========================================
    //configs:
    public static final double
            //drive configs
            MAX_ACCEL = 20.0,
            TURN_ACCEL = 10.0,
            HIGH_POWER = 1.0,
            HALF_POWER = 0.4,
            LOW_POWER = 0.2,
            TURN_SPD = 0.5,
            HIGH_TURN_SPD_FACTOR = 2.0,
            SMALL_TURN_SPD = 0.25;
    public static final int
            //intake configs
            INTAKE_DOWN_POS = 1100,
            INTAKE_THIRD_POS = 600,
            INTAKE_HALF_POS = 400,//325,
            INTAKE_UP_POS = -100,
            //shooter configs
            SHOOTER_DOWN_POS = 200,//200,
            SHOOTER_UP_POS = -950,
            //Tolerances
            SHOOTER_AWAY_TOLERANCE = 50,
            INTAKE_AWAY_TOLERANCE = 100;
    //Auto Configs
    public static final double DEGREES_PER_INCH = 10000/85;
    public static final double TOLERANCE = 0.5*DEGREES_PER_INCH;
    public static final double MAX_ENCODER_ACCEL=12*DEGREES_PER_INCH;//TODO TODO TODO
    //========================================


    public double
            //intake variables
            intakePos=0,
            //shooter variables
            shooterPos=0;

    public static String dp = "%.2f", ip = "%d";

    public double secTotal = 0, secInLoop = 0;

    @Override
    public final void init() {
        hardware.init(hardwareMap);
        ctrl.init(gamepad1, gamepad2);
        hardware.resetMotorOffset();

        telemetry.addData("Say", "Don't forget to press START+(A or B)");    //
        updateTelemetry(telemetry);
    }

    @Override
    public final void init_loop() {
    }

    @Override
    public final void start() {
        time.reset();
        user_start();
    }
    public void user_start(){}

    @Override
    public final void loop() {
        secInLoop = time.seconds() - secTotal;
        secTotal = time.seconds();
        update();
        hardware.intake.setTargetPosition((int)(intakePos+hardware.intakeOffset+0.5));
        if(Math.abs((hardware.shooter.getCurrentPosition()+(int)hardware.shooterOffset)-
                (shooterPos+(int)hardware.shooterOffset)) > SHOOTER_AWAY_TOLERANCE) {
            hardware.shooter.setPower(1.0);
            hardware.shooter.setTargetPosition((int)(shooterPos+hardware.shooterOffset+0.5));
            telemetry.addData("shooter", "from target: " + Math.abs((hardware.shooter.getCurrentPosition()+(int)hardware.shooterOffset)-
                    (shooterPos+(int)hardware.shooterOffset)));
        } else {
            telemetry.addData("shooter", "zero power");
            hardware.shooter.setPower(0);
        }
        updateTelemetry(telemetry);
    }

    public abstract void update();

    @Override
    public final void stop() {
        hardware.disable();
        telemetry.addData("say", "Disabled");
        updateTelemetry(telemetry);
    }

    public double constrainChange(double val,double lastVal,double chg)
    {
        return constrain(val,lastVal-chg,lastVal+chg);
    }

    public double constrain(double val,double min,double max)
    {
        if(val<min)return min;
        if(val>max)return max;
        return val;
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
        hardware.intake.setPower(1);
        setIntake(INTAKE_DOWN_POS);
    }
    public void intakeDownSlow()
    {
        hardware.intake.setPower(0.35);
        setIntake(INTAKE_DOWN_POS);
    }
    public void intakeHalf()
    {
        hardware.intake.setPower(0.25);
        setIntake(INTAKE_HALF_POS);
    }
    public void intakeThird()
    {
        hardware.intake.setPower(0.25);
        setIntake(INTAKE_THIRD_POS);
    }
    public void intakeUp()
    {
        hardware.intake.setPower(0.3);
        setIntake(INTAKE_UP_POS);
    }

    public void setShoot(double tgt)
    {
        if(hardware.intake.getCurrentPosition()>INTAKE_HALF_POS-INTAKE_AWAY_TOLERANCE)//todo: flip
        {
            hardware.shooter.setPower(1);
            shooterPos=tgt;
        }
        else
        {
            intakePos=INTAKE_HALF_POS;
        }
    }
    public void setIntake(double tgt)
    {
        intakePos=tgt;
    }

    public boolean intakeIsAtTgt()
    {
        return intakeIsAt(intakePos);
    }
    public boolean intakeIsAt(double pos) {
        return isCloseTo(pos, hardware.intake.getCurrentPosition(), INTAKE_AWAY_TOLERANCE);
    }

    public boolean shooterIsAtTgt()
    {
        return shooterIsAt(shooterPos);
    }
    public boolean shooterIsAt(double pos) {
        return isCloseTo(pos, hardware.shooter.getCurrentPosition(), SHOOTER_AWAY_TOLERANCE);
    }

    public boolean isCloseTo(double d1, double d2, double tolerance) {
        return Math.abs(d1-d2) < tolerance;
    }
}
