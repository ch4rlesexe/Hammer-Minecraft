package org.ch4rlesexe.hammer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.UUID;

public class TrenchCreateCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }
        if (!sender.hasPermission("trenchpickaxe.create")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();
        TrenchPickaxeCreationData data = new TrenchPickaxeCreationData();
        TrenchPickaxeCreationListener.creationDataMap.put(uuid, data);
        TrenchPickaxeCreationGUI.open(player, data);
        return true;
    }
}
