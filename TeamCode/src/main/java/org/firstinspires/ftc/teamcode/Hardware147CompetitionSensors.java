package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot.
 * See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Motor channel:  Manipulator drive motor:  "left_arm"
 * Servo channel:  Servo to open left claw:  "left_hand"
 * Servo channel:  Servo to open right claw: "right_hand"
 */
public class Hardware147CompetitionSensors
{
    /* Public OpMode members. */
    public DcMotor[][] motors=new DcMotor[2][2];

    public double shooterOffset=0;
    public double intakeOffset=0;

    public DcMotor  intake,shooter,beaconPusher;


    public Ultrasonic147 ultrasonics;
    public LightSensor lightGround;

   public LightSensor lightA, lightB;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        motors[0][0]   = hwMap.dcMotor.get("left_driveA");
        motors[0][1]   = hwMap.dcMotor.get("left_driveB");
        motors[1][0]  = hwMap.dcMotor.get("right_driveA");
        motors[1][1]  = hwMap.dcMotor.get("right_driveB");
        intake = hwMap.dcMotor.get("intake");
        shooter = hwMap.dcMotor.get("shooter");
        beaconPusher = hwMap.dcMotor.get("beacon_pusher");

        // Set all motors to zero power
        for(int i=0;i<motors.length;i++)
        {
            for(int j=0;j<motors[0].length;j++)
            {
                motors[i][j].setPower(0);
                motors[i][j].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motors[i][j].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
        }
        intake.setPower(0.35);
        intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        shooter.setPower(1);
        shooter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        //sensors
        ultrasonics = new Ultrasonic147(ahwMap,"distA","distB");

        lightGround = hwMap.lightSensor.get("lightGround");
        lightGround.enableLed(true);

        lightA = hwMap.lightSensor.get("lightA");
        lightB = hwMap.lightSensor.get("lightB");
    }

    public void setDriveTank(double left,double right)
    {
        for(int i=0;i<motors[0].length;i++)
        {
            motors[0][i].setPower(left);
            motors[1][i].setPower(-right);
        }
    }

    public void beaconPusherLeft() {
        beaconPusher.setTargetPosition(-720);
    }

    public void beaconPusherRight() {
        beaconPusher.setTargetPosition(720);
    }

    public void beaconPusherReset() {
        beaconPusher.setTargetPosition(0);
    }

    public void setBeaconPusher(double power) {
        beaconPusher.setPower(power);
    }

    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     * @throws InterruptedException
     */
    public void waitForTick(long periodMs) throws InterruptedException {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)
            Thread.sleep(remaining);

        // Reset the cycle clock for the next pass.
        period.reset();
    }

    public void stop()
    {
        ultrasonics.interrupt();
    }
}

