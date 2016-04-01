package com.team2383.robot.auto;

import com.team2383.robot.Constants;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.commands.VisionShoot;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CrossHighGoal extends CommandGroup {
	public CrossHighGoal() {
		addSequential(new DriveDistance(1.0, -167, 4, 0.05, Gear.LOW, false));
		addSequential(new GyroTurn(Constants.driveHeadingMoveToVelocity, 179.9, 3));
		addSequential(new VisionShoot());
	}
}