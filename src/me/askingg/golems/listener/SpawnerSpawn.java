package me.askingg.golems.listener;

import java.io.File;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;

import me.askingg.golems.utils.Format;

public class SpawnerSpawn implements Listener {

	@EventHandler
	public void spawnerSpwaner(SpawnerSpawnEvent e) {
		File file = new File("plugins/Golems", "config.yml");
		FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
		File lFile = new File("plugins/Golems", "locations.yml");
		FileConfiguration lConf = YamlConfiguration.loadConfiguration(lFile);
		CreatureSpawner s = (CreatureSpawner) e.getSpawner().getBlock().getState();
		String loc = s.getBlock().getWorld().getName() + ";" + s.getBlock().getX() + "," + s.getBlock().getY() + ","
				+ s.getBlock().getZ();
		if (lConf.getString("Locations." + loc + ".Type") != null) {
			String m = lConf.getString("Locations." + loc + ".Type");
			e.getEntity().setCustomName(Format.color(conf.getString("Spawners." + m + ".Entity.Name")));
			if (conf.getBoolean("Spawners." + m + ".Entity.NameAlwaysVisible") == true) {
				e.getEntity().setCustomNameVisible(true);
			}
			s.setSpawnedType(EntityType.IRON_GOLEM);
			s.update();
			return;
		}
	}
}
