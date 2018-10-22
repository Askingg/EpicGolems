package me.askingg.golems.listener;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.askingg.golems.utils.Misc;

public class BlockBreak implements Listener {
	private Misc misc;

	@SuppressWarnings("static-access")
	@EventHandler
	public void blockBreak(BlockBreakEvent e) {
		Block b = e.getBlock();
		Location l = b.getLocation();
		if (b.getType().equals(Material.MOB_SPAWNER)) {
			File lFile = new File("plugins/Golems", "locations.yml");
			FileConfiguration lConf = YamlConfiguration.loadConfiguration(lFile);
			String loc = e.getBlock().getWorld().getName() + ";" + e.getBlock().getX() + "," + e.getBlock().getY() + ","
					+ e.getBlock().getZ();
			for (String s : lConf.getConfigurationSection("Locations").getKeys(false)) {
				if (loc.equals(s)) {
					String m = lConf.getString("Locations." + s + ".Type");
					b.getWorld().dropItemNaturally(l, misc.Spawner(m, 1));
					b.setType(Material.AIR);
					try {
						lConf.set("Locations." + s, null);
						lConf.save(lFile);
					} catch (Exception ex) {
					}
					break;
				}
			}
		}
	}
}