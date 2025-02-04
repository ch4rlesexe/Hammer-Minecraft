package org.ch4rlesexe.hammer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TrenchPickaxeCreationGUI {

    public static final String TITLE = ChatColor.BLUE + "Trench Pickaxe Creation";

    public static void open(Player player, TrenchPickaxeCreationData data) {
        Inventory inv = Bukkit.createInventory(null, 54, TITLE);

        // Slot 10: Pickaxe Type (Material)
        ItemStack materialItem = new ItemStack(data.getMaterial());
        ItemMeta materialMeta = materialItem.getItemMeta();
        materialMeta.setDisplayName(ChatColor.GOLD + "Pickaxe Type: " + ChatColor.WHITE + data.getMaterial().toString());
        materialItem.setItemMeta(materialMeta);
        inv.setItem(10, materialItem);

        // Slot 12: Enchanted toggle
        ItemStack enchantedItem = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta enchantedMeta = enchantedItem.getItemMeta();
        enchantedMeta.setDisplayName(ChatColor.GOLD + "Enchanted Glow: " + ChatColor.WHITE + (data.isEnchanted() ? "Yes" : "No"));
        enchantedItem.setItemMeta(enchantedMeta);
        inv.setItem(12, enchantedItem);

        // Slot 14: Breakable toggle
        ItemStack breakableItem = new ItemStack(Material.ANVIL);
        ItemMeta breakableMeta = breakableItem.getItemMeta();
        breakableMeta.setDisplayName(ChatColor.GOLD + "Breakable: " + ChatColor.WHITE + (data.isBreakable() ? "Yes" : "No"));
        breakableItem.setItemMeta(breakableMeta);
        inv.setItem(14, breakableItem);

        // Slot 16: Effect – click to set (must be in format NxM)
        ItemStack effectItem = new ItemStack(Material.PAPER);
        ItemMeta effectMeta = effectItem.getItemMeta();
        effectMeta.setDisplayName(ChatColor.GOLD + "Effect (NxM): " + ChatColor.WHITE + data.getEffect());
        effectItem.setItemMeta(effectMeta);
        inv.setItem(16, effectItem);

        // Slot 30: Display Name
        ItemStack nameItem = new ItemStack(Material.NAME_TAG);
        ItemMeta nameMeta = nameItem.getItemMeta();
        nameMeta.setDisplayName(ChatColor.GOLD + "Display Name: " + ChatColor.WHITE + data.getDisplayName());
        nameItem.setItemMeta(nameMeta);
        inv.setItem(30, nameItem);

        // Slot 32: ID
        ItemStack idItem = new ItemStack(Material.PAPER);
        ItemMeta idMeta = idItem.getItemMeta();
        idMeta.setDisplayName(ChatColor.GOLD + "ID: " + ChatColor.WHITE + data.getId());
        idItem.setItemMeta(idMeta);
        inv.setItem(32, idItem);

        // Slot 34: Durability
        ItemStack durabilityItem = new ItemStack(Material.REDSTONE);
        ItemMeta durabilityMeta = durabilityItem.getItemMeta();
        durabilityMeta.setDisplayName(ChatColor.GOLD + "Durability: " + ChatColor.WHITE + data.getDurability());
        durabilityItem.setItemMeta(durabilityMeta);
        inv.setItem(34, durabilityItem);

        // Slot 49: Save & Create button
        ItemStack saveItem = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta saveMeta = saveItem.getItemMeta();
        saveMeta.setDisplayName(ChatColor.GREEN + "Save & Create");
        saveItem.setItemMeta(saveMeta);
        inv.setItem(49, saveItem);

        player.openInventory(inv);
    }
}
