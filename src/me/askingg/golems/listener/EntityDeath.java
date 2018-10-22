package me.askingg.golems.listener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.askingg.golems.utils.Format;

public class EntityDeath implements Listener {

	@EventHandler
	public void entityDeath(EntityDeathEvent e) {
		File file = new File("plugins/Golems", "config.yml");
		FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
		Entity m = e.getEntity();
		for (String s : conf.getConfigurationSection("Spawners").getKeys(false)) {
			if (m.getName().equals(Format.color(conf.getString("Spawners." + s + ".Entity.Name")))) {
				for (ItemStack i : e.getDrops()) {
					if (i.getType().equals(Material.IRON_INGOT)) {
						ItemMeta im = i.getItemMeta();
						List<String> l = new ArrayList<String>();
						i.setType(Material.getMaterial(conf.getString("Spawners." + s + ".Death.Item.Type")));
						im.setDisplayName(Format.color(conf.getString("Spawners." + s + ".Death.Item.Display")));
						if (!conf.getStringList("Spawners." + s + ".Death.Item.Lore").isEmpty()) {
							for (String st : conf.getStringList("Spawners." + s + ".Death.Item.Lore")) {
								l.add(Format.color(st));
							}
						}
						if (!conf.getStringList("Spawners." + s + ".Death.Item.Enchants").isEmpty()) {
							for (String st : conf.getStringList("Spawners." + s + ".Death.Item.Enchants")) {
								String[] ench = st.split(";");
								im.addEnchant(Enchantment.getByName(ench[0].toUpperCase()), Integer.parseInt(ench[1]), true);
							}
						}
						im.setLore(l);
						i.setItemMeta(im);
					} else if (i.getType().equals(Material.RED_ROSE)) {
						if (conf.getBoolean("Spawners." + s + ".Death.Flower.Enabled") == false) {
							i.setType(Material.AIR);
						} else {
							i.setType(Material.getMaterial(conf.getString("Spawners." + s + ".Death.Flower.Type")));
							i.setDurability((byte) conf.getInt("Spawners." + s + ".Death.Flower.Id"));
						}
					}
				}
			}
		}
	}
}

/*
 * @EventHandler public void coalDeath(EntityDeathEvent event) {
 * 
 * Entity mob = event.getEntity(); Location location = mob.getLocation();
 * 
 * if (mob.getName().equals(Main.colorCodes("&8Coal Golem"))) {
 * 
 * event.getDrops().clear(); Integer coalAmount = random.nextInt(3); ItemStack
 * coal = new ItemStack(Material.COAL); coal.setAmount(coalAmount + 3);
 * event.getEntity().getWorld().dropItem(location, coal);
 * 
 * } }
 */
