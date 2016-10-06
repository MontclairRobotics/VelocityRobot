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
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Teleop Joystick Shooter", group="147")
public class TeleopJoystickShooter extends OpMode{

    /* Declare OpMode members. */
    Hardware147Shooter robot       = new Hardware147Shooter(); // use the class created to define a Pushbot's hardware
    ElapsedTime time=new ElapsedTime();

    double MAX_ACCEL=20.0/1000,TURN_ACCEL=10.0/1000;

    double HALF=0.4,QUARTER=0.2,TURN_SPD=0.5,SMALL_TURN_SPD=0.5;

    double lastPower=0,lastTurn=0;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "SETUP COMPLETE");    //
        updateTelemetry(telemetry);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        double power,turn;
        double ms;

        ms=time.milliseconds();
        power=-gamepad1.left_stick_y;
        turn=gamepad1.right_stick_x*TURN_SPD;

        if(gamepad1.right_bumper) {
            power *= 1;
            power += gamepad1.left_stick_x * SMALL_TURN_SPD;
        }
        else if(gamepad1.left_bumper) {
            power *= QUARTER;
        }
        else {
            power *= HALF;
        }
        power=constrain(power,lastPower-MAX_ACCEL*ms,lastPower+MAX_ACCEL*ms);
        turn=constrain(turn,lastTurn-TURN_ACCEL*ms,lastTurn+TURN_ACCEL*ms);

        robot.leftMotor.setPower(power+turn);
        robot.rightMotor.setPower(-power+turn);


        if(gamepad1.a)
        {
            robot.shooter.setPower(1);
        }
        else if(gamepad1.b)
        {
            robot.shooter.setPower(-1);
        }
        else
        {
            robot.shooter.setPower(0);
        }

        telemetry.addData("power",  "%.2f", power);
        telemetry.addData("turn", "%.2f", turn);
        telemetry.addData("ms","%.2f",ms);
        updateTelemetry(telemetry);

        lastPower=power;
        lastTurn=turn;
        time.reset();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }


    public double constrain(double val,double min,double max)
    {
        if(val<min)return min;
        if(val>max)return max;
        return val;
    }
}
