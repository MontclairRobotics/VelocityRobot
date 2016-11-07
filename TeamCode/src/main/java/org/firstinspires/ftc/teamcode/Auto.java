package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Rapoport on 11/7/16.
 */

@Autonomous(name="Auto", group="147")
public class Auto extends OpMode {

    public enum Mode {
        drive, drive_shoot, drive_left_shoot, drive_right_shoot, drive_left_drive, drive_right_drive
    }

    /* Declare OpMode members. */
    Hardware147CompetitionAuto1 robot = new Hardware147CompetitionAuto1();
    ElapsedTime timer=new ElapsedTime();

    int stage = 0;
    Mode autoMode;

    double DEGREES_PER_INCH=10000/85;

    public Auto(Mode mode) {
        this.autoMode = mode;
    }

    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Setup complete: Auto Mode selected");    //
        updateTelemetry(telemetry);

        //tgtDegrees=(int)(TARGET_DRIVE_INCHES*DEGREES_PER_INCH+0.5);
        robot.resetMotorOffset();
    }

    @Override
    public void loop() {
        switch (autoMode) {
            case drive:
                driveLoop();
                break;
            case drive_shoot:
                drive_shootLoop();
                break;
            case drive_left_shoot:
                drive_left_shootLoop();
                break;
            case drive_right_shoot:
                drive_right_shootLoop();
                break;
            case drive_left_drive:
                drive_left_driveLoop();
                break;
            case drive_right_drive:
                drive_right_driveLoop();
                break;
        }
    }

    void driveLoop() {
        //TODO: copy auto code
    }

    void drive_shootLoop() {
        //TODO: copy auto code
    }

    void drive_left_shootLoop() {
        //TODO: copy auto code
    }

    void drive_right_shootLoop() {
        //TODO: copy auto code
    }

    void drive_left_driveLoop() {
        //TODO: copy auto code
    }

    void drive_right_driveLoop() {
        //TODO: copy auto code
    }

    public void prepareNextStage() {
        stage++;
        robot.resetMotorOffset();
    }

//    public void resetState() {
//        state = 0;
//        robot.resetMotorOffset();
//    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

    }
}
