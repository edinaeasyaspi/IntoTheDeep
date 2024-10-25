package org.firstinspires.ftc.teamcode.Test;



public class Robot {
    public static boolean  usingComputer = true;


    public Robot() {
        worldXPosition = 0;
        worldYPosition = 0;
        worldAngle_rad = Math.toRadians(-45);
    }

    private double xSpeed = 0.3;
    private double ySpeed = 0.3;
    private double turnSpeed = 0.3;

    public static double worldXPosition;
    public static double worldYPosition;
    public static double worldAngle_rad;

    public double getXPos() {
        return worldXPosition;
    }
    public double worldYPos() {
        return worldYPosition;
    }
}