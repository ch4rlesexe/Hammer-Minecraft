package org.ch4rlesexe.hammer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TrenchGiveCommand implements CommandExecutor, TabCompleter {

    private final TrenchPickaxeManager manager;

    public TrenchGiveCommand(TrenchPickaxeManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("trenchpickaxe.give")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
        // If sender is a player and no arguments are provided, open the GUI.
        if (sender instanceof Player && args.length == 0) {
            Player player = (Player) sender;
            TrenchGiveGUI.open(player, manager);
            return true;
        }
        // Otherwise, if arguments are provided (for console or advanced usage)
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /trenchgive <player> <pickaxeID>");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }
        String id = args[1].toLowerCase();
        TrenchPickaxe tp = manager.getPickaxe(id);
        if (tp == null) {
            sender.sendMessage(ChatColor.RED + "No pickaxe found with ID: " + id);
            return true;
        }
        // Create the item using our utility method from the give GUI listener.
        ItemStack item = TrenchGiveGUIListener.createTrenchPickaxeItem(tp);
        target.getInventory().addItem(item);
        sender.sendMessage(ChatColor.GREEN + "Gave " + tp.getDisplayName() + " to " + target.getName());
        target.sendMessage(ChatColor.GREEN + "You have been given a custom trench pickaxe: " + tp.getDisplayName());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            // Complete online player names for first argument.
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                    completions.add(p.getName());
                }
            }
        } else if (args.length == 2) {
            // Complete pickaxe IDs for second argument.
            for (String id : manager.getPickaxeMap().keySet()) {
                if (id.toLowerCase().startsWith(args[1].toLowerCase())) {
                    completions.add(id);
                }
            }
        }
        return completions;
    }
}
