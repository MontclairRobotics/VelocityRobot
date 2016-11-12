package org.firstinspires.ftc.teamcode.Core;

/**
 * Created by Hymowitz on 11/12/2016.
 */

public class Utils {
    public static double constrain(double a,double min,double max)
    {
        if(a<min)return min;
        if(a>max)return max;
        return a;
    }
}
