package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * Created by MHS Robotics on 12/4/2016.
 */

public class Ultrasonic147 extends Thread {
    public static double
            DISTANCE_BETWEEN_SENSORS = 11*2.54,
            B_TO_CENTER_OF_SIDE = 4*2.54,
            B_TO_MIDLINE=6*2.54,
            B_OFFSET=2;

    public static long MS_BETWEEN_READINGS=100;
    public static int SMOOTH_TIME=5;

    private Object edit=new Object();

    private UltrasonicSensor distASensor,distBSensor;
    private SmoothData distA,distB;
    double ang,dist;

    public Ultrasonic147(HardwareMap ahwMap,String a,String b)
    {
        distASensor=ahwMap.ultrasonicSensor.get(a);
        distBSensor=ahwMap.ultrasonicSensor.get(b);
        distA=new SmoothData(SMOOTH_TIME);
        distB=new SmoothData(SMOOTH_TIME);
        this.start();
    }
    public void run()
    {
        try
        {
            sleep(200,0);
            while(true)
            {
                updateSensor(distASensor,distA);
                sleep(MS_BETWEEN_READINGS,0);
                updateSensor(distBSensor,distB);
                updateCalc();
                sleep(MS_BETWEEN_READINGS,0);
            }
        }
        catch (InterruptedException e)
        {
            return;
        }
    }
    private void updateSensor(UltrasonicSensor sensor,SmoothData data)
    {
        double val=sensor.getUltrasonicLevel();
        if(val>1&&val<60)
        {
            synchronized (data) {
                data.add(val);
            }
        }
    }
    private void updateCalc()
    {
        double a=getDistA();
        double b=getDistB();
        synchronized (edit) {
            ang = Math.atan2(b - a - B_OFFSET, DISTANCE_BETWEEN_SENSORS);
            dist = (b+B_TO_MIDLINE) * Math.cos(ang) + B_TO_CENTER_OF_SIDE * Math.sin(ang);
        }
    }

    public double getDistA()
    {
        synchronized (distA)
        {
            return distA.get();
        }
    }
    public double getDistB()
    {
        synchronized (distB)
        {
            return distB.get();
        }
    }


    public double getAngle()
    {
        synchronized (edit) {
            return ang;
        }
    }
    public double getDist()
    {
        synchronized (edit)
        {
            return dist;
        }
    }

}
