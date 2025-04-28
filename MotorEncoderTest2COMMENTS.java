// yap yap yap 
//
// these are the packages. They must be in the start of any code 
//
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


// here, you declare the file as autonomous. This is how it shows up in the driver hub under the autonomous Tab.
//so, if it was teleop, it would show up under the teleop tab
//the name is what it shows up as in the driver hub..... it is not needed but I like to change the number everytime after I upload to see if that update uploaded to the driver hub
@Autonomous (name = "File 2 Encoder Test 82")
    
// notice how the file name is --MotorEncoderTest2-- and this says the exact same thing. it MUST DO THAT or it will not work. Make sure if you copy a sample file or anything like that that this matches the files name!!!!
    
public class MotorEncoderTest2 extends LinearOpMode {

// this is where you do a lot of the code set up, like setting up variables. 

// these are the variables. They are just initialzed and have no value assigned to them for now. All of the motors are DcMotors, and are assigned as so. The servos are CrMotor. the ones with int are the ones that I've created and use throughout the program to store numbers, but are not required in every code thingy you do.
// whatever you call the variable here will be what it is used as in the code. we usually use the same name that is in the driver hub
    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;
    private DcMotor motorArm;
    private CRServo servoRotate;
    private DcMotor motorArmLinearSlide;

    // as an integer, these variables will only accept a value like 2 or 23403. No decimals. 
    private int backLeftPos;
    private int frontLeftPos;
    private int backRightPos;
    private int frontRightPos;
    private int armPos;
    private int motorArmLinearSlidePos;

    int armTarget;
    // a double can store decimals
    double armSpeed;
    // boolean is True or false
    boolean up;
    int armLinearTarget;
    double armLinearSpeed;

    @Override
    public void runOpMode() {
    //here is where you will continue some code set up, but more of set values to variables. 

        //taking a look at how this is set, the name I set it to here (frontLeftMotor) is the same as I initalized it as above. 
        //*important that the stuff in the parathesis (omg ignore my spelling) is the SAME as it is in the driver hub configurations* 
        
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        motorArm = hardwareMap.get(DcMotor.class, "motorArm");
        motorArmLinearSlide = hardwareMap.get(DcMotor.class, "motorArmLinearSlide");
        servoRotate = hardwareMap.get(CRServo.class, "servoRotate");
        

        //here I am checking to make sure that the encoder values are all reset so that I can start the counts from 0
        // this is done for every motor!!

        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        
        // in order to go straight some of the wheels have to be reversed. We have the left side reversed.
        // note the motor arm is reversed too
        
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        motorArm.setDirection(DcMotorSimple.Direction.REVERSE);
        

        //using encoders, we look at the position of the wheels. so earlier I reset the encoders to make sure they start at 0. here we are making sure the varible is also.
        
        backLeftPos = 0;
        frontLeftPos = 0;
        backRightPos = 0;
        frontRightPos = 0;
        motorArmLinearSlidePos = 0;


        // this will be explained some more later on, but the target is the position you want the motor to go. Here, It is set to over 360 so that it goes multiple times. It is kind of like using the sleep function, but is much more precise. 
        
        armTarget = 1500;
        armSpeed = 0.3;


        //so the stuff after wait for start will not run until we press play in the driver hub

        waitForStart();


        // usually i like to use functions, but here is a spot where I hard coded it.

        // for clarity, comment what each part does like i did here

        
        //lift arm
        // so earlier we set the arm position to 0. 
        // looking at line 95, we set the arm target to 1500. 
        // so by adding the arm position to the armPos, we are making the new arm position to be 1500.
        
        armPos += armTarget;

        //set target position of the motor to the arm position
        
        motorArm.setTargetPosition(armPos);

        //run the motor arm to the position
        
        motorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //set the speed that the motor does this at
        motorArm.setPower(armSpeed);

        //okok so ik we aren't using sleep function, but here is serves the purpose of giving the code a pause. this can be helpful so that the arm extends and doesn't automatically do anything too fast
        sleep(2000);

        //here I am calling a function. I like to use functions to avoid being repetitive, plus it makes it easier to read. go to around line 270 for this function. 
        // as you can see after looking at the defined function, this will (we hope) hold the arm to position 60, at a speed of 0.05
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

    // here is the function to -supposedly- hold the arm. it takes two arguments of the target of the arm (what we will set the position to) and the speed
    private void armLinearHold(int armLinearTarget, double armLinearSpeed){

        // this follows the same thingy where we take the position that is initially at 0 and then add the target so that it has a new position

        motorArmLinearSlidePos += armLinearTarget;
        motorArmLinearSlide.setTargetPosition(motorArmLinearSlidePos);
        motorArmLinearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorArmLinearSlide.setPower(armLinearSpeed);

        // this part is not required, but nice because it will show on the driver hub when the code is running the position of the motor. 
        // so it checks if the auto mode is going and if the motor is going
        while (opModeIsActive() && motorArmLinearSlide.isBusy()){

            // the first part (telemetry.addData) is telling it that while the opmode and motor arm linear slide motor are going at the same time, to show this on the driver hub
            // the part in the parenthesis ("Arm Linear Slide is holding at:") is the text that will show on the driver hub.
            // the (motorArmLinearSlide.getCurrentPosition()) is, well, getting the position of the motor.
            telemetry.addData("Arm Linear Slide is holding at:", motorArmLinearSlide.getCurrentPosition());

            // as you can guess this updates the telemetry on the driver hub 
            telemetry.update();
        }

        // after it does its thing, we can set the power to 0 to make sure it is fully off
        
        motorArmLinearSlide.setPower(0);

        // here, the sleep is used to make sure that the next code doesn't happen right away, but give a small pause
        sleep(200);

        // same thing as earlier, this shows the position on the driver hub.
        // Notice this is outside of the while loop. So is goes when the arm motor stops going.
        telemetry.addData("Arm Linear has reached target:", motorArmLinearSlide.getCurrentPosition());

    }
}
