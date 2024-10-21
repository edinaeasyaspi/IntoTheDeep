package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.PurePursuitTest.followCurve;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.sql.Array;
import java.util.ArrayList;

public class TestPurePursuit extends OpMode {
    @Override
    public void init() {

    }
    @Override
    public void loop() {

        ArrayList<CurvePoint> allPoints = new ArrayList<>();
        allPoints.add(new CurvePoint (0,0,1.0,1.0,50, Math.toRadians(50),1.0));
        allPoints.add(new CurvePoint (180,180,1.0,1.0,50, Math.toRadians(50),1.0));
        allPoints.add(new CurvePoint (0,0,1.0,1.0,50, Math.toRadians(50),1.0));
        allPoints.add(new CurvePoint (0,0,1.0,1.0,50, Math.toRadians(50),1.0));
        allPoints.add(new CurvePoint (0,0,1.0,1.0,50, Math.toRadians(50),1.0));

        followCurve(allPoints, Math.toRadians(90));
    }
}
