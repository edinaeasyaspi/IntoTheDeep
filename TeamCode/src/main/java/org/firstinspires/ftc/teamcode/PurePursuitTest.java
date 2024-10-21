package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.MathFunctions.AngleWrap;
import static org.firstinspires.ftc.teamcode.MathFunctions.lineCircleIntersection;
import static org.firstinspires.ftc.teamcode.Robot.worldAngle_rad;
import static org.firstinspires.ftc.teamcode.Robot.worldXPosition;
import static org.firstinspires.ftc.teamcode.Robot.worldYPosition;


import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.TestPurePursuit;
import org.opencv.core.Point;

import java.util.ArrayList;


public class PurePursuitTest {



    public static void followCurve(ArrayList<CurvePoint> allPoints, double followAngle) {
        for (int i=0; i < allPoints.size()- 1; i++){

        }
        CurvePoint followMe = getFollowPointPath(allPoints, new Point(worldXPosition, worldYPosition), worldXPosition, worldYPosition, allPoints.get(0).followDistance);

    }


    public static CurvePoint getFollowPointPath(ArrayList<CurvePoint> pathPoints, Point robotLocation, double xPos, double yPos, double followRadius){
        CurvePoint  followMe = new CurvePoint(pathPoints.get(0));
        for(int i=0; i < pathPoints.size() - 1; i ++){
             CurvePoint startLine = pathPoints.get(i);
             CurvePoint endLine = pathPoints.get(i+1);


             ArrayList<Point> intersections = lineCircleIntersection(robotLocation,followRadius, startLine.toPoint(),
                     endLine.toPoint());

             double closestAngle = 10000000;

             for(Point thisIntersection : intersections) {
                 double angle = Math.atan2(thisIntersection.y - worldYPosition, thisIntersection.x - worldXPosition);
                 double deltaAngle = Math.abs(MathFunctions.AngleWrap(angle - worldAngle_rad));

                 if(deltaAngle < closestAngle) {
                     closestAngle = deltaAngle;
                     followMe.setPoint(thisIntersection);
                 }
             }
        }
        return followMe;
    }







    public static void goToPosition(double x, double y, double movementSpeed,double preferredAngle, double turn_speed ){

        double distanceToTarget = Math.hypot(x-worldXPosition, y-worldYPosition);

        double absoluteAngleToTarget = Math.atan2(y-worldYPosition, x-worldXPosition);

        double relativeAngleToPoint = AngleWrap(absoluteAngleToTarget) - (worldAngle_rad - Math.toRadians(90));

         double movement_x;
         double movement_y;
         double movement_turn;

       double relativeXToPoint = Math.cos(relativeAngleToPoint) * distanceToTarget;
       double relativeYToPoint = Math.sin(relativeAngleToPoint) * distanceToTarget;

       double movementXPower = relativeXToPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYToPoint));
       double movementYPower = relativeYToPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYToPoint));

       movement_x = movementXPower * movementSpeed;
       movement_y = movementYPower * movementSpeed;

       double relativeTurnAngle = relativeAngleToPoint - Math.toRadians(180) + preferredAngle;
       movement_turn = Range.clip(relativeTurnAngle/Math.toRadians(30), -1,1) * turn_speed;

       if(distanceToTarget <10){
           movement_turn = 0;
       }
    }
}
