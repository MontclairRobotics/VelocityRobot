package org.firstinspires.ftc.teamcode;

public abstract class Auto {

    public static final double DEGREES_PER_INCH = 10000/85;
    public static final double TOLERANCE = 0.5*DEGREES_PER_INCH;

    Hardware147CompetitionAuto1 hardware;

    public Auto(Hardware147CompetitionAuto1 hardware) {
        this.hardware = hardware;
    }

    public abstract void start();

    public abstract void loop();

    public abstract void stop();

    public abstract boolean isDone();

}
