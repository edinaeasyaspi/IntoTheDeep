package edina.opmodes.teleop.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import edina.library.util.drivecontrol.DriveDirection;

@Disabled
@Autonomous
public class DiagonalTestMode extends DriveTestMode {
    public DiagonalTestMode() {
        dir = DriveDirection.Diagonal;
    }
}
