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

@TeleOp(name="Teleop Test PID", group="147")
public class TeleopTestPID extends OpMode{

    public static double DISTANCE_FROM_WALL=20;

    /* Declare OpMode members. */
    Hardware147CompetitionSensors hardware = new Hardware147CompetitionSensors(); // use the class created to define a Pushbot's hardware
    ControllerPID ctrl = new ControllerPID();
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

    String dp="%.2f";

    boolean distMode;
    PID turnPID=new PID(0.3,0,0.0003),distPID=new PID(0.1,0,0.1);
    double CHG=0.01;
    double angle,dist,sec,tgtTurn;

    double lastPower,lastTurn;

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
        sec=time.seconds();
        ms=time.milliseconds();
        power=ctrl.getPower();
        turn=ctrl.getTurn()*TURN_SPD;

        power *= HALF_POWER;

        if(ctrl.activateDistPID())
        {
            distMode=true;
        }
        if(ctrl.activateTurnPID()) {
            distMode = false;
        }
        if(distMode)
        {
            if(ctrl.pUp()) { distPID.setP(distPID.getP()+CHG*sec); }
            if(ctrl.pDown()) { distPID.setP(distPID.getP()-CHG*sec); }
            if(ctrl.iUp()) { distPID.setI(distPID.getI()+CHG*sec); }
            if(ctrl.iDown()) { distPID.setI(distPID.getI()-CHG*sec); }
            if(ctrl.dUp()) { distPID.setD(distPID.getD()+CHG*sec); }
            if(ctrl.dDown()) { distPID.setD(distPID.getD()-CHG*sec); }
        }
        else
        {
            if(ctrl.pUp()) { turnPID.setP(turnPID.getP()+CHG*sec); }
            if(ctrl.pDown()) { turnPID.setP(turnPID.getP()-CHG*sec); }
            if(ctrl.iUp()) { turnPID.setI(turnPID.getI()+CHG*sec); }
            if(ctrl.iDown()) { turnPID.setI(turnPID.getI()-CHG*sec); }
            if(ctrl.dUp()) { turnPID.setD(turnPID.getD()+CHG*sec); }
            if(ctrl.dDown()) { turnPID.setD(turnPID.getD()-CHG*sec); }
        }


        angle=hardware.ultrasonics.getAngle();
        dist=hardware.ultrasonics.getDist();

        if(ctrl.activateTurnPID())
        {
            turnPID.setTarget(0);
            turnPID.update(angle,sec);
            power=(int)(power*2)*0.3;
            turn=turnPID.get();
        }
        else if(ctrl.activateDistPID())
        {
            power=(power<0?-0.3:.3);
            distPID.setTarget(DISTANCE_FROM_WALL);
            distPID.update(dist,sec);
            tgtTurn=distPID.get();
            turnPID.setTarget(constrainTurn(tgtTurn)*(power>0?1:-1));
            turnPID.update(angle,sec);
            turn=turnPID.get();
        }



/*
        power=constrain(power,lastPower-MAX_ACCEL*ms,lastPower+MAX_ACCEL*ms);
        turn=constrain(turn,lastTurn-TURN_ACCEL*ms,lastTurn+TURN_ACCEL*ms);
        lastPower=power;
        lastTurn=turn;*/
        hardware.setDriveTank(power+turn,power-turn);
        

        //telemetry.addData("say","teleop mode enabled");
        telemetry.addData("power", dp , power);
        telemetry.addData("tgtTurn",dp, tgtTurn);
        telemetry.addData("turn", dp, turn);
        telemetry.addData("turnPIDS",""+turnPID.getP()+","+turnPID.getI()+","+turnPID.getD());
        telemetry.addData("distPIDS",""+distPID.getP()+","+distPID.getI()+","+distPID.getD());
        telemetry.addData("angle",dp,Math.toDegrees(angle));
        telemetry.addData("distance",dp,dist);
        telemetry.addData("sensorA",dp,hardware.ultrasonics.getDistA());
        telemetry.addData("sensorB",dp,hardware.ultrasonics.getDistB());
        telemetry.addData("lightSensor",dp,hardware.lightGround.getLightDetected());
        telemetry.addData("lightSensorRaw",dp,hardware.lightGround.getRawLightDetected());
        telemetry.addData("lightSensorMax",dp,hardware.lightGround.getRawLightDetectedMax());


        telemetry.addData("beacon",dp,hardware.lightBeacon.getLightDetected());
        telemetry.addData("beaconRaw",dp,hardware.lightBeacon.getRawLightDetected());
        telemetry.addData("beaconMax",dp,hardware.lightBeacon.getRawLightDetectedMax());
        

        telemetry.addData("sec per cycle",sec);
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
    public double constrainTurn(double val)
    {
        return constrain(val,-Math.toRadians(10),Math.toRadians(10));
    }
}
