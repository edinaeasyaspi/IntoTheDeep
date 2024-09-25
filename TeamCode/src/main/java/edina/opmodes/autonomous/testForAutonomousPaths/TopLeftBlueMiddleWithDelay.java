package edina.opmodes.autonomous.testForAutonomousPaths;

//@Autonomous(name = "TopLeftBlueMiddleWithDelay", group = "Autonomous")
public class TopLeftBlueMiddleWithDelay extends AutonomousDriveMain {
    public boolean isParkPositionStraight = false;
    public boolean isAllianceLeftBlue = true;
    public boolean isPositionTop = true;
    public boolean includeDelay = true;

    public void start(){
        super.isAllianceLeftBlueMain = isAllianceLeftBlue;
        super.isParkPositionStraightMain = isParkPositionStraight;
        super.isPositionTopMain = isPositionTop;
        super.includeDelayMain = includeDelay;
        super.start();
    }
}
