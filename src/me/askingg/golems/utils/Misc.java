package me.askingg.golems.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Misc {

	public static ItemStack Spawner(String type, Integer amount) {
		File cFile = new File("plugins/Golems", "config.yml");
		FileConfiguration cConf = YamlConfiguration.loadConfiguration(cFile);
		ItemStack i = new ItemStack(Material.MOB_SPAWNER, amount);
		ItemMeta m = i.getItemMeta();
		List<String> l = new ArrayList<String>();
		m.setDisplayName(Format.color(cConf.getString("Spawners." + type + ".Spawner.Display")));
		if (cConf.getBoolean("Spawners." + type + ".Spawner.Glow") == true) {
			m.addEnchant(Enchantment.LUCK, 0, true);
			m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		if (cConf.getStringList("Spawners." + type + ".Spawner.Lore").size() > 0) {
			for (String st : cConf.getStringList("Spawners." + type + ".Spawner.Lore")) {
				l.add(Format.color(st));
			}
		}
		m.setUnbreakable(true);
		m.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		m.setLore(l);
		i.setItemMeta(m);
		return i;
	}
}
