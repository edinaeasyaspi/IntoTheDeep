package org.firstinspires.ftc.teamcode.Test;

import org.opencv.core.Point;

public class CurvePoint {
    public double x;
    public double y;
    public double moveSpeed;
    public double turnSpeed;
    public double followDistance;
    public double pointLength;
    public double slowDownTurnRadians;
    public double slowDownTurnAmount;

    public  CurvePoint (double x, double y, double moveSpeed, double turnSpeed, double followDistance, double slowDownTurnRadians, double slowDownTurnAmount){
        this.y = y;
        this.moveSpeed = moveSpeed;
        this.turnSpeed = turnSpeed;
        this.followDistance = followDistance;
        this.slowDownTurnRadians = slowDownTurnRadians;
        this.slowDownTurnAmount = slowDownTurnAmount;
    }

    public CurvePoint(CurvePoint thisPoint) {
        x = thisPoint.x;
        y = thisPoint.y;
        moveSpeed = thisPoint.moveSpeed;
        turnSpeed = thisPoint.turnSpeed;
        followDistance = thisPoint.followDistance;
        slowDownTurnAmount = thisPoint.slowDownTurnAmount;
        slowDownTurnRadians = thisPoint.slowDownTurnRadians;
        pointLength = thisPoint.pointLength;



    }
    public Point toPoint() {
        return new Point(x,y);
    }
    public void setPoint(Point point) {
        x = point.x;
        y = point.y;
    }
}


















































































