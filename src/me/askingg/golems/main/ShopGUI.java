package me.askingg.golems.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.askingg.golems.utils.Format;
import me.askingg.golems.utils.Message;
import me.askingg.golems.utils.Misc;
import net.milkbowl.vault.economy.EconomyResponse;

public class ShopGUI implements Listener {
	public static List<Player> open = new ArrayList<Player>();

	public static Inventory shop(Player player) {
		File file = new File("plugins/Golems", "config.yml");
		FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
		Inventory inv = Bukkit.createInventory(null, conf.getInt("Shop.Size"),
				Format.color(conf.getString("Shop.Title")));
		Integer size = conf.getInt("Shop.Size");
		ItemStack b = new ItemStack(Material.STAINED_GLASS_PANE);
		b.setDurability((byte) conf.getInt("Shop.BorderColorId"));
		ItemMeta bm = b.getItemMeta();
		bm.setDisplayName(Format.color("&f"));
		b.setItemMeta(bm);
		inv.setItem(0, b);
		inv.setItem(1, b);
		inv.setItem(2, b);
		inv.setItem(3, b);
		inv.setItem(4, b);
		inv.setItem(5, b);
		inv.setItem(6, b);
		inv.setItem(7, b);
		inv.setItem(8, b);
		inv.setItem(9, b);
		inv.setItem(17, b);
		inv.setItem(18, b);
		inv.setItem(26, b);
		if (size == 27) {
			inv.setItem(19, b);
			inv.setItem(20, b);
			inv.setItem(21, b);
			inv.setItem(22, b);
			inv.setItem(23, b);
			inv.setItem(24, b);
			inv.setItem(25, b);
		}
		if (size == 36) {
			inv.setItem(27, b);
			inv.setItem(28, b);
			inv.setItem(29, b);
			inv.setItem(30, b);
			inv.setItem(31, b);
			inv.setItem(32, b);
			inv.setItem(33, b);
			inv.setItem(34, b);
			inv.setItem(35, b);
		}
		if (size == 45) {
			inv.setItem(27, b);
			inv.setItem(35, b);
			inv.setItem(36, b);
			inv.setItem(37, b);
			inv.setItem(38, b);
			inv.setItem(39, b);
			inv.setItem(40, b);
			inv.setItem(41, b);
			inv.setItem(42, b);
			inv.setItem(43, b);
			inv.setItem(44, b);
		}
		if (size == 54) {
			inv.setItem(27, b);
			inv.setItem(35, b);
			inv.setItem(36, b);
			inv.setItem(44, b);
			inv.setItem(45, b);
			inv.setItem(46, b);
			inv.setItem(47, b);
			inv.setItem(48, b);
			inv.setItem(49, b);
			inv.setItem(50, b);
			inv.setItem(51, b);
			inv.setItem(52, b);
			inv.setItem(53, b);
		}
		for (String s : conf.getConfigurationSection("Spawners").getKeys(false)) {
			ItemStack i = new ItemStack(Material.MOB_SPAWNER);
			ItemMeta m = i.getItemMeta();
			List<String> l = new ArrayList<String>();
			m.setDisplayName(Format.color(conf.getString("Spawners." + s + ".Spawner.Display")));
			l.add(Format.color(""));
			for (String st : conf.getStringList("Shop.SpawnerLore")) {
				l.add(Format.color(st).replace("%cost%", conf.getString("Spawners." + s + ".Spawner.Cost")));
			}
			m.setLore(l);
			i.setItemMeta(m);
			inv.addItem(i);
		}
		return inv;
	}

	@EventHandler
	public void invClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if (open.contains(p)) {
			open.remove(p);
		}
	}

	@EventHandler
	public void invClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (open.contains(p)) {
			File file = new File("plugins/Golems", "config.yml");
			FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
			e.setCancelled(true);
			ItemStack i = e.getCurrentItem();
			ItemMeta m = i.getItemMeta();
			if (i.getType().equals(Material.MOB_SPAWNER)) {
				String sp = null;
				String n = m.getDisplayName();
				Double c = null;
				for (String s : conf.getConfigurationSection("Spawners").getKeys(false)) {
					if (Format.color(conf.getString("Spawners." + s + ".Spawner.Display")).equals(n)) {
						sp = s;
						c = conf.getDouble("Spawners." + s + ".Spawner.Cost");
						break;
					}
				}
				EconomyResponse r = Main.eco.withdrawPlayer(p, c);
				if (r.transactionSuccess()) {
					p.getInventory().addItem(Misc.Spawner(sp, 1));
					Message.player(conf.getString("Messages.PurchaseSpawner").replace("%spawner%", sp), p);
				} else {
					Message.player(conf.getString("Messages.NotEnoughMoney"), p);
				}
			}
		}
	}
}
