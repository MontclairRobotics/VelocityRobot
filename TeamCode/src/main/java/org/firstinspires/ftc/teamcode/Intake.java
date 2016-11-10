package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Garrett on 11/9/16.
 */

public class Intake {
    private Motor147 intake;
    HardwareMap hwMap;
    public Intake(Motor147 aux_a){
        intake = new Motor147("aux_a", hwMap);
    }
    public void controlIntake(boolean pressed){
        boolean intaking = false;
        boolean done = false;
        if (pressed && (intake.getPos() < 180) && !(done)){
            intake.setPos(180);
            intaking = true;
        }
        else if(intaking && (intake.getPos() > 180)){
            done = true;
        }
        else if(intaking && done && !(pressed)){
            intake.setPos();

        }
    }
}
