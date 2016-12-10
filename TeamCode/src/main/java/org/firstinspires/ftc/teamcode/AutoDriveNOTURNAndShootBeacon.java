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

@Autonomous(name="Auto Drive Shoot RED BEACON", group="147")
public class AutoDriveNOTURNAndShootBeacon extends AutoMode {
    int
            TARGET_DRIVE_0=AUTO_DRIVE_SHOOT_0,//25 forward 45 degrees left 6 forward shoot forward 20
            TARGET_DRIVE_3=AUTO_DRIVE_SHOOT_1,
            TARGET_DRIVE_4=AUTO_DRIVE_SHOOT_2;

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */

    int state2 = 0;

    @Override
    public void loop() {
        if(state > 4) {
            robot.intake.setTargetPosition(TeleopCompetition.INTAKE_UP_POS);
        }
        switch (state) {
            case 0:
                intakeHalf();
                break;
            case 1: //Shoot
                shootUp();
                break;
            case 2:
                shootDown();
                break;
            case 3:
                state++;
            case 4:
                drive(18);
                break;
            case 5:
                turn(-40);
                break;
            case 6:
                drive(64.5);
                break;
            case 7:
                turn(-40);
                break;
            case 8:
                drive(4);
                break;
            case 9:
                driveToWall(3);
                break;
            case 10:
                drive(-4.25);
                break;
            case 11:
                turn(-80);
                break;
            case 12:
                driveToBeaconBackwards();
                break;
            case 13:
                state++;
            case 14:
                getBeaconColor();
                delay(0.5);
                robot.setTgtPos(0);
                //checkStateCompletion(true);
            case 15:
                drive(getBeaconDriveDist(BEACON.RED));
                break;
            case 16:
                pressBeacon();
                break;
            case 17:
                unpressBeacon();
                break;
            case 18:
                drive(24);
                break;
            case 19:
                driveToBeacon();
                break;
            case 20:
                getBeaconColor();
                delay(0.5);
                robot.setTgtPos(0);
                //checkStateCompletion(true);
            case 21:
                drive(getBeaconDriveDist(BEACON.RED));
                break;
            case 22:
                pressBeacon();
                break;
            case 23:
                unpressBeacon();
                break;
            case 24:
                turn(-30);
                break;
            case 25:
                drive(30);
                break;

        }

        switch(state2) {
            //TODO: Maybe?
        }
        telemetry.addData("color", beaconColor ? "red" : "blue");
        telemetry.addData("state",state);
        telemetry.addData("diff",diff);
        telemetry.addData("Say","Auto enabled: watch out!");
        updateTelemetry(telemetry);
    }
}
