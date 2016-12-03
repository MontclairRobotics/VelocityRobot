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
public class Sensors147
{
    public static double
            DISTANCE_BETWEEN_SENSORS=11,
            DISTANCE_FROM_B_TO_CENTER=4,
            DISTANCE_FROM_WALL=10;

    public static final int HISTORY_LEN=10;
    private double[]
            distAHistory=new double[HISTORY_LEN],
            distBHistory=new double[HISTORY_LEN];
    private int distAI,distBI;
    private double distAAvg,distBAvg;


    public UltrasonicSensor distA,distB;

    public LightSensor lightGround;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();
    private boolean fullA,fullB;

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        distA     = hwMap.ultrasonicSensor.get("distA");
        distB     = hwMap.ultrasonicSensor.get("distB");

        lightGround = hwMap.lightSensor.get("lightGround");
        lightGround.enableLed(true);
    }

    public double getDistA()
    {
        double val=distA.getUltrasonicLevel();///2.54-1.5;
        /*if(fullA) {
            if(val>2&&Math.abs(distAAvg/HISTORY_LEN-val)<20) {
                distAAvg += val - distAHistory[distAI];
                distAI = (distAI + 1) % HISTORY_LEN;
                distAHistory[distAI] = val;
                return val;
            }
            return distAAvg / HISTORY_LEN;
        }
        else
        {
            distAHistory[distAI]=val;
            distAAvg+=val;
            distAI++;
            if(distAI>=HISTORY_LEN) {
                fullA = true;
                distAI=0;
            }*/
            return val;
        //}
    }
    public double getDistB()
    {
        double val=distB.getUltrasonicLevel();///2.54-1.5;
        /*if(fullB) {
            if(val>2&&Math.abs(distBAvg/HISTORY_LEN-val)<20) {
                distBAvg += val - distBHistory[distBI];
                distBI = (distBI + 1) % HISTORY_LEN;
                distBHistory[distBI] = val;
                return val;
            }
            return distBAvg/HISTORY_LEN;
        }
        else
        {
            distBHistory[distBI]=val;
            distBAvg+=val;
            distBI++;
            if(distBI>=HISTORY_LEN) {
                fullB = true;
                distBI=0;
            }*/
            return val;
        //}
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

