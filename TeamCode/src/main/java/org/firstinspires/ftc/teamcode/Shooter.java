package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Core.Motor147;
import org.firstinspires.ftc.teamcode.Core.SubSystem;

/**
 * Created by Hymowitz on 11/12/2016.
 */
public class Shooter implements SubSystem {

    public static final int SHOOTER_DOWN_POS=0,
        SHOOTER_UP_POS=1300,
        INTAKE_AWAY_TOLERANCE=100;

    private Motor147 shooter;

    public Shooter(String shooter) {
        this.shooter=new Motor147(shooter);
    }

    public Motor147 getMotor()
    {
        return shooter;
    }

    public void setPositionButton(boolean button)
    {

    }
    public void update()
    {

    }
}
