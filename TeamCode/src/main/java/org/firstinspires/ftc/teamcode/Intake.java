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

    boolean intaking=false;

    private Motor147 intake;

    public Intake(String intake) {
        this.intake = new Motor147(intake);
    }

    public Motor147 getMotor()
    {
        return intake;
    }

    public void update()
    {

    }
    public void setPositionButton(boolean pressed) {
        // I wanted to take the button in as a parameter but i was not sure how to do it, the booleam pressed represents weather the button is pressed or not

        //I am not sure about the specific positions for the intake but i will change these variables later.
        int INTAKE_UP = 0;
        int INTAKE_DOWN = 0;
        int INTAKE_HALF = 0;
        int state = 1;
        boolean intaking = true;
        while (intaking) {
            switch (state) {
                case 1:
                    if (intake.getPos() == INTAKE_DOWN){
                        state = 2;
                    }
                    else if (pressed){
                        intake.setPos(INTAKE_DOWN);
                    }
                    else{
                        intake.setPos(INTAKE_HALF);
                    }
                    break;

                case 2:
                    if (intake.getPos() == INTAKE_UP){
                        state = 3;
                    }
                    else if (!(pressed)){
                        intake.setPos(INTAKE_UP);
                    }
                    else {
                        intake.setPos(INTAKE_DOWN);
                    }
                    break;

                case 3:
                    intake.setPos(INTAKE_HALF);
                    break;

            }
        }
    }
}
