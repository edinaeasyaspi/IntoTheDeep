package edina.opmodes.autonomous.testForAutonomousPaths;

//@Autonomous(name = "TopLeftBlueStraightWithDelay", group = "Autonomous")
public class TopLeftBlueStraightWithDelay extends AutonomousDriveMain {
    public boolean isParkPositionStraight = true;
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