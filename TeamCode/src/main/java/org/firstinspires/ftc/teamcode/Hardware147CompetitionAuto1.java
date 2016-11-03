package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
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
public class Hardware147CompetitionAuto1
{
    /* Public OpMode members. */
    public DcMotor[][] motors=new DcMotor[2][2];
    public int[][] motorOffset = new int[2][2];
    public DcMotor  intake,shooter;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        motors[0][0]   = hwMap.dcMotor.get("left_a");
        motors[0][1]   = hwMap.dcMotor.get("left_b");
        motors[1][0]  = hwMap.dcMotor.get("right_a");
        motors[1][1]  = hwMap.dcMotor.get("right_b");
        intake = hwMap.dcMotor.get("aux_a");
        shooter = hwMap.dcMotor.get("aux_b");

        // Set all motors to zero power
        for(int i=0;i<motors.length;i++)
        {
            for(int j=0;j<motors[0].length;i++)
            {
                motors[i][j].setPower(0.8);
                motors[i][j].setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
        }
        intake.setPower(0.5);
        intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        shooter.setPower(1);
        shooter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public int setTgtPos(int tgt)
    {
        return setTgtPos(tgt,tgt);
    }

    public int setTurnDistance(int start,int change)
    {
        return setTgtPos(start+change,start-change);
    }

    public int setTgtPos(int left,int right)
    {
        for(int i=0;i<motors[0].length;i++)
        {
            motors[0][i].setTargetPosition(left);
            motors[1][i].setTargetPosition(-right);
        }
        return motors[0][0].getCurrentPosition();
    }

    public int changeTgtPos(int dPos) {
        return changeTgtPos(dPos, dPos);
    }

    public int changeTgtPos(int dLeft, int dRight) {
        return setTgtPos(dLeft+motorOffset[0][0], dRight+motorOffset[1][0]);
    }

    public void resetMotorOffset() {
        for(int i = 0; i < motors.length; i++) {
            for(int j = 0; j < motors[i].length; j++) {
                motorOffset[i][j] = motors[i][j].getCurrentPosition();
            }
        }
    }

    public int getLeftSide() {
        return motors[0][0].getCurrentPosition();
    }

    public int getRightSide() {
        return motors[1][0].getCurrentPosition();
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

