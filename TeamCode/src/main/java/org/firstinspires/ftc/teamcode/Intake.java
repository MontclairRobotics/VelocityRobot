package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Garrett on 11/9/16.
 */

public class Intake {
    private Motor147 intake;
    HardwareMap hwMap;

    public Intake(Motor147 aux_a) {
        intake = new Motor147("aux_a", hwMap);
    }

    public void controlIntake(boolean pressed) {
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
