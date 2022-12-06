package me.neoblade298.neoleaderboard.previous;

import java.util.UUID;

import org.bukkit.Bukkit;

import me.neoblade298.neoleaderboard.points.PointType;

public class PreviousEntry implements Comparable<PreviousEntry> {
	private UUID uuid;
	private String name;
	private PointType category;
	private double points;
	private long date;
	
	public PreviousEntry(UUID uuid, String name, PointType category, double points) {
		this.uuid = uuid;
		this.name = name;
		this.category = category;
		this.points = points;
	}

	public PreviousEntry(UUID uuid, PointType category, double points) {
		this.uuid = uuid;
		this.name = Bukkit.getOfflinePlayer(uuid).getName();
		this.category = category;
		this.points = points;
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public PointType getCategory() {
		return category;
	}

	public double getPoints() {
		return points;
	}
	
	public void setDate(long date) {
		this.date = date;
	}
	
	public long getDate() {
		return date;
	}
	
	@Override
	public int compareTo(PreviousEntry e) {
		if (this.points > e.points) {
			return 1;
		}
		else if (this.points < e.points) {
			return -1;
		}
		else {
			// Name should be sorted by ascending when points are descending
			return e.name.compareTo(this.name);
		}
	}
}
