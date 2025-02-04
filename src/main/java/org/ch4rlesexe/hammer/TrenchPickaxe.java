package org.ch4rlesexe.hammer;

import org.bukkit.Material;
import java.util.List;

public class TrenchPickaxe {
    private final String id;
    private final String displayName;
    private final Material material;
    private final boolean enchanted;
    private final boolean breakable;
    private final String effect;  // e.g., "3x3", "5x5"
    private final int durability;  // Custom total durability (the maximum value)
    private final List<String> lore; // Optional lore

    public TrenchPickaxe(String id, String displayName, Material material, boolean enchanted, boolean breakable, String effect, int durability, List<String> lore) {
        this.id = id;
        this.displayName = displayName;
        this.material = material;
        this.enchanted = enchanted;
        this.breakable = breakable;
        this.effect = effect;
        this.durability = durability;
        this.lore = lore;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Material getMaterial() {
        return material;
    }

    public boolean isEnchanted() {
        return enchanted;
    }

    public boolean isBreakable() {
        return breakable;
    }

    public String getEffect() {
        return effect;
    }

    public int getDurability() {
        return durability;
    }

    public List<String> getLore() {
        return lore;
    }
}
