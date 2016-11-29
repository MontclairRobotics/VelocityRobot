package org.firstinspires.ftc.teamcode;


public class Drive extends Auto {

    private int distance;

    public Drive(Hardware147CompetitionAuto1 hardware, int distance) {
        super(hardware);
        this.distance = distance;
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        diff = robot.setTgtPos(distance*DEGREES_PER_INCH);
        if(diff > 5*DEGREES_PER_INCH) {
            robot.setPower(0.8);
        } else if(diff > 3*DEGREES_PER_INCH) {
            robot.setPower(0.45);
        } else if(diff > 1*DEGREES_PER_INCH) {
            robot.setPower(0.2);
        } else {
            robot.setPower(0.1);
        }
        checkStateCompletion(diff < TOLERANCE);
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isDone() {
        return false;
    }
}
