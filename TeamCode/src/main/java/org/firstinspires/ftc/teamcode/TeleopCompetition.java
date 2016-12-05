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

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

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
public class TeleopCompetition extends OpMode{

    /* Declare OpMode members. */
    Hardware147Competition1 hardware = new Hardware147Competition1(); // use the class created to define a Pushbot's hardware
    Controller147Competition1 ctrl = new Controller147Competition1();
    ElapsedTime time=new ElapsedTime();

    //========================================
    //configs:
    public static final double
            //drive configs
            MAX_ACCEL=20.0/1000,
            TURN_ACCEL=10.0/1000,
            HIGH_POWER=1.0,
            HALF_POWER=0.4,
            LOW_POWER=0.2,
            TURN_SPD=0.5,
            HIGH_TURN_SPD_FACTOR=2.0,
            SMALL_TURN_SPD=0.25;
    public static final int
            //intake configs
            INTAKE_DOWN_POS=1100,
            INTAKE_THIRD_POS=600,
            INTAKE_HALF_POS=400,//325,
            INTAKE_UP_POS=-100,
            //shooter configs
            SHOOTER_DOWN_POS=200,//200,
            SHOOTER_UP_POS=-950,
            //Tolerances
            SHOOTER_AWAY_TOLERANCE=50,
            INTAKE_AWAY_TOLERANCE=100;
    //========================================

    boolean intaking=false;
    boolean invertPressed = false;
    boolean inverted = false;

    String dp="%.2f";
    String ip="%d";
    double
            //drive variables
            lastPower=0,
            lastTurn=0;
    int
            //intake variables
            intakePos=0,
            //shooter variables
            shooterPos=0;

    @Override
    public void init() {
        hardware.init(hardwareMap);
        ctrl.init(gamepad1,gamepad2);

        telemetry.addData("Say", "Don't forget to press START+(A or B)");    //
        updateTelemetry(telemetry);
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        //==========DRIVE==========
        double power,turn;
        double ms;

        ms=time.milliseconds();
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
        power=constrain(power,lastPower-MAX_ACCEL*ms,lastPower+MAX_ACCEL*ms);
        turn=constrain(turn,lastTurn-TURN_ACCEL*ms,lastTurn+TURN_ACCEL*ms);
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
            hardware.shooterOffset+=1*ms;
        }
        if(ctrl.shooterDown())
        {
            hardware.shooterOffset-=1*ms;
        }

        if(ctrl.intakeUp())
        {
            hardware.intakeOffset+=1*ms;
        }
        if(ctrl.intakeDown())
        {
            hardware.intakeOffset-=1*ms;
        }
        if(ctrl.shoot.back)
        {
            hardware.intakeOffset=0;
        }

        if(!invertPressed && ctrl.invertDrive()) {
            inverted = !inverted;
        }
        invertPressed = ctrl.invertDrive();


        hardware.intake.setTargetPosition(intakePos+(int)hardware.intakeOffset);
        if(Math.abs((hardware.shooter.getCurrentPosition()+(int)hardware.shooterOffset)-
                (shooterPos+(int)hardware.shooterOffset)) > SHOOTER_AWAY_TOLERANCE) {
            hardware.shooter.setPower(1.0);
            hardware.shooter.setTargetPosition(shooterPos+(int)hardware.shooterOffset);
            telemetry.addData("shooter", "from target: " + Math.abs((hardware.shooter.getCurrentPosition()+(int)hardware.shooterOffset)-
                    (shooterPos+(int)hardware.shooterOffset)));
        } else {
            telemetry.addData("shooter", "zero power");
            hardware.shooter.setPower(0);
        }

        //telemetry.addData("say","teleop mode enabled");
        //telemetry.addData("power", dp , power);
        //telemetry.addData("turn", dp, turn);
        //telemetry.addData("intake", ip, intakePos);
        //telemetry.addData("intakePosition", ip, hardware.intake.getCurrentPosition());
        //telemetry.addData("shooter", ip, shooterPos);
        //telemetry.addData("shooterPosition", ip, hardware.shooter.getCurrentPosition());
        //telemetry.addData("ms per cycle", dp,ms);
        updateTelemetry(telemetry);

        time.reset();
    }

    @Override
    public void stop() {
        telemetry.addData("say","Disabled");
        updateTelemetry(telemetry);
    }


    public double constrain(double val,double min,double max)
    {
        if(val<min)return min;
        if(val>max)return max;
        return val;
    }
}
