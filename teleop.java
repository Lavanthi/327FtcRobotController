package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.JavaUtil;

@TeleOp(name = "TeleOp2025 java version")
public class teleop extends LinearOpMode {

    private DcMotor frontLeftMotor;
    private DcMotor motorArm;
    private DcMotor motorArmLinearSlide;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;
    private CRServo servoRotate;
    private DcMotor motorLinear1;
    private DcMotor Linear2;

    /**
     * This sample contains the bare minimum Blocks for any regular OpMode. The 3 blue
     * Comment Blocks show where to place Initialization code (runs once, after touching the
     * DS INIT button, and before touching the DS Start arrow), Run code (runs once, after
     * touching Start), and Loop code (runs repeatedly while the OpMode is active, namely not
     * Stopped).
     */
    @Override
    public void runOpMode() {
        float y;
        double x;
        float rx;
        double m;
        double b;
        double denominator;
        float triggerR;
        float triggerL;

        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        motorArm = hardwareMap.get(DcMotor.class, "motorArm");
        motorArmLinearSlide = hardwareMap.get(DcMotor.class, "motorArmLinearSlide");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        servoRotate = hardwareMap.get(CRServo.class, "servoRotate");
        motorLinear1 = hardwareMap.get(DcMotor.class, "motorLinear1");
        Linear2 = hardwareMap.get(DcMotor.class, "Linear 2");

        // Put initialization blocks here.
        waitForStart();
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        motorArm.setDirection(DcMotor.Direction.REVERSE);
        motorArmLinearSlide.setDirection(DcMotor.Direction.REVERSE);
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                y = -gamepad1.left_stick_y;
                x = gamepad1.right_stick_x * 0.7;
                rx = gamepad1.left_stick_x;
                m = -(gamepad2.right_stick_y * 0.5);
                b = gamepad2.left_stick_y * 0.65;
                denominator = JavaUtil.maxOfList(JavaUtil.createListWith(JavaUtil.sumOfList(JavaUtil.createListWith(Math.abs(y), Math.abs(x), Math.abs(rx), Math.abs(m))), 1));
                triggerR = gamepad2.right_trigger;
                triggerL = gamepad2.left_trigger;
                frontLeftMotor.setPower((y + x + rx) / denominator);
                backLeftMotor.setPower(((y - x) + rx) / denominator);
                frontRightMotor.setPower(((y - x) - rx) / denominator);
                backRightMotor.setPower(((y + x) - rx) / denominator);
                motorArm.setPower(m / denominator);
                motorArmLinearSlide.setPower(b / denominator);
                if (triggerL > 0.5) {
                    servoRotate.setPower(-1);
                } else if (triggerR > 0.5) {
                    servoRotate.setPower(1);
                } else {
                    servoRotate.setPower(0);
                }
                if (gamepad2.y) {
                    motorLinear1.setPower(0.6);
                    Linear2.setPower(-0.6);
                } else if (gamepad2.x) {
                    motorLinear1.setPower(-0.6);
                    Linear2.setPower(0.6);
                } else {
                    motorLinear1.setPower(0);
                    Linear2.setPower(0);
                }
            }
        }
    }
}