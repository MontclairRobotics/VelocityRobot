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
        boolean intaking = false;
        boolean done = false;
        boolean running = true;
        //I am not sure about the specific positions for the intake but i will change these variables eventually.
        int INTAKE_UP = 0;
        int INTAKE_DOWN = 0;
        int INTAKE_HALF = 0;
        while (running) {
            if (pressed && (intake.getPos() < INTAKE_DOWN) && !(done)) {
                intake.setPos(INTAKE_DOWN);
                intaking = true;
            } else if (intaking && (intake.getPos() >= INTAKE_DOWN)) {
                done = true;
            } else if (intaking && done && !(pressed)) {
                intake.setPos(INTAKE_UP);
                intaking = false;
            }else if (!(intaking) && !(pressed)){
                intake.setPos(INTAKE_HALF);
            }
        }
    }
}
