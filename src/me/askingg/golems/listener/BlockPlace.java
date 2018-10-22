package me.askingg.golems.listener;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import me.askingg.golems.utils.Format;

public class BlockPlace implements Listener {

	@EventHandler
	public void blockPlace(BlockPlaceEvent e) {
		File cFile = new File("plugins/Golems", "config.yml");
		FileConfiguration cConf = YamlConfiguration.loadConfiguration(cFile);
		File lFile = new File("plugins/Golems", "locations.yml");
		FileConfiguration lConf = YamlConfiguration.loadConfiguration(lFile);
		Player p = e.getPlayer();
		Block b = e.getBlock();
		ItemStack i = e.getItemInHand();
		if (b.getType().equals(Material.MOB_SPAWNER)) {
			if (i.getItemMeta().isUnbreakable()) {
				if (i.hasItemMeta()) {
					if (i.getItemMeta().hasDisplayName()) {
						for (String s : cConf.getConfigurationSection("Spawners").getKeys(false)) {
							if (i.getItemMeta().getDisplayName()
									.equals(Format.color(cConf.getString("Spawners." + s + ".Spawner.Display")))) {
								String loc = e.getBlock().getWorld().getName() + ";" + e.getBlock().getX() + ","
										+ e.getBlock().getY() + "," + e.getBlock().getZ();
								try {
									lConf.set("Locations." + loc + ".Type", s);
									lConf.set("Locations." + loc + ".Owner", p.getName());
									lConf.save(lFile);
								} catch (Exception ex) {
								}
							}
						}
						CreatureSpawner s = (CreatureSpawner) b.getState();
						s.setSpawnedType(EntityType.IRON_GOLEM);
						s.update();
					}
				}
			}
		}
	}
}
