package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Rapoport on 11/23/2016.
 */

@Autonomous(name="Auto Mode", group="147")
public class AutoMode extends OpMode {

    /* Declare OpMode members. */
    Hardware147CompetitionAuto1 robot = new Hardware147CompetitionAuto1();
    ElapsedTime timer = new ElapsedTime();

    double DEGREES_PER_INCH = 10000/85;
    double TOLERANCE = 0.5*DEGREES_PER_INCH;

    int state = 0;
    double diff = 0;
    boolean shot = false;

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
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

    }

    public void checkStateCompletion(boolean didEnd) {
        if (didEnd) {
            state++;
            robot.resetMotorOffset();
        }
    }
}
