package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by Hymowitz on 11/3/2016.
 */
public class ControllerPID {
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

    public boolean activateTurnPID()
    {
        return drive.a;
    }
    public boolean activateDistPID()
    {
        return drive.b;
    }
    public boolean editTurnPID()
    {
        return drive.x;
    }
    public boolean editDistPID()
    {
        return drive.y;
    }
    public boolean pUp()
    {
        return drive.dpad_up;
    }
    public boolean pDown()
    {
        return drive.dpad_up;
    }
    public boolean iUp()
    {
        return drive.left_bumper;
    }
    public boolean iDown()
    {
        return drive.right_bumper;
    }
    public boolean dUp()
    {
        return drive.dpad_left;
    }
    public boolean dDown()
    {
        return drive.dpad_right;
    }
}
