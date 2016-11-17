package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;


/**
 * Created by MONTCLAIR ROBOTICS on 11/11/2016.
 */

public class OpMode147 extends OpMode {
    @Override
    public final void init()
    {
        telemetry.addData("SAY",1);
        telemetry.update();
        Robot.init(hardwareMap,gamepad1,gamepad2,telemetry);
        telemetry.addData("SAY",2);
        telemetry.update();
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
        Robot.robot.update();
        Updater.update();
    }
    public void update(){}

    @Override
    public void stop() {
        telemetry.addData("say","Disabled");
        updateTelemetry(telemetry);
    }
}
