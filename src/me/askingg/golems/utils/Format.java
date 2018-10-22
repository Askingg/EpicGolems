package me.askingg.golems.utils;

import org.bukkit.ChatColor;

public class Format {

	public static String color(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}
}
