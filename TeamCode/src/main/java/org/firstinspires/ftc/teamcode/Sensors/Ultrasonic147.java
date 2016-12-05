package org.firstinspires.ftc.teamcode.Sensors;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * Created by MHS Robotics on 12/4/2016.
 */

public class Ultrasonic147 extends Thread {
    public static double
            DISTANCE_BETWEEN_SENSORS = 11,
            DISTANCE_FROM_B_TO_CENTER = 4,
            B_OFFSET=2;

    public static long MS_BETWEEN_READINGS=100;
    public static int SMOOTH_TIME=10;

    private UltrasonicSensor distASensor,distBSensor;
    private SmoothData distA,distB;
    double ang,dist;

    public Ultrasonic147(HardwareMap ahwMap,String a,String b)
    {
        distASensor=ahwMap.ultrasonicSensor.get(a);
        distBSensor=ahwMap.ultrasonicSensor.get(b);
        distA=new SmoothData(SMOOTH_TIME);
        distB=new SmoothData(SMOOTH_TIME);
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
    private synchronized void updateSensor(UltrasonicSensor sensor,SmoothData data)
    {
        double val=sensor.getUltrasonicLevel();
        if(val>1&&val<60)
        {
            data.add(val);
        }
    }
    private synchronized void updateCalc()
    {
        double a=getDistA();
        double b=getDistB();
        ang=Math.atan2(a-b,DISTANCE_BETWEEN_SENSORS);
        dist=b*Math.cos(ang)-DISTANCE_FROM_B_TO_CENTER*Math.sin(ang);
    }

    public synchronized double getDistA()
    {
        return distB.get();
    }
    public synchronized double getDistB()
    {
        return distB.get()-B_OFFSET;
    }


    public synchronized double getAngle()
    {
        return ang;
    }
    public synchronized double getDist()
    {
        return dist;
    }

}
