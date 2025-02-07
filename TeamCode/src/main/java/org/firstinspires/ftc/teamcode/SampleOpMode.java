package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Jimbo Jones OpMode")
public class SampleOpMode extends LinearOpMode {
    DcMotor motor = hardwareMap.dcMotor.get("Motor1");
    @Override
    public void runOpMode() {
        System.out.println("This runs before start is pressed (init period)");
        waitForStart();
        System.out.println("This runs after start is pressed");
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(0.1);
    }
}




// op mode is the chunk of code that runs. when the robot runs it starts the initialization before
//  the game starts
// then the robot starts to move

//making certain changes to the robot do it before wait for start. you are defining everything


