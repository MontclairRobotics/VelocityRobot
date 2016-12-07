package org.firstinspires.ftc.teamcode.Sensors;

/**
 * Created by MHS Robotics on 2/12/16.
 */

public class SmoothData
{
    private double total=0;
    private int full=1;//no division by 0
    private int i=0;
    private double[] history;

    public SmoothData (int len)
    {
        history=new double[len];
    }

    public void add(double data)
    {
        total+=data-history[i];
        history[i]=data;
        i++;
        if(i>full)
            full=i;
        i=i%history.length;
    }
    public double get()
    {
        return total/full;
    }
}
