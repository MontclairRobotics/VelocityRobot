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

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
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

@Autonomous(name="Auto Drive LEFT And Shoot 2", group="147")
public class AutoDriveLeftAndShoot2 extends AutoMode {
    int
            TARGET_DRIVE_0=25,//25 forward 45 degrees left 6 forward shoot forward 20
            TARGET_TURN_1=-45,
            TARGET_DRIVE_2=14,
            TARGET_DRIVE_3=12;

    double
            deg0 = TARGET_DRIVE_0*DEGREES_PER_INCH,
            deg2 = TARGET_DRIVE_2*DEGREES_PER_INCH,
            deg3 = TARGET_DRIVE_3*DEGREES_PER_INCH;

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        robot.intake.setTargetPosition(-500);
        switch (state) {
            case 0: //Move forward
                diff = robot.setTgtPos(deg0);
                checkStateCompletion(diff < TOLERANCE);
                break;
            case 1: //Turn 45 left
                diff = robot.setTurnDegrees(TARGET_TURN_1*DEGREES_PER_INCH);
                checkStateCompletion(diff < TOLERANCE);
                break;
            case 2: //Move forward
                diff = robot.setTgtPos(deg2);
                checkStateCompletion(diff < TOLERANCE);
                break;
            case 3: //Shoot
                robot.shooter.setTargetPosition(1300);
                checkStateCompletion(robot.shooter.getCurrentPosition() >= 1300 - 100);
                break;
            case 4: //brings intake down at half speed
                robot.intake.setPower(0.25);
                robot.intake.setTargetPosition(-1500);
                robot.shooter.setTargetPosition(0);
                checkStateCompletion(robot.intake.getCurrentPosition() <= -1400 && robot.intake.getCurrentPosition() >= -100);
                break;
            case 5:
                robot.intake.setPower(0.5);
                robot.intake.setTargetPosition(0);
                checkStateCompletion(robot.intake.getCurrentPosition() >= -100);
                break;
            case 6: // brings it back down
                robot.intake.setTargetPosition(-500);
                checkStateCompletion(robot.intake.getCurrentPosition() <= -400);
                break;
            case 7:
                robot.shooter.setTargetPosition(1300);
                checkStateCompletion(robot.shooter.getCurrentPosition() >= 1200);
            case 8: //Push ball off
                diff = robot.setTgtPos(deg3);
                robot.shooter.setTargetPosition(0);
                break;
        }
        telemetry.addData("state",state);
        telemetry.addData("diff",diff);
        telemetry.addData("Say","Auto enabled: watch out!");
        updateTelemetry(telemetry);
    }
}
