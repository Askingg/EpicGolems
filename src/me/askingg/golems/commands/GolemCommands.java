package me.askingg.golems.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.askingg.golems.main.Main;
import me.askingg.golems.main.ShopGUI;
import me.askingg.golems.utils.Message;
import me.askingg.golems.utils.Misc;

public class GolemCommands implements CommandExecutor {

	@SuppressWarnings("unused")
	private Main plugin;
	private Misc misc;

	public GolemCommands(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("golems").setExecutor(this);
	}

	@SuppressWarnings("static-access")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			Message.sender("&8(&e/Golems&8) &l» &fView the help list", sender);
			Message.sender("&8(&e/Golems Shop&8) &l» &fOpen the spawner shop", sender);
			Message.sender("&8(&e/Golems List&8) &l» &fList all available spawner types", sender);
			Message.sender("&8(&e/Golems Give&8) &l» &fGive a player a spawner or two", sender);
			Message.sender("&8(&e/Golems Dev&8) &l» &fView the developer", sender);
			return true;
		} else {
			if (args[0].equalsIgnoreCase("dev")) {
				Message.sender("&8(&fDeveloper&8) &3&l» &8&l&oAsk&7&l&oin&f&l&ogg", sender);
				return true;
			}
			if (args[0].equals("shop")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					File file = new File("plugins/Golems", "config.yml");
					FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
					if (conf.getInt("Shop.Size") >= 27 && conf.getInt("Shop.Size") <= 54) {
						if (sender.hasPermission(conf.getString("Permissions.Commands.Shop"))) {
							ShopGUI.open.add(p);
							p.openInventory(ShopGUI.shop(p));
						}
					} else {
						Message.player("&cThe shop size is invalid, pleace use a minimum of 27 and a maximum of 54", p);
						Message.console("&cThe shop size is invalid, pleace use a minimum of 27 and a maximum of 54");
					}
				} else {
					Message.sender("&cOnly players may use this command", sender);
				}
			}
			if (args[0].equalsIgnoreCase("list")) {
				File cFile = new File("plugins/Golems", "config.yml");
				FileConfiguration cConf = YamlConfiguration.loadConfiguration(cFile);
				if (sender instanceof ConsoleCommandSender
						|| sender.hasPermission(cConf.getString("Permissions.Commands.List"))) {
					if (!cConf.getString("Messages.ListHeader").equals("")) {
						Message.sender(cConf.getString("Messages.ListHeader"), sender);
					}
					for (String s : cConf.getConfigurationSection("Spawners").getKeys(false)) {
						Message.sender(cConf.getString("Messages.ListFormat").replace("%spawner%", s), sender);
					}
					if (!cConf.getString("Messages.ListFooter").equals("")) {
						Message.sender(cConf.getString("Messages.ListFooter"), sender);
					}
				} else {
					Message.sender(cConf.getString("Messages.NoPermission"), sender);
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("give")) { // give player spawner amount
				File cFile = new File("plugins/Golems", "config.yml");
				FileConfiguration cConf = YamlConfiguration.loadConfiguration(cFile);
				if (sender instanceof ConsoleCommandSender
						|| sender.hasPermission(cConf.getString("Permissions.Commands.Give"))) {
					if (args.length == 4) {
						Player p = Bukkit.getPlayer(args[1]);
						if (p == null) {
							Message.sender(cConf.getString("Messages.InvalidPlayer").replace("%target%", args[1]),
									sender);
							return false;
						} else {
							Integer x = null;
							try {
								x = Integer.parseInt(args[3]);
							} catch (Exception ex) {
								Message.sender(
										cConf.getString("Messages.InvalidInteger").replaceAll("%invalid%", args[3]),
										sender);
								return false;
							}
							for (String s : cConf.getConfigurationSection("Spawners").getKeys(false)) {
								if (args[2].equalsIgnoreCase(s)) {
									try {
										Message.player(cConf.getString("Messages.ReceiveSpawner")
												.replace("%amount%", x.toString()).replace("%spawner%", args[2]), p);
										Message.sender(cConf.getString("Messages.GaveSpawner")
												.replace("%player%", p.getName()).replace("%amount%", x.toString())
												.replace("%spawner%", args[2]), sender);
										p.getInventory().addItem(misc.Spawner(s, x));
										break;
									} catch (Exception ex) {
										ex.printStackTrace();
									}
								}
							}
						}
					} else {
						Message.sender(cConf.getString("Messages.GiveUsage"), sender);
					}
				} else {
					Message.sender(cConf.getString("Messages.NoPermission"), sender);
				}
			}
		}

		return false;
	}
}
