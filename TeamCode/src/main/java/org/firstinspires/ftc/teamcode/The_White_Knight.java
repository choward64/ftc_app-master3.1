package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.subsystems.ServoManagement;


/** Sean Cardosi
 * Team Rise 7719
 * Created by rpcardosimd on 10/25/16.
 */
@TeleOp(name = "The White Knight", group = "Rise Robot")
@Disabled
public class The_White_Knight extends OpMode {


    DcMotor left_back_drive;
    DcMotor left_front_drive;
    DcMotor right_back_drive;
    DcMotor right_front_drive;
    DcMotor conveyor_belt;
    DcMotor shooter1;
    DcMotor shooter2;
    Servo ball_servo;
    Servo right_pusher;
    Servo left_pusher;
    ServoManagement srvo;
    boolean shooter_srvo = true;

    public void init() {

        left_back_drive = hardwareMap.dcMotor.get("1");
        left_front_drive = hardwareMap.dcMotor.get("2");
        left_back_drive.setDirection(DcMotor.Direction.FORWARD);
        left_front_drive.setDirection(DcMotor.Direction.FORWARD);


        right_back_drive = hardwareMap.dcMotor.get("3");
        right_front_drive = hardwareMap.dcMotor.get("4");
        right_back_drive.setDirection(DcMotor.Direction.REVERSE);
        right_front_drive.setDirection(DcMotor.Direction.REVERSE);

        shooter1 = hardwareMap.dcMotor.get("5");
        shooter2 = hardwareMap.dcMotor.get("6");
        shooter1.setDirection(DcMotor.Direction.REVERSE);

        conveyor_belt = hardwareMap.dcMotor.get("cb");
        conveyor_belt.setDirection(DcMotor.Direction.REVERSE);
        ball_servo = hardwareMap.servo.get("bs");
        left_pusher = hardwareMap.servo.get("left_pusher");
        right_pusher = hardwareMap.servo.get("right_pusher");
        left_pusher.setDirection(Servo.Direction.FORWARD);
        right_pusher.setDirection(Servo.Direction.REVERSE);

        srvo = new ServoManagement(hardwareMap, telemetry);
    }

    public void loop() {


        //-------------------------------drivetrain---------------------------------------
        float throttle_left = gamepad1.left_stick_y;
        float throttle_right = gamepad1.right_stick_y;


        throttle_left = Range.clip(throttle_left, -1, 1);
        throttle_right = Range.clip(throttle_right, -1, 1);


        left_back_drive.setPower(throttle_left);
        left_front_drive.setPower(throttle_left);
        right_back_drive.setPower(throttle_right);
        right_front_drive.setPower(throttle_right);
        //---------------------------------------------------------------------------------




        //-------------------------------button pushers------------------------------------
        if (gamepad2.left_bumper) {
            if (!srvo.leftpush) {
                srvo.leftPusher(1);
            }
        } else if (!gamepad2.left_bumper) {
            if (srvo.leftpush) {
                srvo.leftPusher(0);
            }
        }
        if (gamepad2.right_bumper) {
            if (!srvo.rightpush) {
                srvo.rightPusher(1);
            }
        } else if (!gamepad2.right_bumper) {
            if (srvo.rightpush) {
                srvo.rightPusher(0);
            }
        }
        //---------------------------------------------------------------------------------




        //--------------------------------shooter mechanism--------------------------------
        if (shooter_srvo) {
            ball_servo.setPosition(0.0);
            shooter_srvo = false;
        }
        if (gamepad2.right_trigger > 0.75) {
            shooter1.setPower(0.7);
            shooter2.setPower(0.7);
        } else if (gamepad2.left_trigger > 0.75) {
            shooter1.setPower(0.7);
            shooter2.setPower(0.7);
        } else {
            shooter1.setPower(0.0);
            shooter2.setPower(0.0);
        }
        if (gamepad2.a) {
            ball_servo.setPosition(1.0);
        }else{
            ball_servo.setPosition(0.0);
        }
        //----------------------------------------------------------------------------------




        //-------------------------------conveyor belt--------------------------------------
        //toggle conveyor belt with dpad
        if (gamepad2.dpad_up) {
            conveyor_belt.setPower(1.0);
        }
        if (gamepad2.dpad_down) {
            conveyor_belt.setPower(-1.0);
        }
        if (gamepad2.dpad_left || gamepad2.dpad_right) {
            conveyor_belt.setPower(0.0);
        }
        //----------------------------------------------------------------------------------
    }
}