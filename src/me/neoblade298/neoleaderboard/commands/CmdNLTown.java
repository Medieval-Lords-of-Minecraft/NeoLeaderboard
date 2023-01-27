package me.neoblade298.neoleaderboard.commands;

import java.util.Iterator;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;

import me.neoblade298.neocore.shared.commands.Arg;
import me.neoblade298.neocore.bukkit.commands.Subcommand;
import me.neoblade298.neocore.shared.commands.SubcommandRunner;
import me.neoblade298.neocore.bukkit.util.Util;
import me.neoblade298.neoleaderboard.NeoLeaderboard;
import me.neoblade298.neoleaderboard.points.NationEntry;
import me.neoblade298.neoleaderboard.points.PlayerEntry;
import me.neoblade298.neoleaderboard.points.PointsManager;
import me.neoblade298.neoleaderboard.points.TownEntry;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention;
import net.md_5.bungee.api.chat.hover.content.Text;

public class CmdNLTown extends Subcommand {
	public CmdNLTown(String key, String desc, String perm, SubcommandRunner runner) {
		super(key, desc, perm, runner);
		args.add(new Arg("town"));
	}

	@Override
	public void run(CommandSender s, String[] args) {
		Town t = TownyUniverse.getInstance().getTown(args[0]);
		if (t == null) {
			Util.msg(s, "&cThis town doesn't exist!");
			return;
		}
		Nation n = t.getNationOrNull();
		if (n == null) {
			Util.msg(s, "&cThis town isn't part of a nation!");
			return;
		}
		new BukkitRunnable() {
			public void run() {
				NationEntry ne = PointsManager.getNationEntry(n.getUUID());
				TownEntry te = ne.getTownEntry(t.getUUID());
				Iterator<PlayerEntry> iter = te.getTopPlayers().descendingIterator();
				
				ComponentBuilder builder = new ComponentBuilder("§6§l>§8§m--------§c§l» §6Town Points: §e" + te.getTown().getName() + " §c§l«§8§m--------§6§l<");
				int i = 0;
				while (iter.hasNext() && i++ < 10) {
					PlayerEntry e = iter.next();
					String name = e.getDisplay();
					// double effective = PointsManager.calculateEffectivePoints(ne, e.getContributed());
					builder.append("\n§6§l" + i + ". §e" + name + " §7- §f" + PointsManager.formatPoints(e.getContributed()), FormatRetention.NONE)
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§fClick for details:\n§e/nl " + name)))
					.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/nl " + name));
				}
				s.spigot().sendMessage(builder.create());
			}
		}.runTaskAsynchronously(NeoLeaderboard.inst());
	}
}
