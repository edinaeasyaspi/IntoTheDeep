package edu.edina.opmodes.autonomous.testForAutonomousPaths;

//@Autonomous(name = "TopLeftBlueStraight", group = "Autonomous")
public class TopLeftBlueStraight extends AutonomousDriveMain{
    public boolean isParkPositionStraight = true;
    public boolean isAllianceLeftBlue = true;
    public boolean isPositionTop = true;
    public boolean includeDelay = false;

    public void init(){
        super.isAllianceLeftBlueMain = isAllianceLeftBlue;
        super.isParkPositionStraightMain = isParkPositionStraight;
        super.isPositionTopMain = isPositionTop;
        super.includeDelayMain = includeDelay;
    }

    public void start(){
        super.start();
    }
}
