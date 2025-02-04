package org.ch4rlesexe.hammer;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TrenchGiveGUIListener implements Listener {

    public static ItemStack createTrenchPickaxeItem(TrenchPickaxe tp) {
        ItemStack newItem = new ItemStack(tp.getMaterial());
        ItemMeta meta = newItem.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(tp.getDisplayName());
            if (tp.isEnchanted()) {
                meta.addEnchant(org.bukkit.enchantments.Enchantment.UNBREAKING, 1, true);
                meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS);
            }
            meta.setUnbreakable(!tp.isBreakable());
            if (meta instanceof org.bukkit.inventory.meta.Damageable) {
                ((org.bukkit.inventory.meta.Damageable) meta).setDamage(tp.getDurability());
            }
            meta.getPersistentDataContainer().set(TrenchPickaxePlugin.TRENCH_KEY, org.bukkit.persistence.PersistentDataType.STRING, tp.getId());
            newItem.setItemMeta(meta);
        }
        return newItem;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(TrenchGiveGUI.TITLE)) return;
        event.setCancelled(true);
        if (event.getCurrentItem() == null) return;
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        ItemMeta meta = clicked.getItemMeta();
        if (meta == null) return;
        // Check for our persistent data tag
        if (!meta.getPersistentDataContainer().has(TrenchPickaxePlugin.TRENCH_KEY, org.bukkit.persistence.PersistentDataType.STRING)) return;
        String id = meta.getPersistentDataContainer().get(TrenchPickaxePlugin.TRENCH_KEY, org.bukkit.persistence.PersistentDataType.STRING);
        if (id == null) return;
        TrenchPickaxe tp = TrenchPickaxePlugin.getInstance().getManager().getPickaxe(id);
        if (tp == null) return;
        // Give the item to the player
        ItemStack item = createTrenchPickaxeItem(tp);
        player.getInventory().addItem(item);
        player.sendMessage(ChatColor.GREEN + "You have been given the custom trench pickaxe: " + tp.getDisplayName());
        player.closeInventory();
    }
}
