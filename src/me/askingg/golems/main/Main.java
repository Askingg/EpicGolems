package me.askingg.golems.main;

import java.io.File;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.askingg.golems.commands.GolemCommands;
import me.askingg.golems.listener.BlockBreak;
import me.askingg.golems.listener.BlockPlace;
import me.askingg.golems.listener.EntityDeath;
import me.askingg.golems.listener.SpawnerSpawn;
import me.askingg.golems.utils.Message;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	@SuppressWarnings("unused")
	private GolemCommands gcmd;
	public static Economy eco = null;

	public void onEnable() {
		if (!setupEconomy()) {
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		createFolders();
		Message.console("&fPlugin successfully loaded");
		getServer().getPluginManager().registerEvents(new ShopGUI(), this);
		getServer().getPluginManager().registerEvents(new BlockPlace(), this);
		getServer().getPluginManager().registerEvents(new SpawnerSpawn(), this);
		getServer().getPluginManager().registerEvents(new EntityDeath(), this);
		getServer().getPluginManager().registerEvents(new BlockBreak(), this);
		gcmd = new GolemCommands(this);
	}

	private void createFolders() {
		File folder = new File("plugins/Golems");
		if (!folder.exists()) {
			folder.mkdirs();
			Message.console("&fThe '&bGolems&f' folder was successfully created");
		}
		File lFile = new File("plugins/Golems", "locations.yml");
		FileConfiguration lConf = YamlConfiguration.loadConfiguration(lFile);
		if (!lFile.exists()) {
			try {
				lFile.createNewFile();
				lConf.options().header("Location data file\nDo not edit!");
				lConf.createSection("Locations");
				lConf.save(lFile);
				Message.console("&fThe '&blocations.yml&f' file was successfully created");
			} catch (Exception e) {
			}
		}
		File cFile = new File("plugins/Golems", "config.yml");
		FileConfiguration cConf = YamlConfiguration.loadConfiguration(cFile);
		if (!(cFile.exists())) {
			try {
				cFile.createNewFile();
				cConf.set("Prefix", "&8(&cGolems&8) &l»");
				cConf.set("Shop.Title", "&8&l« &cGolems Shop &8&l»");
				cConf.set("Shop.Size", 27);
				cConf.set("Shop.BorderColorId", 7);
				cConf.set("Shop.SpawnerLore", Arrays.asList("", "&fCost &8&l» &b$%cost%"));
				cConf.set("Messages.PurchaseSpawner", "%prefix% &fYou purchased a(n) &b%spawner% &fgolem spawner");
				cConf.set("Messages.ReceiveSpawner", "&a+ %amount% &f%spawner% &fgolem spawner(s)");
				cConf.set("Messages.GaveSpawner", "%prefix% &fYou gave &b%amount% %spawner% spawner(s)&f to &b%player%");
				cConf.set("Messages.NoPermission", "%prefix% &fSorry, but you don't have permission to do this");
				cConf.set("Messages.NotEnoughMoney", "%prefix% &fSorry, but you don't have enough money");
				cConf.set("Messages.InvalidPlayer", "%prefix% &fSorry, but &c%target%&f is an invalid player");
				cConf.set("Messages.InvalidInteger", "%prefix% &fSorry, but &c%invalid%&f is an invalid integer");
				cConf.set("Messages.GiveUsage", "%prefix% &e/Golems Give Player Spawner Amount");
				cConf.set("Messages.ListHeader", "%prefix% &fAvailable Golem Spawners &8&l»");
				cConf.set("Messages.ListFormat", "&8&l» &f%spawner%");
				cConf.set("Messages.ListFooter", "");
				cConf.set("Permissions.Commands.Shop", "golems.spawners.shop");
				cConf.set("Permissions.Commands.Give", "golems.spawners.give");
				cConf.set("Permissions.Commands.List", "golems.spawners.list");
				cConf.set("Spawners.Coal.Spawner.Display", "&8&l« &7Coal Golem Spawner &8&l»");
				cConf.set("Spawners.Coal.Spawner.Lore", Arrays.asList());
				cConf.set("Spawners.Coal.Spawner.Glow", false);
				cConf.set("Spawners.Coal.Spawner.Cost", 500);
				cConf.set("Spawners.Coal.Entity.Name", "&7Coal Golem");
				cConf.set("Spawners.Coal.Entity.NameAlwaysVisible", false);
				cConf.set("Spawners.Coal.Death.Item.Display", "");
				cConf.set("Spawners.Coal.Death.Item.Type", "COAL");
				cConf.set("Spawners.Coal.Death.Item.Id", 0);
				cConf.set("Spawners.Coal.Death.Item.Lore", Arrays.asList());
				cConf.set("Spawners.Coal.Death.Item.Enchants", Arrays.asList());
				cConf.set("Spawners.Coal.Death.Flower.Enabled", true);
				cConf.set("Spawners.Coal.Death.Flower.Type", "RED_ROSE");
				cConf.set("Spawners.Coal.Death.Flower.Id", 8);
				cConf.set("Spawners.Iron.OnlyOwnerCanBreak", false);
				cConf.set("Spawners.Iron.Spawner.Display", "&8&l« &fIron Golem Spawner &8&l»");
				cConf.set("Spawners.Iron.Spawner.Lore", Arrays.asList());
				cConf.set("Spawners.Iron.Spawner.Glow", false);
				cConf.set("Spawners.Iron.Spawner.Cost", 1000);
				cConf.set("Spawners.Iron.Entity.Name", "&fIron Golem");
				cConf.set("Spawners.Iron.Entity.NameAlwaysVisible", false);
				cConf.set("Spawners.Iron.Death.Item.Display", "");
				cConf.set("Spawners.Iron.Death.Item.Type", "IRON_INGOT");
				cConf.set("Spawners.Iron.Death.Item.Id", 0);
				cConf.set("Spawners.Iron.Death.Item.Lore", Arrays.asList());
				cConf.set("Spawners.Iron.Death.Item.Enchants", Arrays.asList());
				cConf.set("Spawners.Iron.Death.Flower.Enabled", true);
				cConf.set("Spawners.Iron.Death.Flower.Type", "RED_ROSE");
				cConf.set("Spawners.Iron.Death.Flower.Id", 0);
				cConf.set("Spawners.Gold.OnlyOwnerCanBreak", false);
				cConf.set("Spawners.Gold.Spawner.Display", "&8&l« &6Gold Golem Spawner &8&l»");
				cConf.set("Spawners.Gold.Spawner.Lore", Arrays.asList());
				cConf.set("Spawners.Gold.Spawner.Glow", false);
				cConf.set("Spawners.Gold.Spawner.Cost", 2500);
				cConf.set("Spawners.Gold.Entity.Name", "&6Gold Golem");
				cConf.set("Spawners.Gold.Entity.NameAlwaysVisible", false);
				cConf.set("Spawners.Gold.Death.Item.Display", "");
				cConf.set("Spawners.Gold.Death.Item.Type", "GOLD_INGOT");
				cConf.set("Spawners.Gold.Death.Item.Id", 0);
				cConf.set("Spawners.Gold.Death.Item.Lore", Arrays.asList());
				cConf.set("Spawners.Gold.Death.Item.Enchants", Arrays.asList());
				cConf.set("Spawners.Gold.Death.Flower.Enabled", true);
				cConf.set("Spawners.Gold.Death.Flower.Type", "YELLOW_FLOWER");
				cConf.set("Spawners.Gold.Death.Flower.Id", 0);
				cConf.set("Spawners.Diamond.OnlyOwnerCanBreak", false);
				cConf.set("Spawners.Diamond.Spawner.Display", "&8&l« &bDiamond Golem Spawner &8&l»");
				cConf.set("Spawners.Diamond.Spawner.Lore", Arrays.asList());
				cConf.set("Spawners.Diamond.Spawner.Glow", false);
				cConf.set("Spawners.Diamond.Spawner.Cost", 5000);
				cConf.set("Spawners.Diamond.Entity.Name", "&bDiamond Golem");
				cConf.set("Spawners.Diamond.Entity.NameAlwaysVisible", false);
				cConf.set("Spawners.Diamond.Death.Item.Display", "");
				cConf.set("Spawners.Diamond.Death.Item.Type", "DIAMOND");
				cConf.set("Spawners.Diamond.Death.Item.Id", 0);
				cConf.set("Spawners.Diamond.Death.Item.Lore", Arrays.asList());
				cConf.set("Spawners.Diamond.Death.Item.Enchants", Arrays.asList());
				cConf.set("Spawners.Diamond.Death.Flower.Enabled", true);
				cConf.set("Spawners.Diamond.Death.Flower.Type", "RED_ROSE");
				cConf.set("Spawners.Diamond.Death.Flower.Id", 1);
				cConf.set("Spawners.Emerald.OnlyOwnerCanBreak", false);
				cConf.set("Spawners.Emerald.Spawner.Display", "&8&l« &aEmerald Golem Spawner &8&l»");
				cConf.set("Spawners.Emerald.Spawner.Lore", Arrays.asList());
				cConf.set("Spawners.Emerald.Spawner.Glow", false);
				cConf.set("Spawners.Emerald.Spawner.Cost", 10000);
				cConf.set("Spawners.Emerald.Entity.Name", "&aEmerald Golem");
				cConf.set("Spawners.Emerald.Entity.NameAlwaysVisible", false);
				cConf.set("Spawners.Emerald.Death.Item.Display", "");
				cConf.set("Spawners.Emerald.Death.Item.Type", "EMERALD");
				cConf.set("Spawners.Emerald.Death.Item.Id", 0);
				cConf.set("Spawners.Emerald.Death.Item.Lore", Arrays.asList());
				cConf.set("Spawners.Emerald.Death.Item.Enchants", Arrays.asList());
				cConf.set("Spawners.Emerald.Death.Flower.Enabled", true);
				cConf.set("Spawners.Emerald.Death.Flower.Type", "RED_ROSE");
				cConf.set("Spawners.Emerald.Death.Flower.Id", 2);
				cConf.set("Spawners.Token.OnlyOwnerCanBreak", true);
				cConf.set("Spawners.Token.Spawner.Display", "&8&l« &3EToken Golem Spawner &8&l»");
				cConf.set("Spawners.Token.Spawner.Lore", Arrays.asList("", "&cThis spawner will", "&cspawn Token golems", "", "&7(You're not just limited to ores)"));
				cConf.set("Spawners.Token.Spawner.Glow", true);
				cConf.set("Spawners.Token.Spawner.Cost", 50000);
				cConf.set("Spawners.Token.Entity.Name", "&3EToken Golem");
				cConf.set("Spawners.Token.Entity.NameAlwaysVisible", true);
				cConf.set("Spawners.Token.Death.Item.Display", "&aEnchantment Token");
				cConf.set("Spawners.Token.Death.Item.Type", "SLIME_BALL");
				cConf.set("Spawners.Token.Death.Item.Id", 0);
				cConf.set("Spawners.Token.Death.Item.Lore", Arrays.asList("&f", "&7Enchantment Token", "&7(You're not just limited to ores)"));
				cConf.set("Spawners.Token.Death.Item.Enchants", Arrays.asList("durability;5"));
				cConf.set("Spawners.Token.Death.Flower.Enabled", false);
				cConf.set("Spawners.Token.Death.Flower.Type", "RED_ROSE");
				cConf.set("Spawners.Token.Death.Flower.Id", 0);
				cConf.save(cFile);
				Message.console("&fThe '&blocations.yml&f' file was successfully created");
			} catch (Exception e) {
			}
		}
	}

	public static boolean setupEconomy() {
		if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		eco = rsp.getProvider();
		return eco != null;
	}
}
