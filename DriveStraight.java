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

@Config
@Autonomous(name="auto test 20 pidf")
public class DriveStraight extends LinearOpMode {
    DcMotorEx lsMotor;
    DcMotor motorArm;
    FtcDashboard dashboard;

    int armPos;
    int armTarget;
    double armSpeed;
    @Override
    public void runOpMode() {
        lsMotor = hardwareMap.get(DcMotorEx.class, "motorArmLinearSlide");
        motorArm = hardwareMap.get(DcMotor.class, "motorArm");

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
        // lift arm
        armPos += armTarget;
        motorArm.setTargetPosition(armPos);
        motorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorArm.setPower(armSpeed);
        sleep(3000);

        // Move arm to position with PIDF
        double kP = 0.00015, kI = 0.0, kD = 0.00002, kF = 0.2;
        double target = 150;
        double integral = 0, lastError = 0;
        long lastTime = System.currentTimeMillis();

        while (opModeIsActive() && Math.abs(lsMotor.getCurrentPosition() - target) > 10) {
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

            }

            lsMotor.setPower(0);

        }


    }
