package org.firstinspires.ftc.teamcode.Core;


import com.qualcomm.robotcore.hardware.DcMotor;


public class Motor147 {
    private DcMotor motor;

    private boolean setPosMode=false;

    private double setPosPower=1;
    private double target;
    private double offset;
    private int pos,lastPos,dPos;
    private double speed;

    public Motor147(String name){

        motor=(Robot.robot.hardwareMap.dcMotor.get(name));
        lastPos=motor.getCurrentPosition();
    }

    public void setSpeed(double speed)
    {
        setMode(false);
        target=speed;
    }
    public void setTargetPosition(double pos)
    {
        setMode(true);
        target=pos;
    }
    public void setPosPower(double power)
    {
        this.setPosPower=power;
    }

    public double getSpeed()
    {
        return speed;
    }
    public double getPos() { return motor.getCurrentPosition()-offset; }
    public double getRawPos() { return motor.getCurrentPosition(); }

    public void adjustOffset(double adjust)
    {
        offset+=adjust;
    }
    public void resetOffset()
    {
        offset=motor.getCurrentPosition();
    }

    public double getError()
    {
        return Math.abs(target-getPos());
    }

    public void setMode(boolean setPos)
    {
        if(setPosMode!=setPos) {
            if (setPos) {
                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motor.setPower(0.0);
            }
            else {
                motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor.setPower(setPosPower);
            }
        }
    }
    public void update()
    {
        lastPos=pos;
        pos=motor.getCurrentPosition();
        dPos=pos-lastPos;
        speed=dPos/ Robot.robot.getMs()*1000;
        if(setPosMode)
            motor.setTargetPosition((int)(target+offset+0.5));
        else
            motor.setPower(target);
    }
}
