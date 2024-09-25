package edina.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

import edina.library.util.RobotHardware;
import edina.library.util.SmartGamepad;

@TeleOp(name = "DriveMe Webcam", group = "teleop")
public class WebcamOpMode extends OpMode {
    protected RobotHardware robotHardware;
    protected SmartGamepad driver1Gamepad;
    protected SmartGamepad driver2Gamepad;
    private OpenCvCamera webcam;
    private YourImageProcessingPipeline imageProcessingPipeline;

    @Override
    public void init() {
        driver1Gamepad = new SmartGamepad(gamepad1);
        driver2Gamepad = new SmartGamepad(gamepad2);
        robotHardware = new RobotHardware(hardwareMap);

        // Initialize webcam
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(robotHardware.LogitechC270_8034PI, cameraMonitorViewId);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                /*
                 * Tell the webcam to start streaming images to us! Note that you must make sure
                 * the resolution you specify is supported by the camera. If it is not, an exception
                 * will be thrown.
                 *
                 * Keep in mind that the SDK's UVC driver (what OpenCvWebcam uses under the hood) only
                 * supports streaming from the webcam in the uncompressed YUV image format. This means
                 * that the maximum resolution you can stream at and still get up to 30FPS is 480p (640x480).
                 * Streaming at e.g. 720p will limit you to up to 10FPS and so on and so forth.
                 *
                 * Also, we specify the rotation that the webcam is used in. This is so that the image
                 * from the camera sensor can be rotated such that it is always displayed with the image upright.
                 * For a front facing camera, rotation is defined assuming the user is looking at the screen.
                 * For a rear facing camera or a webcam, rotation is defined assuming the camera is facing
                 * away from the user.
                 */
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });


        // Set up image processing pipeline
        imageProcessingPipeline = new YourImageProcessingPipeline(); // Implement this class
        webcam.setPipeline(imageProcessingPipeline);

      //  webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
    }
 
    @Override
    public void init_loop() {
        //RUN THE CODE COME ON I WANT TO SEE THE STREAMING ON THE CAMERA PLEase
    }

    @Override
    public void start() {
        Mat processedFrame = imageProcessingPipeline.getResultMat();
        webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
    }

    @Override
    public void loop() {
        driver1Gamepad.update();
        driver2Gamepad.update();

        // Access the result of image processing through the pipeline
        Mat processedFrame = imageProcessingPipeline.getResultMat();



        // Perform actions based on processed frames
        // ...


        telemetry.addData("Some Information", "...");
        telemetry.update();
    }

    @Override
    public void stop() {
        // Actions to perform when OpMode stops
       // webcam.stopStreaming();
    }

    // Implement your image processing pipeline
    public static class YourImageProcessingPipeline extends OpenCvPipeline {
        private Mat resultMat;


        @Override
        public Mat processFrame(Mat input) {
            // Implement your image processing here
            // Modify 'input' as needed
            resultMat = input.clone(); // Example: simply clone the input

            return resultMat;
        }

        public Mat getResultMat() {
            return resultMat;
        }
    }
}
