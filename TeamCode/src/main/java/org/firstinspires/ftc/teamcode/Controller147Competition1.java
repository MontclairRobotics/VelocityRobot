package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by Hymowitz on 11/3/2016.
 */
public class Controller147Competition1 {
    public Gamepad drive=null,shoot=null;

    public void init(Gamepad a, Gamepad b)
    {
        this.drive=a;
        this.shoot=b;
    }
    public float getPower()
    {
        return -drive.left_stick_y;
    }
    public float getTurn()
    {
        return drive.right_stick_x;
    }
    public float getSmallTurn()
    {
        return drive.left_stick_x;
    }
    public boolean lowSpeed()
    {
        return drive.right_bumper;
    }
    public boolean highSpeed()
    {
        return drive.left_bumper;
    }
    public boolean intake()
    {
        return shoot.y;
    }
    public boolean shoot()
    {
        return shoot.a;
    }
    public boolean shooterUp() {return shoot.dpad_up;}
    public boolean shooterDown(){return shoot.dpad_down;}
    public boolean intakeUp(){return shoot.dpad_right;}
    public boolean intakeDown(){return shoot.dpad_left;}
}
