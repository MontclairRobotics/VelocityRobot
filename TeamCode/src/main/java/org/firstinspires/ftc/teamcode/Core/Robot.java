package org.firstinspires.ftc.teamcode.Core;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Intake;
import org.firstinspires.ftc.teamcode.Shooter;

/**
 * Created by MONTCLAIR ROBOTICS on 11/11/2016.
 */

public class Robot{
    public static Robot robot;
    public static Controller controller;
    public static HardwareMap hardwareMap;
    public ElapsedTime time;
    public Telemetry telemetry;
    public DriveTrain driveTrain;
    public Shooter shooter;
    public Intake intake;
    public SubSystem[] subSystems;

    public double ms;

    private Robot(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry)
    {
        this.hardwareMap=hardwareMap;
        controller=new Controller();
        controller.init(gamepad1,gamepad2);
        this.telemetry=telemetry;
        telemetry.addData("SAY",5);
        telemetry.update();
        time=new ElapsedTime();
        /*driveTrain=new DriveTrain(Config.LEFT_MOTORS,Config.RIGHT_MOTORS);
        intake=new Intake(Config.INTAKE);
        shooter=new Shooter(Config.SHOOTER);
        telemetry.addData("SAY",7);
        telemetry.update();
        subSystems=new SubSystem[3];
        subSystems[0]=driveTrain;
        subSystems[1]=intake;
        subSystems[2]=shooter;*/
    }

    public static final void init(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry)
    {
        if(robot==null) {
            robot = new Robot(hardwareMap, gamepad1, gamepad2, telemetry);
        }
    }

    public void update() {
        for(SubSystem subSystem:subSystems)
            subSystem.update();
        time.reset();
    }
    public double getMs()
    {
        return time.milliseconds();
    }
}
