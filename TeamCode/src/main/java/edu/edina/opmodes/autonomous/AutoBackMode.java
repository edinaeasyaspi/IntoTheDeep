package edu.edina.opmodes.autonomous;

import static edu.edina.library.util.drivecontrol.DriveDirection.Axial;
import static edu.edina.library.util.drivecontrol.DriveDirection.Lateral;
import static edu.edina.opmodes.autonomous.AutoMode.SelectedSpike.AUDIENCE;
import static edu.edina.opmodes.autonomous.AutoMode.SelectedSpike.BACKSTAGE;
import static edu.edina.opmodes.autonomous.AutoMode.SelectedSpike.MIDDLE;

import edu.edina.library.util.GrabberSide;
import edu.edina.library.util.Position;

public abstract class AutoBackMode extends AutoMode {
    protected AutoBackMode(boolean invert, Position startingPos) {
        super(invert, startingPos);
    }

    @Override
    protected void runMainPath() {
        if (position == BACKSTAGE) {
            driveToClosestPoint(24, 84, Axial);
            rotateToHeading(-45);
            driveToClosestPoint(32, 87.5, Axial);
            piBot.drop(GrabberSide.Left);
            driveToClosestPoint(24, 84, Axial);
        } else if (position == MIDDLE) {
            driveToClosestPoint(33, 84, Axial);
            piBot.drop(GrabberSide.Left);
            driveToClosestPoint(16, 84, Axial);
        } else if (position == AUDIENCE) {
            driveToClosestPoint(27, 84, Axial);
            rotateToHeading(-135);
            driveToClosestPoint(33, 78, Axial);
            piBot.drop(GrabberSide.Left);
            driveToClosestPoint(27, 84, Axial);
            driveToClosestPoint(21, 90, Lateral);
        } else {
            driveToClosestPoint(24, 84, Axial);
            rotateToHeading(-45);
            driveToClosestPoint(32, 87.5, Axial);
            piBot.drop(GrabberSide.Left);
            driveToClosestPoint(24, 84, Axial);
        }

        rotateToPoint(20, 116);
        driveToClosestPoint(20, 116, Axial);
        rotateToHeading(180);
        driveToClosestPoint(37, 116, Lateral);
        driveToClosestPoint(36, 13, Axial);
        //flip arm over
        piBot.drop(GrabberSide.Right);
        rotateToPoint(67, 85);
        driveToClosestPoint(67, 85, Axial);
        rotateToPoint(61, 16);
        driveToClosestPoint(61, 21, Axial);
        rotateToHeading(0);
        driveToClosestPoint(61, 15, Axial);
        piBot.grab(GrabberSide.Left);
        //driveToClosestPoint();

        //drop pixel
        //driveToClosestPoint(10, 110, Lateral);
    }
}
