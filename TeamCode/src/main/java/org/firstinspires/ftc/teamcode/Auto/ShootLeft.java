package org.firstinspires.ftc.teamcode.Auto;

/**
 * Created by Hymowitz on 11/12/2016.
 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Shoot Left", group="147")
public class ShootLeft extends AutoOpMode{
    public ShootLeft()
    {
        super(new Drive(25),//25 forward 45 degrees left 6 forward shoot forward 20
                new Turn(-45),
                new Drive(14),
                new Shoot(),
                new Drive(12));
    }
}
