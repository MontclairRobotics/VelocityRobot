package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Core.OpMode147;

import static org.firstinspires.ftc.teamcode.Core.Robot.controller;
import static org.firstinspires.ftc.teamcode.Core.Robot.robot;

/**
 * Created by MONTCLAIR ROBOTICS on 11/12/2016.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Teleop Competition", group="147")
public class TeleOp extends OpMode147 {
    public void update() {
        robot.driveTrain.setSpeedJoystick();
        robot.intake.setPositionButton(controller.intake());
        robot.intake.getMotor().adjustOffset(controller.intakeOffset()*robot.getMs()*10.0);
        robot.shooter.setPositionButton(controller.shoot());
        robot.shooter.getMotor().adjustOffset(controller.shooterOffset()*robot.getMs()*1.0);

    }
}
