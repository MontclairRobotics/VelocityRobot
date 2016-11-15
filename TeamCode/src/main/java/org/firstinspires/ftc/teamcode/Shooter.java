package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Core.Motor147;
import org.firstinspires.ftc.teamcode.Core.SubSystem;
import org.firstinspires.ftc.teamcode.Core.Updatable;
import org.firstinspires.ftc.teamcode.Core.Updater;

/**
 * Created by Hymowitz on 11/12/2016.
 */
public class Shooter implements SubSystem, Updatable {

    public static final int SHOOTER_DOWN_POS=0,
        SHOOTER_UP_POS=1300,
        INTAKE_AWAY_TOLERANCE=100;

    public static int targetPosition;

    private Motor147 shooter;

    public Shooter(String shooter) {
        this.shooter = new Motor147(shooter);
        Updater.addUpdatable(this);
    }

    public Motor147 getMotor() {
        return shooter;
    }

    public void shoot() {
        targetPosition = SHOOTER_UP_POS;
    }

    public void reload() {
        targetPosition = SHOOTER_DOWN_POS;
    }

    public void setPositionButton(boolean button) {
        if (button)
        {
            shoot();
        }
        else
        {
            reload();
        }
    }

    @Override
    public void update() {
        shooter.setTargetPosition(targetPosition);
    }
}