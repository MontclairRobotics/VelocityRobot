package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Core.OpMode147;
import org.firstinspires.ftc.teamcode.Core.Updater;

import static org.firstinspires.ftc.teamcode.Core.Robot.robot;

/**
 * Created by MONTCLAIR ROBOTICS on 11/12/2016.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Teleop Competition", group="147")
public class TeleOp extends OpMode147 {
    public void update() {
        robot.driveTrain.setSpeedJoystick();
        robot.intake.setPositionButton(robot.controller.intake());
        robot.intake.getMotor().adjustOffset(robot.controller.intakeOffset()*robot.getMs()*10.0);
        robot.shooter.setPositionButton(robot.controller.shoot());
        robot.shooter.getMotor().adjustOffset(robot.controller.shooterOffset()*robot.getMs()*1.0);

        Updater.update();
    }
}
