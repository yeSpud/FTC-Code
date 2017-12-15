package org.firstinspires.ftc.teamcode.Dragons1;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import static org.firstinspires.ftc.teamcode.Dragons1.SixtyOneTwentyEightConfig.drive;

/**
 * Created by Stephen Ogden on 12/8/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@Autonomous(name = "Drive forward 3 feet", group = "Test")
//@Disabled
public class driveForwardXInches extends LinearOpMode {

    SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();

    public void runOpMode() {
        telemetry.addData("Status", "Initializing");
        telemetry.update();

        bot.getConfig(hardwareMap);

        bot.rightServo.setPosition(bot.rightUp);
        bot.leftServo.setPosition(bot.leftUp);

        bot.right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        telemetry.addData("Status", "Done");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            drive(bot.right, bot.left, 36);
            if (bot.right.getPower() == 0) {
                stop();
            }

            telemetry.addData("Current position", bot.right.getCurrentPosition())
                    .addData("Target position", bot.right.getTargetPosition())
                    .addData("Right power", bot.right.getPower())
                    .addData("Left power", bot.left.getPower());
            telemetry.update();
        }
    }
}
