package org.firstinspires.ftc.teamcode.teleop.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import edina.library.util.drivecontrol.DriveDirection;

@Disabled
@Autonomous
public class StrafeTestMode extends DriveTestMode{
    public StrafeTestMode() {
        dir = DriveDirection.Lateral;
    }
}
