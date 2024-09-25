package edina.library.subsystems;


import edina.library.enums.LiftState;
import edina.library.enums.LiftTipState;
import edina.library.util.Robot;
import edina.library.util.RobotConfiguration;
import edina.library.util.RobotHardware;
import edina.library.util.RobotState;


public class Lift extends Subsystem{
    private Robot robot;

    public Lift(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void init() {
        RobotState state = RobotState.getInstance();
        RobotHardware hardware = robot.RobotHardware;
        RobotConfiguration config = RobotConfiguration.getInstance();

        state.liftState = LiftState.Idle;
    }

    @Override
    public void start() {
    }

    @Override
    public void update() {
        RobotState state = RobotState.getInstance();
        RobotHardware hardware = robot.RobotHardware;
        RobotConfiguration config = RobotConfiguration.getInstance();

      //  if (robot.Started) {
        //    switch (state.liftState) {
          //      case Up:
            //        hardware.liftMotor.setPower(1.0);
              //      break;
                //case Down:
                  //  hardware.liftMotor.setPower(-1);
                    //break;
                //default:
                  //  hardware.liftMotor.setPower(0);
            //}

            switch (state.liftTipState) {
                case Horizontal:
                  //  hardware.leftLiftServo.setPosition(0.0);
                  //  hardware.rightLiftServo.setPosition(1.0);
                    break;
                default:
                case Vertical:
                   // hardware.leftLiftServo.setPosition(1);
                   // hardware.rightLiftServo.setPosition(0.0);
                    break;
            }

        }




    public void setProperties(boolean rightTrigger, boolean leftTrigger, boolean horizontal, boolean vertical) {
        RobotState state = RobotState.getInstance();
        RobotHardware hardware = robot.RobotHardware;
        RobotConfiguration config = RobotConfiguration.getInstance();

        if (leftTrigger) {
            state.liftState = LiftState.Up;
        } else if (rightTrigger) {
            state.liftState = LiftState.Down;
        } else {
            state.liftState = LiftState.Idle;
        }

        if (horizontal) {
            state.liftTipState = LiftTipState.Horizontal;
        } else {
            state.liftTipState = LiftTipState.Vertical;
        }

    }

}
