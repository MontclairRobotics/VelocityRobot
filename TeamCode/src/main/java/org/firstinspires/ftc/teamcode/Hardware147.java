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
public class Hardware147
{
    /* Public OpMode members. */
    public DcMotor[][] motors=new DcMotor[2][2];
    public double[][] motorOffset=new double[2][2];

    public double shooterOffset=0;
    public double intakeOffset=0;

    public DcMotor  intake,shooter;

    //public Ultrasonic147 ultrasonics;
    public LightSensor lightGround;

    // public ColorSensor colorBeacon;

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
        //ultrasonics = new Ultrasonic147(ahwMap,"distA","distB");

        lightGround = hwMap.lightSensor.get("lightGround");
        lightGround.enableLed(true);

        //colorBeacon = hwMap.colorSensor.get("colorBeacon");
    }

    public void setDriveTank(double left,double right)
    {
        for(int i=0;i<motors[0].length;i++)
        {
            motors[0][i].setPower(left);
            motors[1][i].setPower(-right);
        }
    }

    public double setTurnDegrees(double degrees)
    {
        double chg=(degrees/360)*18*Math.PI;
        return setTgtPos(chg,-chg,0.8);
    }
    public double setTgtPos(double tgt)
    {
        return setTgtPos(tgt,tgt,1);
    }
    public double setTgtPos(double left,double right){return setTgtPos(left,right,1);}

    public double setTgtPos(double left,double right,double maxPower)
    {
        double leftError=getLeftSide()-(left+motorOffset[0][0]),
                rightError=getRightSide()-(-right+motorOffset[1][0]);
        double leftPower=Math.sqrt(2*Robot.MAX_ENCODER_ACCEL*leftError),
                rightPower=Math.sqrt(2*Robot.MAX_ENCODER_ACCEL*rightError);
        if(leftPower>maxPower)leftPower=maxPower;
        if(rightPower>maxPower)rightPower=maxPower;
        for(int i=0;i<motors[0].length;i++)
        {
            motors[0][i].setPower(leftPower);
            motors[1][i].setPower(rightPower);
        }
        return (leftError+rightError)/2;
    }

    public void resetMotorOffset() {
        for(int i = 0; i < motors.length; i++) {
            for(int j = 0; j < motors[i].length; j++) {
                motorOffset[i][j] = motors[i][j].getCurrentPosition();
            }
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

    public int getLeftSide() {
        return motors[0][0].getCurrentPosition();
    }

    public int getRightSide() {
        return motors[1][0].getCurrentPosition();
    }

    public void disable()
    {
        //ultrasonics.interrupt();
        for(int i=0;i<motors[0].length;i++)
        {
            motors[0][i].setPower(0);
            motors[1][i].setPower(0);
        }
        intake.setPower(0);
        shooter.setPower(0);
    }
}