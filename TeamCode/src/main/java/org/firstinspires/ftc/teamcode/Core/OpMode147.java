package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import static org.firstinspires.ftc.teamcode.Core.Robot.robot;

/**
 * Created by MONTCLAIR ROBOTICS on 11/11/2016.
 */

public class OpMode147 extends OpMode {
    @Override
    public final void init()
    {
        Robot.init(hardwareMap,gamepad1,gamepad2,telemetry);
        user_init();
    }
    public void user_init(){}

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }

    @Override
    public final void loop() {
        update();
        robot.update();
    }
    public void update(){}

    @Override
    public void stop() {
        telemetry.addData("say","Disabled");
        updateTelemetry(telemetry);
    }
}
