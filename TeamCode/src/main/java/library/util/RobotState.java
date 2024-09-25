package library.util;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import edina.library.enums.IntakeState;
import edina.library.enums.LiftState;
import edina.library.enums.LiftTipState;

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
