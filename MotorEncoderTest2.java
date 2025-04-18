package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous (name = "File 2 Encoder Test 82")
public class MotorEncoderTest2 extends LinearOpMode {

    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;
    private DcMotor motorArm;
    private CRServo servoRotate;
    private DcMotor motorArmLinearSlide;
    private int backLeftPos;
    private int frontLeftPos;
    private int backRightPos;
    private int frontRightPos;
    private int armPos;
    private int motorArmLinearSlidePos;

    int armTarget;
    double armSpeed;
    boolean up;
    int armLinearTarget;
    double armLinearSpeed;

    @Override
    public void runOpMode() {
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        motorArm = hardwareMap.get(DcMotor.class, "motorArm");
        motorArmLinearSlide = hardwareMap.get(DcMotor.class, "motorArmLinearSlide");
        servoRotate = hardwareMap.get(CRServo.class, "servoRotate");

        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        motorArm.setDirection(DcMotorSimple.Direction.REVERSE);

        backLeftPos = 0;
        frontLeftPos = 0;
        backRightPos = 0;
        frontRightPos = 0;
        motorArmLinearSlidePos = 0;

        armTarget = 1500;
        armSpeed = 0.3;

        waitForStart();

        //lift arm
        armPos += armTarget;
        motorArm.setTargetPosition(armPos);
        motorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorArm.setPower(armSpeed);
        sleep(2000);

        //extend
        armLinearHold(60, 0.05);
        //drive
        drive(800, 800, 0.4, 0.4);
        // servo rotate
        sleep(800);
        servoRotate.setPower(-0.6);
        sleep(5000);
        servoRotate.setPower(0);
        //put arm back
        sleep(500);

        //go straight
        //go backwards
        drive(-700, -700, 0.4, 0.4);
        armPos = 0;
        motorArm.setTargetPosition(armPos);
        motorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorArm.setPower(armSpeed);
        sleep(700);

        /*
        // straff right
        straffRight(700, 700, 0.4);
        System.out.println("straffed");
        //go forward
        drive(500, 500, 0.4, 0.4);
        // straff left
        straffLeft(700, 700, 0.4);

         */
    }

    private void drive(int leftTarget, int rightTarget, double leftSpeed, double rightSpeed) {
        backLeftPos += leftTarget;
        frontLeftPos += leftTarget;
        backRightPos += rightTarget;
        frontRightPos += rightTarget;

        backLeftMotor.setTargetPosition(backLeftPos);
        frontLeftMotor.setTargetPosition(frontLeftPos);
        backRightMotor.setTargetPosition(backRightPos);
        frontRightMotor.setTargetPosition(frontRightPos);

        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        backLeftMotor.setPower(leftSpeed);
        frontLeftMotor.setPower(leftSpeed);
        backRightMotor.setPower(rightSpeed);
        frontRightMotor.setPower(rightSpeed);

        while (opModeIsActive() && backLeftMotor.isBusy() && backRightMotor.isBusy() && frontLeftMotor.isBusy() && frontRightMotor.isBusy()) {
            telemetry.addData("Front Left Position", frontLeftMotor.getCurrentPosition());
            telemetry.addData("Back Left Position", backLeftMotor.getCurrentPosition());
            telemetry.addData("Front Right Position", frontRightMotor.getCurrentPosition());
            telemetry.addData("Back Right Position", backRightMotor.getCurrentPosition());
            telemetry.update();
            idle();
        }


        backLeftMotor.setPower(0);
        frontLeftMotor.setPower(0);
        backRightMotor.setPower(0);
        frontRightMotor.setPower(0);

    }

    private void moveArm(int armTarget, double armSpeed, boolean up) {
        armPos += armTarget;
        motorArm.setTargetPosition(armPos);

        motorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (up){
            motorArm.setPower(armSpeed);
        } else {
            motorArm.setPower(-armSpeed);
        }
    }

    private void straffRight(int leftTarget, int rightTarget, double speed) {
        backLeftPos -= leftTarget;
        frontLeftPos += leftTarget;
        backRightPos += rightTarget;
        frontRightPos -= rightTarget;

        backLeftMotor.setTargetPosition(backLeftPos);
        frontLeftMotor.setTargetPosition(frontLeftPos);
        backRightMotor.setTargetPosition(backRightPos);
        frontRightMotor.setTargetPosition(frontRightPos);

        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        backLeftMotor.setPower(speed);
        frontLeftMotor.setPower(speed);
        backRightMotor.setPower(speed);
        frontRightMotor.setPower(speed);

        while (opModeIsActive() && backLeftMotor.isBusy() && backRightMotor.isBusy() && frontLeftMotor.isBusy() && frontRightMotor.isBusy()) {
            telemetry.addData("Straff Front Left Position", frontLeftMotor.getCurrentPosition());
            telemetry.addData("Straff Back Left Position", backLeftMotor.getCurrentPosition());
            telemetry.addData("Straff Front Right Position", frontRightMotor.getCurrentPosition());
            telemetry.addData("Straff Back Right Position", backRightMotor.getCurrentPosition());
            telemetry.update();
            idle();
        }
    }

    private void straffLeft(int leftTarget, int rightTarget, double speed) {
        backLeftPos += leftTarget;
        frontLeftPos -= leftTarget;
        backRightPos -= rightTarget;
        frontRightPos += rightTarget;

        backLeftMotor.setTargetPosition(backLeftPos);
        frontLeftMotor.setTargetPosition(frontLeftPos);
        backRightMotor.setTargetPosition(backRightPos);
        frontRightMotor.setTargetPosition(frontRightPos);

        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        backLeftMotor.setPower(speed);
        frontLeftMotor.setPower(speed);
        backRightMotor.setPower(speed);
        frontRightMotor.setPower(speed);

        while (opModeIsActive() && backLeftMotor.isBusy() && backRightMotor.isBusy() && frontLeftMotor.isBusy() && frontRightMotor.isBusy()) {
            telemetry.addData("Straff Front Left Position", frontLeftMotor.getCurrentPosition());
            telemetry.addData("Straff Back Left Position", backLeftMotor.getCurrentPosition());
            telemetry.addData("Straff Front Right Position", frontRightMotor.getCurrentPosition());
            telemetry.addData("Straff Back Right Position", backRightMotor.getCurrentPosition());
            telemetry.update();
            idle();
        }


    }

    private void armLinearHold(int armLinearTarget, double armLinearSpeed){
        motorArmLinearSlidePos += armLinearTarget;
        motorArmLinearSlide.setTargetPosition(motorArmLinearSlidePos);
        motorArmLinearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorArmLinearSlide.setPower(armLinearSpeed);

        while (opModeIsActive() && motorArmLinearSlide.isBusy()){
            telemetry.addData("Arm Linear Slide is holding at:", motorArmLinearSlide.getCurrentPosition());
            telemetry.update();
        }

        motorArmLinearSlide.setPower(0);
        sleep(200);
        telemetry.addData("Arm Linear has reached target:", motorArmLinearSlide.getCurrentPosition());

    }
}