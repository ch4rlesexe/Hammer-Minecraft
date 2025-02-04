package org.ch4rlesexe.hammer;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TrenchPickaxeCreationListener implements Listener {

    // Map to store creation data for each player
    public static Map<UUID, TrenchPickaxeCreationData> creationDataMap = new HashMap<>();

    // Allowed materials for pickaxes
    private static final Material[] ALLOWED_MATERIALS = {
            Material.DIAMOND_PICKAXE,
            Material.IRON_PICKAXE,
            Material.GOLDEN_PICKAXE,
            Material.STONE_PICKAXE,
            Material.NETHERITE_PICKAXE
    };

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(TrenchPickaxeCreationGUI.TITLE)) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            UUID uuid = player.getUniqueId();
            TrenchPickaxeCreationData data = creationDataMap.get(uuid);
            if (data == null) return;
            int slot = event.getRawSlot();
            if (slot == 10) {
                // Cycle material
                Material current = data.getMaterial();
                int index = Arrays.asList(ALLOWED_MATERIALS).indexOf(current);
                index = (index + 1) % ALLOWED_MATERIALS.length;
                data.setMaterial(ALLOWED_MATERIALS[index]);
                TrenchPickaxeCreationGUI.open(player, data);
            } else if (slot == 12) {
                // Toggle enchanted
                data.setEnchanted(!data.isEnchanted());
                TrenchPickaxeCreationGUI.open(player, data);
            } else if (slot == 14) {
                // Toggle breakable
                data.setBreakable(!data.isBreakable());
                TrenchPickaxeCreationGUI.open(player, data);
            } else if (slot == 16) {
                // Prompt for effect input
                player.closeInventory();
                player.sendMessage(ChatColor.YELLOW + "Please type the effect in format NxM (e.g., 3x3, 5x5) in chat.");
                TrenchPickaxeCreationChatListener.awaitingEffect.put(uuid, data);
            } else if (slot == 30) {
                // Set display name: prompt chat input
                player.closeInventory();
                player.sendMessage(ChatColor.YELLOW + "Please type the new display name in chat.");
                TrenchPickaxeCreationChatListener.awaitingDisplayName.put(uuid, data);
            } else if (slot == 32) {
                // Set ID: prompt chat input
                player.closeInventory();
                player.sendMessage(ChatColor.YELLOW + "Please type the new ID in chat (no spaces).");
                TrenchPickaxeCreationChatListener.awaitingID.put(uuid, data);
            } else if (slot == 34) {
                // Prompt for durability input
                player.closeInventory();
                player.sendMessage(ChatColor.YELLOW + "Please type the total durability value (an integer; e.g., 1000) in chat.");
                TrenchPickaxeCreationChatListener.awaitingDurability.put(uuid, data);
            } else if (slot == 49) {
                // Save & Create: check for duplicate ID, then create the pickaxe, add it to config, and give it to the player
                if (TrenchPickaxePlugin.getInstance().getManager().getPickaxe(data.getId()) != null) {
                    player.sendMessage(ChatColor.RED + "A pickaxe with that ID already exists! Please choose a different ID.");
                    TrenchPickaxeCreationGUI.open(player, data);
                    return;
                }
                TrenchPickaxe newPickaxe = new TrenchPickaxe(
                        data.getId(),
                        data.getDisplayName(),
                        data.getMaterial(),
                        data.isEnchanted(),
                        data.isBreakable(),
                        data.getEffect(),
                        data.getDurability(),
                        null // no lore for now
                );
                TrenchPickaxePlugin.getInstance().getManager().addPickaxe(newPickaxe);
                // Create the item from our settings and give it to the player
                ItemStack item = TrenchGiveGUIListener.createTrenchPickaxeItem(newPickaxe);
                player.getInventory().addItem(item);
                player.sendMessage(ChatColor.GREEN + "Created and given you the custom trench pickaxe: " + data.getDisplayName());
                creationDataMap.remove(uuid);
                player.closeInventory();
            }
        }
    }
}
