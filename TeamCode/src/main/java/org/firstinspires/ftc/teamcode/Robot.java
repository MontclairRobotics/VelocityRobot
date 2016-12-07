package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by MHS Robotics on 12/4/2016.
 */

public abstract class Robot extends OpMode {
    /* Declare OpMode members. */
    Hardware147 hardware = new Hardware147(); // use the class created to define a Pushbot's hardware
    Controller147 ctrl = new Controller147();
    ElapsedTime time = new ElapsedTime();

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
    //========================================

    public static String dp = "%.2f", ip = "%d";

    double secTotal = 0, secInLoop = 0;

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
    public abstract void user_start();

    @Override
    public final void loop() {
        secInLoop = time.seconds() - secTotal;
        secTotal = time.seconds();
        update();
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
}
