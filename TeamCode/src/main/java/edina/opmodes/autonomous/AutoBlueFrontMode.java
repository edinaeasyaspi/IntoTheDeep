package edina.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import edina.library.util.Position;

@Autonomous
public class AutoBlueFrontMode extends AutoFrontMode {
    public AutoBlueFrontMode() {
        super(false, new Position(0, 36, -90));
    }
}
