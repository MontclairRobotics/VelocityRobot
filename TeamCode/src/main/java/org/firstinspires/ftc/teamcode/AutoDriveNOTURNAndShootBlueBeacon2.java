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

@Autonomous(name="Auto Shoot BLUE beacon 2", group="147")
public class AutoDriveNOTURNAndShootBlueBeacon2 extends AutoMode {
    double
            DRIVE_0=    AUTO_BEACON_DRIVE_0,
            TURN_1=     AUTO_BEACON_TURN_1,
            DRIVE_2=    AUTO_BEACON_DRIVE_2,
            TURN_3=     AUTO_BEACON_TURN_3,
            DRIVE_4=    AUTO_BEACON_DRIVE_4,

            BEACON_TIME_0=   BEACON_SUB_TIME_0,
            BEACON_DRIVE_1=  BEACON_SUB_DRIVE_1,
            BEACON_TURN_2=   180-BEACON_SUB_TURN_2,
            BEACON_DRIVE_3=  -BEACON_SUB_DRIVE_3,
            BEACON_TURN_4=   -BEACON_SUB_TURN_4,
            BEACON_DRIVE_5=  -BEACON_SUB_DRIVE_5,
            BEACON_TURN_6=   -BEACON_SUB_TURN_6,
            BEACON_DRIVE_7=  -BEACON_SUB_DRIVE_7;

    int state2 = 0;

    @Override
    public void loop() {
        if(state > 5) {
            robot.intake.setTargetPosition(TeleopCompetition.INTAKE_UP_POS);
            robot.shooter.setTargetPosition(TeleopCompetition.SHOOTER_DOWN_POS);
        }
        switch (state) {
            case 0:
                intakeHalf();
            case 1:
                shootUpAndIntakeThird();
                break;
            case 2: //Shoot
                shootDownAndIntakeThird();
                break;
            case 3:
                intakeUp();
                break;
            case 4:
                intakeHalf();
                break;
            case 5:
                shootUp();
                break;
            case 6:
                drive(DRIVE_0);
                break;
            case 7:
                turn(TURN_1);
                break;
            case 8:
                drive(DRIVE_2);
                break;
            case 9:
                turn(TURN_3);
                break;
            case 10:
                drive(DRIVE_4);
                break;
            case 11:
                driveToWall(BEACON_TIME_0);
                break;
            case 12:
                drive(BEACON_DRIVE_1);
                break;
            case 13:
                turn(BEACON_TURN_2);
                break;
            case 14:
                drive(BEACON_DRIVE_3);
                break;
            case 15:
                driveToBeacon();
                break;
            case 16:
                getBeaconColor();
                delay(0.5);
                robot.setTgtPos(0);
                break;
            case 17:
                drive(getBeaconDriveDist(BEACON.BLUE));
                break;
            case 18:
                pressBeacon();
                break;
            case 19:
                unpressBeacon();
                break;
            case 20:
                turn(BEACON_TURN_4);
                break;
            case 21:
                drive(BEACON_DRIVE_5);
                break;
            case 22:
                driveToBeaconBackwards();
                break;
            case 23:
                getBeaconColor();
                delay(0.5);
                robot.setTgtPos(0);
                break;
            case 24:
                drive(getBeaconDriveDist(BEACON.BLUE));
                break;
            case 25:
                pressBeacon();
                break;
            case 26:
                unpressBeacon();
                break;
            case 27:
                turn(BEACON_TURN_6);
                break;
            case 28:
                drive(BEACON_DRIVE_7);
                break;

        }

        /*switch(state2) {
            //TODO: Maybe?
        }*/
        telemetry.addData("color", beaconColor ? "red blue" : "blue red");
        telemetry.addData("state",state);
        telemetry.addData("diff",diff);
        telemetry.addData("Say","Auto enabled: watch out!");
        updateTelemetry(telemetry);
    }
}
