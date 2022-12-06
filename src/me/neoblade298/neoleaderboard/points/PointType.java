package me.neoblade298.neoleaderboard.points;

public interface PointType {
	public String getDisplay();
	
	public static PointType getPointType(String str) {
		str = str.toUpperCase();
		if (NationPointType.valueOf(str) != null) {
			return NationPointType.valueOf(str);
		}
		else {
			return PlayerPointType.valueOf(str);
		}
	}
}
