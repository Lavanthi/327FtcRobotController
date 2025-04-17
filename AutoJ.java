package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;

@Autonomous(name="Autonomous_Forward", group="Linear Opmode")
public class AutoJ extends LinearOpMode {

    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private CRServo outtakeServo = null;

    private static final double POWER = 0.5;
    private static final int TARGET_POSITION = 1000; // Adjust based on distance needed

    @Override
    public void runOpMode() {
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        outtakeServo = hardwareMap.get(CRServo.class, "outtakeServo");

        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftDrive.setTargetPosition(TARGET_POSITION);
        rightDrive.setTargetPosition(TARGET_POSITION);

        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftDrive.setPower(POWER);
        rightDrive.setPower(POWER);

        waitForStart();

        while (opModeIsActive() && leftDrive.isBusy() && rightDrive.isBusy()) {
            telemetry.addData("Left Position", leftDrive.getCurrentPosition());
            telemetry.addData("Right Position", rightDrive.getCurrentPosition());
            telemetry.update();
        }

        /// yap yap pay 
        leftDrive.setPower(0);
        rightDrive.setPower(0);

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Activate outtake servo
        outtakeServo.setPower(1.0);
        sleep(1000); // Let the servo run for a second
        outtakeServo.setPower(0);
    }
}
