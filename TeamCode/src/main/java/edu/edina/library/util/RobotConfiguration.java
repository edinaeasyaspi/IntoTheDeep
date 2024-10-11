package edu.edina.library.util;

public class RobotConfiguration {
    private static RobotConfiguration robotConfiguration = null;


    public static synchronized RobotConfiguration getInstance()
    {
        if (robotConfiguration == null) {
            robotConfiguration = new RobotConfiguration();
        }

        return robotConfiguration;
    }
}
