package edina.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import edina.library.util.Position;

@Autonomous
public class AutoRedFrontMode extends AutoFrontMode {
    public AutoRedFrontMode() {
        super(true, new Position(144, 36, 90));
    }
}
