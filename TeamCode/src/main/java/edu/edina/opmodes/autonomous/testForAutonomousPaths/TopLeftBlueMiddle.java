package edu.edina.opmodes.autonomous.testForAutonomousPaths;

//@Autonomous(name = "TopLeftBlueMiddle", group = "Autonomous")
public class TopLeftBlueMiddle extends AutonomousDriveMain {
    public boolean isParkPositionStraight = false;
    public boolean isAllianceLeftBlue = true;
    public boolean isPositionTop = true;
    public boolean includeDelay = false;

    public void start(){
        super.isAllianceLeftBlueMain = isAllianceLeftBlue;
        super.isParkPositionStraightMain = isParkPositionStraight;
        super.isPositionTopMain = isPositionTop;
        super.includeDelayMain = includeDelay;
        super.start();
    }
}
