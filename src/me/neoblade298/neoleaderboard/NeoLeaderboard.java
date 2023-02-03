package me.neoblade298.neoleaderboard;

import java.time.LocalDateTime;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.neoblade298.neocore.bukkit.InstanceType;
import me.neoblade298.neocore.bukkit.NeoCore;
import me.neoblade298.neocore.bukkit.commands.SubcommandManager;
import me.neoblade298.neocore.bukkit.scheduler.ScheduleInterval;
import me.neoblade298.neocore.bukkit.scheduler.SchedulerAPI;
import me.neoblade298.neocore.shared.commands.SubcommandRunner;
import me.neoblade298.neoleaderboard.commands.*;
import me.neoblade298.neoleaderboard.listeners.InstanceListener;
import me.neoblade298.neoleaderboard.listeners.PointsListener;
import me.neoblade298.neoleaderboard.listeners.TownyListener;
import me.neoblade298.neoleaderboard.points.PointsManager;
import net.md_5.bungee.api.ChatColor;

public class NeoLeaderboard extends JavaPlugin {
	private static NeoLeaderboard inst;
	
	public void onEnable() {
		Bukkit.getServer().getLogger().info("NeoLeaderboard Enabled");
		inst = this;
		
		if (NeoCore.getInstanceType() != InstanceType.SESSIONS) {
			NeoCore.registerIOComponent(this, new PointsManager(), "PointsManager");

			PointsManager.initialize();
			initCommands();
			
			Bukkit.getPluginManager().registerEvents(new PointsListener(), this);
			Bukkit.getPluginManager().registerEvents(new TownyListener(), this);

			SchedulerAPI.scheduleRepeating("Leaderboard-finalize", ScheduleInterval.DAILY, () -> {
				if (LocalDateTime.now().getDayOfMonth() == 1) {
					PointsManager.finalizeScores();
				}
			});
		}
		else if (NeoCore.getInstanceType() == InstanceType.SESSIONS) {
			Bukkit.getPluginManager().registerEvents(new InstanceListener(), this);
		}
		
	}
	
	public void onDisable() {
	    org.bukkit.Bukkit.getServer().getLogger().info("NeoLeaderboard Disabled");
	    super.onDisable();
	}
	
	private void initCommands() {
		SubcommandManager mngr = new SubcommandManager("nations", null, ChatColor.RED, this);
		mngr.register(new CmdNations("", null, null, SubcommandRunner.BOTH));
		
		mngr = new SubcommandManager("nl", null, ChatColor.RED, this);
		mngr.register(new CmdNLNation("nation", null, null, SubcommandRunner.BOTH));
		mngr.register(new CmdNLTown("town", null, null, SubcommandRunner.BOTH));
		mngr.register(new CmdNLBase("", null, null, SubcommandRunner.BOTH));
		mngr.registerCommandList("help");

		mngr = new SubcommandManager("nlc", null, ChatColor.RED, this);
		mngr.register(new CmdNLCBase("", null, null, SubcommandRunner.BOTH));
		mngr.register(new CmdNLCTop("top", null, null, SubcommandRunner.BOTH));
		mngr.register(new CmdNLCNation("nation", null, null, SubcommandRunner.BOTH));
		mngr.register(new CmdNLCTown("town", null, null, SubcommandRunner.BOTH));
		mngr.registerCommandList("help");

		mngr = new SubcommandManager("nladmin", "neoleaderboard.admin", ChatColor.DARK_RED, this);
		mngr.registerCommandList("");
		mngr.register(new CmdNLAAddPlayer("addplayer", "Add points to a player", null, SubcommandRunner.BOTH));
		mngr.register(new CmdNLAAddNation("addnation", "Add points to a nation", null, SubcommandRunner.BOTH));
		mngr.register(new CmdNLAFinalize("finalize", "Finalize nation points for the month", null, SubcommandRunner.BOTH));
		mngr.register(new CmdNLAReset("reset", "Reset all points", null, SubcommandRunner.BOTH));
	}
	
	public static NeoLeaderboard inst() {
		return inst;
	}
}
