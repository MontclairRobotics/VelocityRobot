package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
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
public class Sensors147 {
    public static double
            DISTANCE_BETWEEN_SENSORS = 11,
            DISTANCE_FROM_B_TO_CENTER = 4,
            DISTANCE_FROM_WALL = 20,
            A_OFFSET=1.5,
            B_OFFSET=2.5;

    int SMOOTH_TIME=100;
    double ang,dist;
    SmoothData avgAngle=new SmoothData(SMOOTH_TIME),avgDist=new SmoothData(SMOOTH_TIME);


    public UltrasonicSensor distASensor, distBSensor;

    public LightSensor lightGround;

    /* local OpMode members. */
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        distASensor = hwMap.ultrasonicSensor.get("distA");
        distBSensor = hwMap.ultrasonicSensor.get("distB");

        lightGround = hwMap.lightSensor.get("lightGround");
        lightGround.enableLed(true);
    }

    public double getAng() {
        double distA = getDistA();
        double distB = getDistB();
        if (isOK(distA) && isOK(distB)) {
            distA-=A_OFFSET;
            distB-=B_OFFSET;
            ang=Math.atan2(distA-distB,DISTANCE_BETWEEN_SENSORS);
            dist=distB*Math.cos(ang)-DISTANCE_FROM_B_TO_CENTER*Math.sin(ang);
        }
        avgAngle.add(ang);
        avgDist.add(dist);
        return avgAngle.get();
    }
    public double getDist()
    {
        return avgDist.get();
    }

    public boolean isOK(double dist)
    {
        return dist>1&&dist<60;
    }

    public double getDistA()
    {
        return distASensor.getUltrasonicLevel();
    }
    public double getDistB()
    {
        return distBSensor.getUltrasonicLevel();
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
}

