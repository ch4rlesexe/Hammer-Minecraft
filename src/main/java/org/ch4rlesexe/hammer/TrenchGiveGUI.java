package org.ch4rlesexe.hammer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TrenchGiveGUI {

    public static final String TITLE = ChatColor.BLUE + "Trench Pickaxe Selection";

    public static void open(Player player, TrenchPickaxeManager manager) {
        // Always create a 54-slot inventory to show the full GUI.
        Inventory inv = Bukkit.createInventory(null, 54, TITLE);

        // For each defined pickaxe, add its item representation.
        for (TrenchPickaxe tp : manager.getPickaxeMap().values()) {
            ItemStack item = TrenchGiveGUIListener.createTrenchPickaxeItem(tp);
            inv.addItem(item);
        }
        player.openInventory(inv);
    }
}
