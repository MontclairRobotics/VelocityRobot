package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;


public class Motor147 {
    private DcMotor motor;

    public Motor147(String name, HardwareMap map){
        motor = map.dcMotor.get(name);
    }
    public void setSpeed(double speed){
        motor.setPower(speed);
    }
    public double getPos()
    {
        return motor.getCurrentPosition();
    }
    public void setPos(int pos){
        motor.setTargetPosition(pos);
    }
}
