package library.util;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ThreadPool;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import edina.library.subsystems.Intake;
import edina.library.subsystems.Lift;
import edina.library.subsystems.Subsystem;
import edina.library.subsystems.Webcam;

public class Robot {
    private ExecutorService subsystemUpdateExecutor;
    public boolean Started;
    private boolean runMultiThreaded;
    public RobotHardware RobotHardware;
    private List<Subsystem> subsystems = new ArrayList<>();
    private Telemetry telemetry;
    public Lift Lift;
   // public MecanumDrive MecanumDrive;
    public Intake Intake;
    public Webcam Webcam;
    private Runnable subsystemUpdateRunnable = () -> {
        while (!Thread.currentThread().isInterrupted()) {
            internal_update();
        }
    };

    public Robot(Telemetry telemetry, HardwareMap map, boolean runMultiThreaded) {
        this.telemetry = telemetry;
        this.runMultiThreaded = runMultiThreaded;
        this.RobotHardware = new RobotHardware(map);

        this.Lift = new Lift(this);
        subsystems.add(this.Lift);

        //this.MecanumDrive = new MecanumDrive(this);
        //subsystems.add(this.MecanumDrive);

        this.Intake = new Intake(this);
        subsystems.add(this.Intake);

       // this.Webcam = new Webcam(this);

        if (this.runMultiThreaded) {
            // setup the thread executor
            subsystemUpdateExecutor = ThreadPool.newSingleThreadExecutor("subsystem update");
        }
    }

    public void init() {
        for (Subsystem subsystem : subsystems) {
            subsystem.init();
        }
    }

    public void update() {
        // check to see if running update on different thread
        if (!runMultiThreaded) {
            // we are not so run it here
            internal_update();
        }
    }

    private void internal_update() {
        for (Subsystem subsystem : subsystems) {
            subsystem.update();
        }
    }

    public void start() {
        for (Subsystem subsystem : subsystems) {
            subsystem.start();
        }

        if (runMultiThreaded){
            if (!Started) {
                subsystemUpdateExecutor.submit(subsystemUpdateRunnable);
            }
        }

        Started = true;
    }

    public void stop() {
        if (runMultiThreaded){
            if (subsystemUpdateExecutor != null) {
                subsystemUpdateExecutor.shutdownNow();
                subsystemUpdateExecutor = null;
            }
        }

        Started = false;
    }

    public void telemetry() {
        RobotState.getInstance().telemetry(this.telemetry, this.RobotHardware);
        telemetry.update();
    }
}
