package com.team2383.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class VisionShoot extends CommandGroup {
	public VisionShoot() {
		addParallel(new UseVisionPreset(false));
		addParallel(new SpoolToRPM());
		addSequential(new VisionTurn());
		addSequential(new AutoShoot());
	}
}
