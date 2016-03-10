
package com.team2383.robot;

import com.team2383.robot.auto.AutoCommand;
import com.team2383.robot.auto.ChevaldeFrise;
import com.team2383.robot.auto.LowBar;
import com.team2383.robot.auto.LowBarBatterHighGoal;
import com.team2383.robot.auto.LowBarBatterLowGoal;
import com.team2383.robot.auto.LowBarCourtyardHighGoal;
import com.team2383.robot.auto.Moat;
import com.team2383.robot.auto.Portcullis;
import com.team2383.robot.auto.Ramparts;
import com.team2383.robot.auto.Reach;
import com.team2383.robot.auto.RockWall;
import com.team2383.robot.auto.RoughTerrain;
import com.team2383.robot.auto.SpyBotHighGoal;
import com.team2383.robot.auto.SpyBotLowGoal;
import com.team2383.robot.commands.GeneralPeriodic;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	Command autonomousCommand;
	Command generalPeriodicCommand = new GeneralPeriodic();
	SendableChooser chooser;

	@Override
	public void robotInit() {
		chooser = new SendableChooser();
		chooser.addDefault("Low Bar + Batter High Goal", new LowBarBatterHighGoal());
		chooser.addObject("Low Bar + Batter Low Goal", new LowBarBatterLowGoal());
		chooser.addObject("Low Bar + Courtyard High Goal", new LowBarCourtyardHighGoal());
		chooser.addObject("Spy Bot + High Goal", new SpyBotHighGoal());
		chooser.addObject("Spy Bot + Low Goal", new SpyBotLowGoal());
		chooser.addObject("Reach Any Defense", new Reach());
		chooser.addObject("Damage Low Bar", new LowBar());
		chooser.addObject("Reach Any Defense", new Reach());
		chooser.addObject("Rough Terrain", new RoughTerrain());
		chooser.addObject("Rock Wall", new RockWall());
		chooser.addObject("Portcullis", new Portcullis());
		chooser.addObject("Cheval de Frise",new ChevaldeFrise());
		chooser.addObject("Moat", new Moat());
		chooser.addObject("Ramparts", new Ramparts());
		
		SmartDashboard.putData("Auto mode", chooser);
	}

	@Override
	public void disabledInit() {
		if (!generalPeriodicCommand.isRunning()) {
			generalPeriodicCommand.start();
		}
	}

	@Override
	public void disabledPeriodic() {
		// if autonomousCommand has options, check dashboard for updates;
		if (autonomousCommand != null && autonomousCommand instanceof AutoCommand) {
			((AutoCommand) autonomousCommand).update();
		}

		Scheduler.getInstance().run();

		// 21.18 in TalonSRX software manual
		HAL.leftFront.enableBrakeMode(false);
		HAL.leftRear.enableBrakeMode(false);
		HAL.rightFront.enableBrakeMode(false);
		HAL.rightRear.enableBrakeMode(false);
		HAL.shooterMotor.enableBrakeMode(false);
		HAL.hoodMotor.enableBrakeMode(true);
		HAL.armMotor.enableBrakeMode(true);
	}

	@Override
	public void autonomousInit() {
		if (!generalPeriodicCommand.isRunning()) {
			generalPeriodicCommand.start();
		}

		autonomousCommand = (Command) chooser.getSelected();

		// schedule the autonomous command (example)
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (!generalPeriodicCommand.isRunning()) {
			generalPeriodicCommand.start();
		}
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
