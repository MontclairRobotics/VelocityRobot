/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * This file provides basic Telop driving for a Pushbot robot.
 * The code is structured as an Iterative OpMode
 *
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 *
 * This particular OpMode executes a basic Tank Drive Teleop for a PushBot
 * It raises and lowers the claw using the Gamepad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Teleop Competition", group="147")
public class TeleopCompetition extends Robot{

    boolean intaking=false;
    boolean invertPressed = false;
    boolean inverted = false;

    double
            //drive variables
            lastPower = 0,
            lastTurn = 0;

    @Override
    public void update() {
        //==========DRIVE==========
        double power,turn;
        power=ctrl.getPower();
        turn=ctrl.getTurn()*TURN_SPD;

        if(ctrl.highSpeed()) {
            power *= HIGH_POWER;
            turn *= HIGH_TURN_SPD_FACTOR;
            turn += ctrl.getSmallTurn() * SMALL_TURN_SPD;
        }
        else if(ctrl.lowSpeed()) {
            power *= LOW_POWER;
        }
        else {
            power *= HALF_POWER;
        }
        if(inverted) {
            power *= -1;
        }
        power=constrainChange(power,lastPower,MAX_ACCEL*secInLoop);
        turn=constrainChange(turn,lastTurn,TURN_ACCEL*secInLoop);
        lastPower=power;
        lastTurn=turn;
        hardware.setDriveTank(power+turn,power-turn);
        //==========Intake==========
        if(ctrl.intake()) {
            intakePos = INTAKE_DOWN_POS;
            hardware.intake.setPower(1);
            intaking=true;
        }
        else if (intaking)
        {
            intakePos=INTAKE_UP_POS;
            hardware.intake.setPower(0.35);
            if(hardware.intake.getCurrentPosition()<=INTAKE_UP_POS+INTAKE_AWAY_TOLERANCE)//todo: Flip
            {
                intaking=false;
            }
        }
        else
        {
            intakePos=INTAKE_HALF_POS;
            hardware.intake.setPower(0.35);
        }

        if(ctrl.intakeFull()) {
            intakePos=INTAKE_UP_POS;
            hardware.intake.setPower(0.35);
        }

        //==========Shooter==========
        shooterPos=SHOOTER_DOWN_POS;
        if(ctrl.shoot())
        {
            if(hardware.intake.getCurrentPosition()>INTAKE_HALF_POS+INTAKE_AWAY_TOLERANCE)//TODO: flip if the intake positions are negative
                intakePos=INTAKE_HALF_POS;
            else
                shooterPos=SHOOTER_UP_POS;
        }

        //===Manual Reset===
        if(ctrl.shooterUp())
        {
            hardware.shooterOffset+=1000*secInLoop;
        }
        if(ctrl.shooterDown())
        {
            hardware.shooterOffset-=1000*secInLoop;
        }

        if(ctrl.intakeUp())
        {
            hardware.intakeOffset+=1*1000*secInLoop;
        }
        if(ctrl.intakeDown())
        {
            hardware.intakeOffset-=1*1000*secInLoop;
        }
        if(ctrl.shoot.back)
        {
            hardware.intakeOffset=0;
        }

        if(!invertPressed && ctrl.invertDrive()) {
            inverted = !inverted;
        }
        invertPressed = ctrl.invertDrive();



    }
}
