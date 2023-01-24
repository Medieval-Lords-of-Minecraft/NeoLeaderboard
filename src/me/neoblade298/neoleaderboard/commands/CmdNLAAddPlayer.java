package me.neoblade298.neoleaderboard.commands;

import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.neoblade298.neocore.bukkit.commands.CommandArgument;
import me.neoblade298.neocore.bukkit.commands.CommandArguments;
import me.neoblade298.neocore.bukkit.commands.Subcommand;
import me.neoblade298.neocore.bukkit.commands.SubcommandRunner;
import me.neoblade298.neocore.bukkit.util.BukkitUtil;
import me.neoblade298.neoleaderboard.points.PlayerPointType;
import me.neoblade298.neoleaderboard.points.PointsManager;

public class CmdNLAAddPlayer implements Subcommand {
	private static final CommandArguments args = new CommandArguments(Arrays.asList(new CommandArgument("player"),
			new CommandArgument("point type"), new CommandArgument("amount")));

	@Override
	public String getDescription() {
		return "Add player points to a player";
	}

	@Override
	public String getKey() {
		return "addplayer";
	}

	@Override
	public String getPermission() {
		return null;
	}

	@Override
	public SubcommandRunner getRunner() {
		return SubcommandRunner.BOTH;
	}
	
	@Override
	public CommandArguments getArgs() {
		return args;
	}

	@Override
	public void run(CommandSender s, String[] args) {
		PlayerPointType type = null;
		try {
			type = PlayerPointType.valueOf(args[1].toUpperCase());
		}
		catch (IllegalArgumentException ex) {
			BukkitUtil.msg(s, "&cInvalid type! Valid types are:");
			for (PlayerPointType t : PlayerPointType.values()) {
				BukkitUtil.msg(s, "&7- &c" + t);
			}
			return;
		}
		Player p = Bukkit.getPlayer(args[0]);
		if (p == null) {
			BukkitUtil.msg(s, "&cPlayer is not currently online!");
			return;
		}
		
		int amount = Integer.parseInt(args[2]);
		PointsManager.addPlayerPoints(p.getUniqueId(), amount, type);
		BukkitUtil.msg(s, "&7Successfully added &e" + amount + " &6" + type + " &7points to player &c" + p.getName());
	}
}
