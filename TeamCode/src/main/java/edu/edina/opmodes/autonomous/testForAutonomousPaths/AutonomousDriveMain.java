package edu.edina.opmodes.autonomous.testForAutonomousPaths;


public class AutonomousDriveMain   {
    private int verticalDistance = 10; //how much distance from starting point to board, 10 = top distance, bottom distance = 34
    private int horizontalDistance = 10; //how much distance from starting point to middle
    private int delayInSeconds = 0; //how long we want to delay before bot starts moving.
    public boolean isParkPositionStraightMain; //true = goes straight to corner, false = goes farther right or left and goes to middle
    public boolean isAllianceLeftBlueMain; //true = if alliance is blue, false = alliance is red
    public boolean isPositionTopMain; //true = position of robot above truss, false = below truss
    public boolean includeDelayMain; //true = delay is needed meaning starts seconds after, false = starts immediately

public void init() {
    // Update the verticalDistance based on isPositionTop
    if (isPositionTopMain = false) {
       verticalDistance = verticalDistance + 24;
    }

    if (isParkPositionStraightMain = false) {
        horizontalDistance = horizontalDistance + 24;
    }

    //Update the horizontalDistance based on isParkPositionStraight

    //Update the horizontalDistance based on isAlliance


    }
    public void start() {
       //merry christmas

    }
}



