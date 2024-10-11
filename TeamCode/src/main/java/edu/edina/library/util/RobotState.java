package edu.edina.library.util;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import edu.edina.library.enums.IntakeState;
import edu.edina.library.enums.LiftState;
import edu.edina.library.enums.LiftTipState;

public class RobotState {
    private static RobotState robotState = null;

    // lift properties
    public LiftTipState liftTipState;
    public LiftState liftState;
    public IntakeState intakeState;

    public RobotState() {
        liftTipState = LiftTipState.Vertical;
        liftState = LiftState.Idle;
        intakeState = IntakeState.Idle;
    }

    public static synchronized RobotState getInstance()
    {
        if (robotState == null) {
            robotState = new RobotState();
        }

        return robotState;
    }

    public void telemetry(Telemetry telemetry, RobotHardware hardware) {

        if (hardware != null) {
        }
    }
}
