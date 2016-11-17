package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Core.OpMode147;

import static org.firstinspires.ftc.teamcode.Core.Robot.controller;

/**
 * Created by MONTCLAIR ROBOTICS on 11/12/2016.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Teleop Competition", group="147")
public class TeleOp extends OpMode147 {

    DcMotor motor;
    @Override
    public  void user_init()
    {
        motor=hardwareMap.dcMotor.get("aux_1");
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void update() {
        /*robot.driveTrain.setSpeedJoystick();
        robot.intake.setPositionButton(controller.intake());
        robot.intake.getMotor().adjustOffset(controller.intakeOffset()*robot.getMs()*10.0);
        robot.shooter.setPositionButton(controller.shoot());
        robot.shooter.getMotor().adjustOffset(controller.shooterOffset()*robot.getMs()*1.0);
*/
        if(controller.shoot.a)
        {
            motor.setPower(1);
        }
        else if(controller.shoot.b)
        {
            motor.setPower(-1);
        }
        else
        {
            motor.setPower(0);
        }
    }
}
