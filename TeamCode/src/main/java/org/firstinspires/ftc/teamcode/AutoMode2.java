package org.firstinspires.ftc.teamcode;


public class AutoMode2 {

    private Auto[] autoModes;
    private int currentState = 0;

    public AutoMode2(Auto... autoModes) {
        this.autoModes = autoModes;
        autoModes[currentState].start();
    }

    public void loop() {
        if(currentState < 0) {
            return;
        }

        if(autoModes[currentState].isDone()) {
            autoModes[currentState].stop();
            if(autoModes.length > currentState) {
                currentState++;
                autoModes[currentState].start();
            } else {
                currentState = -1;
            }
        }

        autoModes[currentState].loop();
    }

}
