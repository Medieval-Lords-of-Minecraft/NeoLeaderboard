package me.neoblade298.neoleaderboard.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.neoblade298.neocore.shared.commands.Arg;
import me.neoblade298.neocore.bukkit.commands.Subcommand;
import me.neoblade298.neocore.shared.commands.SubcommandRunner;
import me.neoblade298.neocore.bukkit.util.Util;
import me.neoblade298.neoleaderboard.points.PlayerPointType;
import me.neoblade298.neoleaderboard.points.PointsManager;

public class CmdNLAAddPlayer extends Subcommand {
	public CmdNLAAddPlayer(String key, String desc, String perm, SubcommandRunner runner) {
		super(key, desc, perm, runner);
		args.add(new Arg("player"),	new Arg("point type"), new Arg("amount"));
	}

	@Override
	public void run(CommandSender s, String[] args) {
		PlayerPointType type = null;
		try {
			type = PlayerPointType.valueOf(args[1].toUpperCase());
		}
		catch (IllegalArgumentException ex) {
			Util.msg(s, "&cInvalid type! Valid types are:");
			for (PlayerPointType t : PlayerPointType.values()) {
				Util.msg(s, "&7- &c" + t);
			}
			return;
		}
		Player p = Bukkit.getPlayer(args[0]);
		if (p == null) {
			Util.msg(s, "&cPlayer is not currently online!");
			return;
		}
		
		int amount = Integer.parseInt(args[2]);
		PointsManager.addPlayerPoints(p.getUniqueId(), amount, type);
		Util.msg(s, "&7Successfully added &e" + amount + " &6" + type + " &7points to player &c" + p.getName());
	}
}
