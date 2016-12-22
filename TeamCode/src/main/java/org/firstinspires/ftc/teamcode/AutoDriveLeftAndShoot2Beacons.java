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

@Autonomous(name="Z_DONT USE Auto Drive LEFT And Shoot 2 Beacons", group="147")
public class AutoDriveLeftAndShoot2Beacons extends AutoMode {
    int
            TARGET_DRIVE_0=AUTO_DRIVE_TURN_SHOOT_0,//25 forward 45 degrees left 6 forward shoot forward 20
            TARGET_TURN_1=-AUTO_DRIVE_TURN_SHOOT_1_TURN,
            TARGET_DRIVE_2=AUTO_DRIVE_TURN_SHOOT_2-1,
            TARGET_DRIVE_3=AUTO_DRIVE_TURN_SHOOT_3+1;
    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        //robot.intake.setTargetPosition(-500);
        switch (state) {
            case 0: //brings intake down at half speed
                intakeDownSlow();
                break;
            case 1:
                intakeThird();
                break;
            case 2: //Move forward
                drive(TARGET_DRIVE_0);
                break;
            case 3: //Turn 45 left
                turn(TARGET_TURN_1);
                break;
            case 4: //Move forward
                drive(TARGET_DRIVE_2);
                break;
            case 5:
                delay(4);
                break;
            case 6: //Shoot
                shootUp();
                break;
            case 7: //prep to reload
                shootDown();
                break;
            case 8:
                intakeUp();
                break;
            case 9:
                intakeHalf();
            case 10:
                delay(4);
                break;
            case 11://Shoot again
                shootUp();
                break;
            case 12://reload shooter
                shootDown();
                break;
            case 13:
                intakeUp();
                break;
            case 14://Drive To Wall
                drive(10);
                break;
            case 15:
                turn(90);
                break;
            case 16:
                turnToWall();
                break;
            case 17:
                driveToBeaconBackwards();
                break;
            case 18:
                getBeaconColor();
                checkStateCompletion(true);
            case 19:
                drive(getBeaconDriveDist(BEACON.RED));
                break;
            case 20:
                pressBeacon();
                break;
            case 21:
                unpressBeacon();
                break;
            case 22:
                turn(15);
                break;
            case 23:
                drive(5);
                break;
            case 24:
                turn(75);
                break;
            case 25: //Push ball off
                drive(TARGET_DRIVE_3);
                break;
        }
        telemetry.addData("state",state);
        telemetry.addData("diff",diff);
        telemetry.addData("Say","Auto enabled: watch out!");
        updateTelemetry(telemetry);
    }
}