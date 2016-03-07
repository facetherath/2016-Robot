package com.team2383.robot;

import org.strongback.components.Switch;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.DirectionalAxis.Direction;
import org.strongback.components.ui.FlightStick;
import org.strongback.function.DoubleToDoubleFunction;
import org.strongback.hardware.Hardware;

import com.team2383.robot.components.ToggleSwitch;

public final class OI {

    private static FlightStick leftStick = Hardware.HumanInterfaceDevices.logitechAttack3(0);
    private static FlightStick rightStick = Hardware.HumanInterfaceDevices.logitechAttack3(1);

    private static double expo(double x) {
        return Constants.inputExpo * Math.pow(x, 3) + (1 - Constants.inputExpo) * x;
    };

    private static double deadband(double x) {
        return Math.abs(x) <= Constants.inputDeadband ? 0 : x;
    };

    private static double invert(double x) {
        return Math.abs(x) <= Constants.inputDeadband ? 0 : x;
    };

    private static DoubleToDoubleFunction mapper = (x) -> OI.expo(OI.deadband(x));
    private static DoubleToDoubleFunction mapperWithInvert = (x) -> OI.expo(OI.deadband(OI.invert(x)));

    public static Switch invert = leftStick.getTrigger();

    public static ContinuousRange arcadeDriveSpeed = leftStick.getPitch().map(OI.mapperWithInvert);
    public static ContinuousRange arcadeTurnSpeed = rightStick.getRoll().map(OI.mapperWithInvert);
    public static ContinuousRange tankLeftSpeed = leftStick.getPitch().map(OI.mapperWithInvert);
    public static ContinuousRange tankRightSpeed = rightStick.getPitch().map(OI.mapperWithInvert);

    public static Switch straight = rightStick.getTrigger();
    public static Switch useTank = new ToggleSwitch(leftStick.getButton(2));

    public static Switch shiftDown = leftStick.getThumb();
    public static Switch shiftUp = rightStick.getThumb();

    private static FlightStick operatorStick = Hardware.HumanInterfaceDevices.logitechExtreme3DPro(2);

    public static ContinuousRange hood = OI.operatorStick.getPitch().map(OI.mapper);

    public static Switch feedIn = operatorStick.getButton(7);
    public static Switch feedOut = operatorStick.getButton(8);

    public static Switch extendArms = operatorStick.getDPad(0).getDirectionAsSwitch(Direction.UP);
    public static Switch retractArms = operatorStick.getDPad(0).getDirectionAsSwitch(Direction.DOWN);

    public static Switch shoot = operatorStick.getTrigger();
    public static Switch spool = operatorStick.getThumb();

    public static Switch manualHood = operatorStick.getButton(9);
    public static Switch presetBatter = operatorStick.getButton(10);
    public static Switch presetClose = operatorStick.getButton(11);
    public static Switch presetFar = operatorStick.getButton(12);
    public static Switch presetCloseHoodAndStopShooter = operatorStick.getButton(6);
}
