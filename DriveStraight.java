package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.CRServo;


@Config
@Autonomous(name="auto test 47 pidf")
public class DriveStraight extends LinearOpMode {
    DcMotorEx lsMotor;
    DcMotor motorArm;
    FtcDashboard dashboard;

    private DcMotor frontRightMotor;
    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    private CRServo servoRotate;


    int armPos;
    int armTarget;
    double armSpeed;
    @Override
    public void runOpMode() {
        lsMotor = hardwareMap.get(DcMotorEx.class, "motorArmLinearSlide");
        motorArm = hardwareMap.get(DcMotor.class, "motorArm");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        servoRotate = hardwareMap.get(CRServo.class, "servoRotate");

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);


        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();

        lsMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorArm.setDirection(DcMotorSimple.Direction.REVERSE);

        lsMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        armPos = 0;
        armTarget = 1500;
        armSpeed = 0.3;

        waitForStart();
        lsMotor.setPower(-0.4);
        motorArm.setPower(0);
        //beginning push linear slide in
        servoRotate.setPower(1);
        sleep(500);
        servoRotate.setPower(0);
        sleep(50);
        // strafe away from the wall to avoid collisions
        StrafeRight();
        // lift arm
        sleep(450);
        // Drive twards bucket
        DriveForward();
        sleep(1390);
        Pause();
        sleep(50);
        // Turn a bit to face the bucket more
        TurnLeft();
        sleep(470);
        Pause();
        sleep(1000);

        //lift arm
        armPos += armTarget;
        motorArm.setTargetPosition(armPos);
        motorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorArm.setPower(armSpeed);
        sleep(3000);

        // Move arm to position with PIDF
        double kP = 0.001, kI = 0.0, kD = 0.00002, kF = 0.2;
        double target = 400;
        double integral = 0, lastError = 0;
        long lastTime = System.currentTimeMillis();

        ElapsedTime timeout = new ElapsedTime();
        timeout.reset();

        //while (opModeIsActive() && Math.abs(lsMotor.getCurrentPosition() - target) > 10) {

            double error = target - lsMotor.getCurrentPosition();
            long now = System.currentTimeMillis();
            double dt = (now - lastTime) / 1000.0;

            integral += error * dt;
            double derivative = (error - lastError) / dt;
            double output = kP * error + kI * integral + kD * derivative + kF;

            lsMotor.setPower(output);

            lastError = error;
            lastTime = now;
            telemetry.update();
            sleep(3000);

            //}
            double downTarget = -1000;
            double integral2 = 0;
            double lastError2 = 0;
            long lastTime2 = System.currentTimeMillis();


       //while (opModeIsActive() && Math.abs(lsMotor.getCurrentPosition() - downTarget) > 10) {
            double error2 = downTarget - lsMotor.getCurrentPosition();
             long now2 = System.currentTimeMillis();
             double dt2 = (now2 - lastTime2) / 1000.0;
            double kP2 = 0.01;
             integral2 += error2 * dt2;
            double derivative2 = (error2 - lastError2) / dt2;

             double output2 = kP2 * error2 + kI * integral2 + kD * derivative2 + kF;

            lsMotor.setPower(output2);

            lastError2 = error2;
            lastTime2 = now2;
            telemetry.update();

        //}
            lsMotor.setPower(0);
            sleep(5000);


        armPos -= armTarget;
        motorArm.setTargetPosition(armPos);
        motorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorArm.setPower(armSpeed);
        sleep(3000);

        }

    private void StrafeRight() {
        frontRightMotor.setPower(-0.4);
        frontLeftMotor.setPower(0.4);
        backLeftMotor.setPower(-0.4);
        backRightMotor.setPower(0.4);
    }

    private void DriveForward() {
        frontRightMotor.setPower(0.4);
        frontLeftMotor.setPower(0.4);
        backLeftMotor.setPower(0.4);
        backRightMotor.setPower(0.4);
    }

    private void Pause() {
        frontRightMotor.setPower(0);
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    private void TurnLeft() {
        frontRightMotor.setPower(0.4);
        frontLeftMotor.setPower(-0.4);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }


    }
