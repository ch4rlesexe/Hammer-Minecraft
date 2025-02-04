package org.ch4rlesexe.hammer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class TrenchPickaxeCreationChatListener implements Listener {

    public static ConcurrentHashMap<UUID, TrenchPickaxeCreationData> awaitingDisplayName = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<UUID, TrenchPickaxeCreationData> awaitingID = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<UUID, TrenchPickaxeCreationData> awaitingEffect = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<UUID, TrenchPickaxeCreationData> awaitingDurability = new ConcurrentHashMap<>();

    private static final Pattern effectPattern = Pattern.compile("^(\\d+)x(\\d+)$");

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (awaitingDisplayName.containsKey(uuid)) {
            event.setCancelled(true);
            String input = event.getMessage();
            TrenchPickaxeCreationData data = awaitingDisplayName.remove(uuid);
            data.setDisplayName(ChatColor.translateAlternateColorCodes('&', input));
            player.sendMessage(ChatColor.GREEN + "Display name set to: " + data.getDisplayName());
            player.getServer().getScheduler().runTask(TrenchPickaxePlugin.getInstance(), () -> {
                TrenchPickaxeCreationGUI.open(player, data);
            });
        } else if (awaitingID.containsKey(uuid)) {
            event.setCancelled(true);
            String input = event.getMessage().replaceAll("\\s+", "");
            TrenchPickaxeCreationData data = awaitingID.remove(uuid);
            data.setId(input);
            player.sendMessage(ChatColor.GREEN + "ID set to: " + data.getId());
            player.getServer().getScheduler().runTask(TrenchPickaxePlugin.getInstance(), () -> {
                TrenchPickaxeCreationGUI.open(player, data);
            });
        } else if (awaitingEffect.containsKey(uuid)) {
            event.setCancelled(true);
            String input = event.getMessage().trim();
            if (!effectPattern.matcher(input).matches()) {
                player.sendMessage(ChatColor.RED + "Invalid format! Please enter effect in format NxM (e.g., 3x3, 5x5).");
                return;
            }
            TrenchPickaxeCreationData data = awaitingEffect.remove(uuid);
            data.setEffect(input);
            player.sendMessage(ChatColor.GREEN + "Effect set to: " + data.getEffect());
            player.getServer().getScheduler().runTask(TrenchPickaxePlugin.getInstance(), () -> {
                TrenchPickaxeCreationGUI.open(player, data);
            });
        } else if (awaitingDurability.containsKey(uuid)) {
            event.setCancelled(true);
            String input = event.getMessage().trim();
            try {
                int dur = Integer.parseInt(input);
                TrenchPickaxeCreationData data = awaitingDurability.remove(uuid);
                data.setDurability(dur);
                player.sendMessage(ChatColor.GREEN + "Total durability set to: " + data.getDurability());
                player.getServer().getScheduler().runTask(TrenchPickaxePlugin.getInstance(), () -> {
                    TrenchPickaxeCreationGUI.open(player, data);
                });
            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "Invalid number! Please enter an integer.");
            }
        }
    }
}
