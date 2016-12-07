package org.firstinspires.ftc.teamcode;

/**
 * Created by MHS Robotics on 11/29/2016.
 *
 * Uses WPI math still
 */

public class PID {
    private final double P_GAIN = 5.0, I_GAIN = 3.0, D_GAIN = 3.0;
    private double p,i,d;
    private double minOut=-1,maxOut=1;
    private double target,totalError,error,lastError,out;

    public PID() {}

    public PID(double p,double i,double d)
    {
        setPID(p,i,d);
    }

    public PID setPID(double p,double i,double d) {
        this.p=p;
        this.i=i;
        this.d=d;
        return this;
    }

    public void setTarget(double t)
    {
        target=t;
    }

    public double update(double val,double sec)
    {
        error = target - val;
        totalError += error*sec;
        if(sec>1)
        {
            totalError=error;
            lastError=error;
        }
        if (i != 0) {
            double iGain = totalError * i;
            if (iGain < minOut) {
                totalError = minOut / i;
            } else if (iGain > maxOut) {
                totalError = maxOut / i;
            }
        }
        out = p * error + i * totalError + d * (error - lastError)/sec;
        lastError = error;
        return out;
    }
    public double get()
    {
        return out;
    }

    public void setP(double p){
        this.p=p;
    }
    public double getP()
    {
        return p;
    }
    public void setI(double i){
        this.i=i;
    }
    public double getI()
    {
        return i;
    }
    public void setD(double d){
        this.d=d;
    }
    public double getD()
    {
        return d;
    }
}