package org.firstinspires.ftc.teamcode;


public class Motor147 {
    private DcMotor motor;

    public Motor147(String name, HardwareMap map){
        motor = map.getMotor(name);
    }
    public void setSpeed(double speed){
        motor.setPower(speed);
    }
}
