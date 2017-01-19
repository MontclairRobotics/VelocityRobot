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
 * Motor channel:  Left  drive motor:        "left_drive" rick harrison bald overlord rick harrison bald overlordrick harrison bald overlordrick harrison bald overlordrick harrison bald overlordrick harrison bald overlordrick harrison bald overlordrick harrison bald overlordrick harrison bald overlordrick harrison bald overlordrick harrison bald overlordrick harrison bald overlordrick harrison bald overlordrick harrison bald overlordrick harrison bald overlordrick harrison bald overlord
 * Motor channel:  Right drive motor:        "right_drive"
 * Motor channel:  Manipulator drive motor:  "left_arm"
 * Servo channel:  Servo to open left claw:  "left_hand"
 * Servo channel:  Servo to open right claw: "right_hand"
 */
public class Hardware147CompetitionAutoSensors
{
    /* Public OpMode members. */
    public DcMotor[][] motors=new DcMotor[2][2];
    public int[][] motorOffset = new int[2][2];
    public DcMotor  intake,shooter,beaconPusher;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();


    public Ultrasonic147 ultrasonics;
    public LightSensor lightGround;

    public LightSensor lightA,lightB;

    float lastPosition = 0.0f;
    float speed = 0.0f;

    public static final float hugWeight = 1.15f;

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
                motors[i][j].setPower(0.8);
                motors[i][j].setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motors[i][j].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
        }
        intake.setPower(0.35);
        intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        shooter.setPower(1);
        shooter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        beaconPusher.setPower(0.15);
        beaconPusher.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        beaconPusher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);


        //sensors
        ultrasonics = new Ultrasonic147(ahwMap,"distA","distB");

        lightGround = hwMap.lightSensor.get("lightGround");
        lightGround.enableLed(true);

        lightA = hwMap.lightSensor.get("lightA");
        lightB = hwMap.lightSensor.get("lightB");
    }

    public double setTurnDegrees(double degrees)
    {
        double chg=(degrees/360)*18*Math.PI;
        return setTgtPos(chg,-chg);
    }

    public double setTgtPos(double tgt)
    {
        return setTgtPos((int)(tgt+0.5));
    }

    public double setTgtPos(int tgt)
    {
        return setTgtPos(tgt,tgt);
    }

    public double setTgtPos(double left,double right)
    {
        return setTgtPos((int)(left+0.5),(int)(right+0.5));
    }
    public double setTgtPos(int left,int right)
    {
        double error=0;
        for(int i=0;i<motors[0].length;i++)
        {
            motors[0][i].setTargetPosition(left+motorOffset[0][i]);
            motors[1][i].setTargetPosition(-right+motorOffset[1][i]);
            error += Math.abs(motors[0][i].getCurrentPosition() - (left+motorOffset[0][i]))
                    + Math.abs(motors[1][i].getCurrentPosition() - (-right+motorOffset[1][i]));
        }
        return error/4;
    }

    public double setTgtPosLeftWeight(int left, int right) {
        setTgtPos((int)((left*hugWeight)+0.5), (int)(right+0.5));
        double error = 0;
        for(int i = 0; i < motors[1].length; i++) {
            error += Math.abs(motors[1][i].getCurrentPosition() - (right+motorOffset[1][i]));
        }

        return error/2;
    }

    public double setTgtPosRightWeight(int left, int right) {
        setTgtPos((int)(left+0.5), (int)((right*hugWeight)+0.5));
        double error = 0;
        for(int i = 0; i < motors[0].length; i++) {
            error += Math.abs(motors[0][i].getCurrentPosition() - (left+motorOffset[0][i]));
        }

        return error/2;
    }

    public void resetMotorOffset() {
        for(int i = 0; i < motors.length; i++) {
            for(int j = 0; j < motors[i].length; j++) {
                motorOffset[i][j] = motors[i][j].getCurrentPosition();
            }
        }
    }

    public void setPower(double left, double right) {
        for(DcMotor m : motors[0]) {
            m.setPower(left);
        }

        for(DcMotor m : motors[1]) {
            m.setPower(right);
        }
    }

    public void setPowerLeftWeight(double power) {
        setPowerLeftWeight(power, power);
    }

    public void setPowerLeftWeight(double left, double right) {
        for(DcMotor m : motors[0]) {
            m.setPower(left*hugWeight);
        }

        for(DcMotor m : motors[1]) {
            m.setPower(right);
        }
    }

    public void setPowerRightWeight(double power) {
        setPowerRightWeight(power, power);
    }

    public void setPowerRightWeight(double left, double right) {
        for(DcMotor m : motors[0]) {
            m.setPower(left);
        }

        for(DcMotor m : motors[1]) {
            m.setPower(right*hugWeight);
        }
    }

    public void setPower(double pwr)
    {
        for(int i=0;i<motors.length;i++)
        {
            for(int j=0;j<motors[0].length;j++)
            {
                motors[i][j].setPower(pwr);
            }
        }
    }

    public void runWithPower() {
        if(motors[0][0].getMode() == DcMotor.RunMode.RUN_USING_ENCODER) return;
        for(DcMotor[] motorSide : motors) {
            for(DcMotor motor : motorSide) {
                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motor.setPower(0);
            }
        }
    }

    public void runWithPosition() {
        if(motors[0][0].getMode() == DcMotor.RunMode.RUN_TO_POSITION) return;
        for(DcMotor[] motorSide : motors) {
            for(DcMotor motor : motorSide) {
                motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor.setPower(0.8);
            }
        }
    }

    public void setDriveTank(double left,double right)
    {
        int leftOffset = left > 0 ? 1000 : -1000;
        int rightOffset = right > 0 ? -1000 : 1000;
        for(int i=0;i<motors[0].length;i++)
        {
            motors[0][i].setTargetPosition(motors[0][i].getCurrentPosition() + leftOffset);
            motors[0][i].setPower(left);
            motors[1][i].setTargetPosition(motors[1][i].getCurrentPosition() + rightOffset);
            motors[1][i].setPower(right);
        }
    }

    public int getLeftSide() {
        return motors[0][0].getCurrentPosition();
    }

    public int getRightSide() {
        return motors[1][0].getCurrentPosition();
    }

    public float getSpeed() {
        return speed;
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


    public void loop() {
        float currentPosition = 0.0f;
        for (DcMotor[] motorSide : motors) {
            for (DcMotor motor : motorSide) {
                currentPosition += motor.getCurrentPosition();
            }
        }
        currentPosition /= 4;
        speed = currentPosition - lastPosition;
        lastPosition = currentPosition;
    }

    public void stop()
    {
        ultrasonics.interrupt();
    }

}

