package org.ch4rlesexe.hammer;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrenchPickaxeManager {
    private final JavaPlugin plugin;
    private final Map<String, TrenchPickaxe> pickaxeMap = new HashMap<>();

    public TrenchPickaxeManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadPickaxes() {
        pickaxeMap.clear();
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("trench-pickaxes");
        if (section == null) return;
        for (String key : section.getKeys(false)) {
            String displayName = section.getString(key + ".display-name", key);
            String materialName = section.getString(key + ".material", "DIAMOND_PICKAXE");
            Material material = Material.getMaterial(materialName.toUpperCase());
            if (material == null) {
                plugin.getLogger().warning("Invalid material for pickaxe " + key + ": " + materialName);
                continue;
            }
            boolean enchanted = section.getBoolean(key + ".enchanted", false);
            boolean breakable = section.getBoolean(key + ".breakable", true);
            String effect = section.getString(key + ".effect", "3x3");
            int durability = section.getInt(key + ".durability", 0);
            List<String> lore = section.getStringList(key + ".lore");
            TrenchPickaxe tp = new TrenchPickaxe(key, displayName, material, enchanted, breakable, effect, durability, lore);
            pickaxeMap.put(key.toLowerCase(), tp);
        }
    }

    public Map<String, TrenchPickaxe> getPickaxeMap() {
        return pickaxeMap;
    }

    public TrenchPickaxe getPickaxe(String id) {
        return pickaxeMap.get(id.toLowerCase());
    }

    public void addPickaxe(TrenchPickaxe tp) {
        pickaxeMap.put(tp.getId().toLowerCase(), tp);
        plugin.getConfig().set("trench-pickaxes." + tp.getId() + ".display-name", tp.getDisplayName());
        plugin.getConfig().set("trench-pickaxes." + tp.getId() + ".material", tp.getMaterial().toString());
        plugin.getConfig().set("trench-pickaxes." + tp.getId() + ".enchanted", tp.isEnchanted());
        plugin.getConfig().set("trench-pickaxes." + tp.getId() + ".breakable", tp.isBreakable());
        plugin.getConfig().set("trench-pickaxes." + tp.getId() + ".effect", tp.getEffect());
        plugin.getConfig().set("trench-pickaxes." + tp.getId() + ".durability", tp.getDurability());
        plugin.getConfig().set("trench-pickaxes." + tp.getId() + ".lore", tp.getLore());
        plugin.saveConfig();
    }
}
