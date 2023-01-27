package me.neoblade298.neoleaderboard.commands;

import org.bukkit.command.CommandSender;

import me.neoblade298.neocore.bukkit.commands.Subcommand;
import me.neoblade298.neocore.shared.commands.SubcommandRunner;
import me.neoblade298.neoleaderboard.points.NationPointType;
import me.neoblade298.neoleaderboard.points.PlayerPointType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention;
import net.md_5.bungee.api.chat.hover.content.Text;

public class CmdNLCBase extends Subcommand {
	public CmdNLCBase(String key, String desc, String perm, SubcommandRunner runner) {
		super(key, desc, perm, runner);
	}

	@Override
	public void run(CommandSender s, String[] args) {
			ComponentBuilder builder = new ComponentBuilder("§6§l>§8§m--------§c§l» Nation Categories «§8§m--------§6§l<");
			for (NationPointType type : NationPointType.values()) {
				builder.append("\n §6§l» §e" + type.getDisplay(), FormatRetention.NONE)
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("/nlc top " + type)))
				.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/nlc top " + type));
			}
			builder.append("\n§6§l>§8§m--------§c§l» Player Categories «§8§m--------§6§l<", FormatRetention.NONE);
			for (PlayerPointType type : PlayerPointType.values()) {
				builder.append("\n §6§l» §e" + type.getExtendedDisplay(), FormatRetention.NONE)
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("/nlc top " + type)))
				.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/nlc top " + type));
			}
			s.spigot().sendMessage(builder.create());
	}
}
