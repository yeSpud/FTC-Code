package org.firstinspires.ftc.teamcode.Dragons1;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Stephen Ogden on 11/7/17.
 * FTC 6128 | 7935
 * FRC 1595
 */

@Autonomous(name = "Blue Jewel", group = "Test")
//@Disabled
public class BlueJewel extends LinearOpMode {

    private SixtyOneTwentyEightConfig bot = new SixtyOneTwentyEightConfig();

    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        bot.getConfig(hardwareMap);

        ElapsedTime time = new ElapsedTime();

        int stageNumber = 0;
        double colorValue = 0.0;

        String color = "";

        bot.leftServo.setPosition(bot.leftUp);
        bot.rightServo.setPosition(bot.rightUp);

        telemetry.addData("Status", "Done! Press play to start");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {

            if (stageNumber == 0) {
                bot.leftServo.setPosition(bot.leftDown);
                time.reset();
                stageNumber++;
            } else if (stageNumber == 1) {
                if (time.seconds() < 2) {
                    // This is here to wait for the servo to get into position
                } else {
                    stageNumber = 2;
                }
            } else if (stageNumber == 2 || stageNumber == 3 || stageNumber == 4) {
                if (bot.leftColorSensor.red() > bot.leftColorSensor.blue()) {
                    colorValue = (colorValue + 1.0);
                    stageNumber++;
                } else {
                    // Its blue, so dont add the color value
                    stageNumber++;
                }
            } else if (stageNumber == 5) {
                if (((int) Math.round(colorValue / 3)) == 1) {
                    color = "Red";
                    bot.app.setBackgroundColor(Color.argb(bot.leftColorSensor.alpha(), bot.leftColorSensor.red(), bot.leftColorSensor.green(), bot.leftColorSensor.blue()));
                    stageNumber = 10;
                } else {
                    color = "Blue";
                    bot.app.setBackgroundColor(Color.argb(bot.leftColorSensor.alpha(), bot.leftColorSensor.red(), bot.leftColorSensor.green(), bot.leftColorSensor.blue()));
                    stageNumber = 10;
                }

            } else if (stageNumber == 6) {
                if (color.equals("Red")) {
                    driveToPostion(bot.left, 2);
                    driveToPostion(bot.right, 2);
                    stageNumber++;
                } else {
                    driveToPostion(bot.left, -2);
                    driveToPostion(bot.right, -2);
                    stageNumber++;
                }
            } else if (stageNumber == 7) {
                if (isThere(bot.left, 2000) || isThere(bot.right, 2000)) {
                    bot.left.setPower(0);
                    bot.right.setPower(0);
                    bot.leftServo.setPosition(bot.leftUp);
                    stageNumber++;
                }

            } else if (stageNumber == 8) {
                if (color.equals("Red")) {
                    driveToPostion(bot.left, 1);
                    driveToPostion(bot.right, -1);
                    stageNumber++;
                } else {
                    driveToPostion(bot.left, -1);
                    driveToPostion(bot.right, 1);
                    stageNumber++;
                }
            } else if (stageNumber == 9) {
                if (isThere(bot.left, 2000) || isThere(bot.right, 2000)) {
                    bot.left.setPower(0);
                    bot.right.setPower(0);
                    stageNumber++;
                }
            } else if (stageNumber == 10) {
                //stop();
            }

            if ((bot.left.getTargetPosition() - bot.left.getCurrentPosition()) >= 10) {
                bot.left.setPower(1);
            } else if ((bot.left.getTargetPosition() - bot.left.getCurrentPosition()) <= -10) {
                bot.left.setPower(-1);
            } else {
                bot.left.setPower(0);
            }

            if ((bot.right.getTargetPosition() - bot.right.getCurrentPosition()) >= 10) {
                bot.right.setPower(1);
            } else if ((bot.right.getTargetPosition() - bot.right.getCurrentPosition()) <= -10) {
                bot.right.setPower(-1);
            } else {
                bot.right.setPower(0);
            }

            telemetry.addData("Stage number", stageNumber)
                    .addData("Color", color)
                    .addData("Time", time.seconds())
                    .addData("Red value", bot.leftColorSensor.red())
                    .addData("Blue value", bot.leftColorSensor.blue())
                    .addData("", "").addData("lefFront pos", bot.left.getCurrentPosition())
                    .addData("left target", bot.left.getTargetPosition())
                    .addData("left ∆", Math.abs(bot.left.getTargetPosition() - bot.left.getCurrentPosition()))
                    .addData("", "").addData("right pos", bot.right.getCurrentPosition())
                    .addData("right target", bot.right.getTargetPosition())
                    .addData("right ∆", Math.abs(bot.right.getTargetPosition() - bot.right.getCurrentPosition()));
            telemetry.update();

            idle();
        }
        telemetry.addData("Status", "Done!").addData("Stage number", stageNumber);
        telemetry.update();
    }

    private static void driveToPostion(DcMotor motor, int position) {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setTargetPosition(position * 25810);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    private static boolean isThere(DcMotor motor, int discrepancy) {
        int curentPos = motor.getCurrentPosition();
        int targetPos = motor.getTargetPosition();
        return Math.abs((targetPos - curentPos)) <= discrepancy;
    }

}
