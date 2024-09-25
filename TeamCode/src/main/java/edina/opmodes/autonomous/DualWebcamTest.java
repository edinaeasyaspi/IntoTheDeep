package edina.opmodes.autonomous;

import static org.firstinspires.ftc.vision.VisionPortal.CameraState.STREAMING;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@Autonomous
public class DualWebcamTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        int[] viewportIds = VisionPortal.makeMultiPortalView(2, VisionPortal.MultiPortalLayout.HORIZONTAL);

        AprilTagProcessor frontAprilTagProc = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .build();

        AprilTagProcessor rearAprilTagProc = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .build();

        VisionPortal frontView = new VisionPortal.Builder()
                .setLiveViewContainerId(viewportIds[0])
                .enableLiveView(true)
                .addProcessor(frontAprilTagProc)
                .setCamera(hardwareMap.get(WebcamName.class, "LogitechC270_8034PI"))
                .setCameraResolution(new Size(640, 480))
                .build();

        VisionPortal rearView = new VisionPortal.Builder()
                .setLiveViewContainerId(viewportIds[1])
                .enableLiveView(true)
                .addProcessor(rearAprilTagProc)
                .setCamera(hardwareMap.get(WebcamName.class, "LogitechC270_8034PI_Rear"))
                .setCameraResolution(new Size(640, 480))
                .build();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("front", frontView.getCameraState());
            telemetry.addData("rear", rearView.getCameraState());
            telemetry.update();

            if (gamepad1.a) {
                if (frontView.getCameraState() == STREAMING)
                    frontView.stopStreaming();
                else
                    frontView.resumeStreaming();
            }

            if (gamepad1.b) {
                if (rearView.getCameraState() == STREAMING)
                    rearView.stopStreaming();
                else
                    rearView.resumeStreaming();
            }
        }
    }
}
