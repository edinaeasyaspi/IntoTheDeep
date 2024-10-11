package edu.edina.opmodes.autonomous.testForAutonomousPaths;

//@Autonomous(name = "BottomLeftBlueMiddle", group = "Autonomous")
public class BottomLeftBlueStraight extends AutonomousDriveMain {
    public boolean isParkPositionStraight = true;
    public boolean isAllianceLeftBlue = true;
    public boolean isPositionTop = false;
    public boolean includeDelay = false;

    public void start(){
        super.isAllianceLeftBlueMain = isAllianceLeftBlue;
        super.isParkPositionStraightMain = isParkPositionStraight;
        super.isPositionTopMain = isPositionTop;
        super.includeDelayMain = includeDelay;
        super.start();
    }
}
