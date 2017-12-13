package org.firstinspires.ftc.teamcode.Dragons1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import static org.firstinspires.ftc.teamcode.Dragons1.SixtyOneTwentyEightConfig.isThere;
import static org.firstinspires.ftc.teamcode.Dragons1.SixtyOneTwentyEightConfig.driveToPosition;
import static org.firstinspires.ftc.teamcode.Dragons1.SixtyOneTwentyEightConfig.turn;

/**
 * Created by Stephen Ogden on 11/28/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

//@Disabled
@Autonomous(name = "Red Club (straight)", group = "Test")

public class RedCubeStraight extends LinearOpMode {
    SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();
    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        bot.getConfig(hardwareMap);

        ElapsedTime time = new ElapsedTime();

        int stageNumber = 0;

        bot.leftServo.setPosition(bot.leftUp);
        bot.rightServo.setPosition(bot.rightUp);
        bot.arm.setPower(0);

        telemetry.addData("Status", "Done!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            if (stageNumber == 0) {
                //bot.arm.setTargetPosition(-977);
                driveToPosition(bot.right, -30);
                //driveToPosition(bot.left, 12);
                bot.arm.setPower(0);
                bot.left.setPower(-1);
                stageNumber++;
            } else if (stageNumber == 1) {
                if (isThere(bot.right, 100)) {
                    bot.right.setPower(0);
                    bot.left.setPower(0);
                    stageNumber++;
                }
            } else if (stageNumber == 2) {
                    bot.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    turn(90, bot.left, bot.right, bot.gyro);
                    bot.left.setPower(bot.right.getPower() * -1);
                    if (bot.right.getPower() == 0) {
                        stageNumber++;
                        bot.left.setPower(0);
                    }
            } else if (stageNumber == 3) {
                bot.left.setPower(0);
                bot.right.setPower(0);
                time.reset();
                bot.arm.setPower(0);
                while (time.milliseconds() < 500) {
                    bot.arm.setPower(-0.5d);
                }
                bot.arm.setPower(0);
                time.reset();
                while (time.milliseconds() < 500) {
                    bot.lintake.setPower(-1);
                    bot.rintake.setPower(-1);
                }
                bot.lintake.setPower(0);
                bot.rintake.setPower(0);
                bot.arm.setPower(0);
                stageNumber++;

            } else if (stageNumber == 4) {
                //stop();
            }

            if (stageNumber == 1) {
                if ((bot.right.getTargetPosition() - bot.right.getCurrentPosition()) >= 10) {
                    bot.right.setPower(1);
                } else if ((bot.right.getTargetPosition() - bot.right.getCurrentPosition()) <= -10) {
                    bot.right.setPower(-1);
                } else {
                    bot.right.setPower(0);
                }
            }

            Orientation angles = bot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            telemetry.addData("Stage number", stageNumber)
                    .addData("","")
                    .addData("Right motor position", bot.right.getCurrentPosition())
                    .addData("Right motor target", bot.right.getTargetPosition())
                    .addData("Right motor ∆", bot.right.getTargetPosition()-bot.right.getCurrentPosition())
                    .addData("","")
                    .addData("Left motor position", bot.left.getCurrentPosition())
                    .addData("Left motor target", bot.left.getTargetPosition())
                    .addData("Left motor ∆", bot.left.getTargetPosition()-bot.left.getCurrentPosition())
                    .addData("","")
                    .addData("Arm position", bot.arm.getCurrentPosition())
                    .addData("Arm target", bot.arm.getTargetPosition())
                    .addData("Arm ∆", bot.arm.getCurrentPosition() - bot.arm.getTargetPosition())
                    .addData("","")
                    .addData("Angles", angles + " (" + (int)Math.round((double) angles.firstAngle) + ")");
            telemetry.update();

        }
    }
}
