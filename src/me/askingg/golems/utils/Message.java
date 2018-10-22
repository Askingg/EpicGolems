package me.askingg.golems.utils;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Message {

	public static void console(String msg) {
		Bukkit.getConsoleSender().sendMessage(Format.color(msg));
	}

	public static void player(String msg, Player player) {
		File file = new File("plugins/Golems", "config.yml");
		FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
		player.sendMessage(Format.color(msg).replace("%prefix%", Format.color(conf.getString("Prefix"))));
	}

	public static void sender(String msg, CommandSender sender) {
		File file = new File("plugins/Golems", "config.yml");
		FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
		sender.sendMessage(Format.color(msg).replace("%prefix%", Format.color(conf.getString("Prefix"))));
	}
}
