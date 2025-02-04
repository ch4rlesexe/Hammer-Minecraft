package org.ch4rlesexe.hammer;

import org.bukkit.Material;

public class TrenchPickaxeCreationData {
    private String id = "default_id";
    private String displayName = "Default Pickaxe";
    private Material material = Material.DIAMOND_PICKAXE;
    private boolean enchanted = false;
    private boolean breakable = true;
    private String effect = "3x3"; // Default effect in format NxM
    private int durability = 0;  // Default durability (0 means use the material’s default)

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public Material getMaterial() {
        return material;
    }
    public void setMaterial(Material material) {
        this.material = material;
    }
    public boolean isEnchanted() {
        return enchanted;
    }
    public void setEnchanted(boolean enchanted) {
        this.enchanted = enchanted;
    }
    public boolean isBreakable() {
        return breakable;
    }
    public void setBreakable(boolean breakable) {
        this.breakable = breakable;
    }
    public String getEffect() {
        return effect;
    }
    public void setEffect(String effect) {
        this.effect = effect;
    }
    public int getDurability() {
        return durability;
    }
    public void setDurability(int durability) {
        this.durability = durability;
    }
}
