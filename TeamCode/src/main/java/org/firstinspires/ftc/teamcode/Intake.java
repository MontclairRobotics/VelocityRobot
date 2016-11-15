package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Core.Motor147;
import org.firstinspires.ftc.teamcode.Core.SubSystem;

/**
 * Created by Garrett on 11/9/16.
 */

public class Intake implements SubSystem {
    public static final int
            //intake configs
            INTAKE_DOWN_POS=-1500,
            INTAKE_HALF_POS=-500,
            INTAKE_UP_POS=0;


    private boolean intaking=false;

    private Motor147 intake;

    public Intake(String intake) {
        this.intake = new Motor147(intake);
    }

    public Motor147 getMotor()
    {
        return intake;
    }

    public void setDown(){

    }



    public void setPositionButton(boolean pressed) {

    }
    public void update()
    {

    }
}
