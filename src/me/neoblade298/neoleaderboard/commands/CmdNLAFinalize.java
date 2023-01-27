package me.neoblade298.neoleaderboard.commands;

import org.bukkit.command.CommandSender;

import me.neoblade298.neocore.bukkit.commands.Subcommand;
import me.neoblade298.neocore.shared.commands.SubcommandRunner;
import me.neoblade298.neoleaderboard.points.PointsManager;

public class CmdNLAFinalize extends Subcommand {
	public CmdNLAFinalize(String key, String desc, String perm, SubcommandRunner runner) {
		super(key, desc, perm, runner);
	}

	@Override
	public void run(CommandSender s, String[] args) {
		PointsManager.finalizeScores();
	}
}
