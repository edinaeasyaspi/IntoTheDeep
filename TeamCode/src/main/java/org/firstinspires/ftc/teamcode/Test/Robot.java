package org.firstinspires.ftc.teamcode.Test;



public class Robot {
    public static boolean  usingComputer = true;


    public Robot() {
        worldXPosition = 50;
        worldYPosition = 140;
        worldAngle_rad = Math.toRadians(-45);
    }

    private double xSpeed = 0;
    private double ySpeed = 0;
    private double turnSpeed = 0;

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