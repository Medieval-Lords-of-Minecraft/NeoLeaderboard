package me.neoblade298.neoleaderboard.commands;

import org.bukkit.command.CommandSender;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Nation;

import me.neoblade298.neocore.shared.commands.Arg;
import me.neoblade298.neocore.bukkit.commands.Subcommand;
import me.neoblade298.neocore.shared.commands.SubcommandRunner;
import me.neoblade298.neocore.bukkit.util.Util;
import me.neoblade298.neoleaderboard.points.NationPointType;
import me.neoblade298.neoleaderboard.points.PointsManager;

public class CmdNLAAddNation extends Subcommand {
	public CmdNLAAddNation(String key, String desc, String perm, SubcommandRunner runner) {
		super(key, desc, perm, runner);
		args.add(new Arg("nation"), new Arg("point type"), new Arg("amount"));
	}

	@Override
	public void run(CommandSender s, String[] args) {
		NationPointType type = null;
		try {
			type = NationPointType.valueOf(args[1].toUpperCase());
		}
		catch (IllegalArgumentException ex) {
			Util.msg(s, "&cInvalid type! Valid types are:");
			for (NationPointType t : NationPointType.values()) {
				Util.msg(s, "&7- &c" + t);
			}
			return;
		}
		Nation n = TownyAPI.getInstance().getNation(args[0]);
		if (n == null) {
			Util.msg(s, "&cNation does not exist!");
			return;
		}
		
		int amount = Integer.parseInt(args[2]);
		PointsManager.addNationPoints(n.getUUID(), amount, type);
		Util.msg(s, "&7Successfully added &e" + amount + " &6" + type + " &7points to nation &c" + n.getName());
	}
}
