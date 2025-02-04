package org.ch4rlesexe.hammer;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HammerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return true;
        }
        Player player = (Player) sender;
        ItemStack hammer = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta meta = hammer.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("Hammer");
            hammer.setItemMeta(meta);
        }
        player.getInventory().addItem(hammer);
        player.sendMessage(ChatColor.GREEN + "You have been given a hammer!");
        return true;
    }
}
