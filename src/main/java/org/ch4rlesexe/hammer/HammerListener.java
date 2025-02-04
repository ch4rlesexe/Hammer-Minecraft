package org.ch4rlesexe.hammer;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class HammerListener implements Listener {

    private boolean isBulkBreaking = false;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (isBulkBreaking) return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || !item.hasItemMeta()) return;
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasDisplayName()) return;
        if (!item.getType().name().endsWith("PICKAXE")) return;
        if (!meta.getDisplayName().equalsIgnoreCase("Hammer")) return;

        Block centerBlock = event.getBlock();
        Location centerLocation = centerBlock.getLocation();

        isBulkBreaking = true;

        double pitch = player.getLocation().getPitch();
        if (Math.abs(pitch) < 45) {
            /*
             * For a vertical plane we want one axis to be vertical (Y axis)
             * and the other axis to be the horizontal left/right relative to the player's facing.
             *
             * We determine the left/right axis by checking the player's yaw and rounding it
             * to the nearest cardinal direction.
             */
            double yaw = player.getLocation().getYaw();
            // Normalize yaw to [0, 360)
            yaw = (yaw % 360 + 360) % 360;
            Vector horizontal;
            if (yaw >= 315 || yaw < 45) {
                horizontal = new Vector(1, 0, 0);
            } else if (yaw >= 45 && yaw < 135) {
                horizontal = new Vector(0, 0, 1);
            } else if (yaw >= 135 && yaw < 225) {
                horizontal = new Vector(-1, 0, 0);
            } else {
                horizontal = new Vector(0, 0, -1);
            }
            Vector vertical = new Vector(0, 1, 0);

            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) continue;

                    Location targetLocation = centerLocation.clone()
                            .add(horizontal.clone().multiply(i))
                            .add(vertical.clone().multiply(j));

                    Block targetBlock = targetLocation.getBlock();

                    if (targetBlock.getType() != Material.AIR) continue;
                    if (targetBlock.getType() == Material.BEDROCK) continue;
                    if (targetBlock.getType() == Material.BARRIER) continue;
                    if (targetBlock.getType() == Material.END_PORTAL) continue;
                    if (targetBlock.getType() == Material.END_PORTAL_FRAME) continue;
                    if (targetBlock.getType() == Material.END_GATEWAY) continue;
                    if (targetBlock.getType() == Material.LAVA) continue;
                    if (targetBlock.getType() == Material.WATER) continue;

                    targetBlock.breakNaturally(item);
                }
            }
        } else {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (dx == 0 && dz == 0) continue;

                    Location targetLocation = centerLocation.clone().add(dx, 0, dz);
                    Block targetBlock = targetLocation.getBlock();

                    if (targetBlock.getType() != Material.AIR) continue;
                    if (targetBlock.getType() == Material.BEDROCK) continue;
                    if (targetBlock.getType() == Material.BARRIER) continue;
                    if (targetBlock.getType() == Material.END_PORTAL) continue;
                    if (targetBlock.getType() == Material.END_PORTAL_FRAME) continue;
                    if (targetBlock.getType() == Material.END_GATEWAY) continue;
                    if (targetBlock.getType() == Material.LAVA) continue;
                    if (targetBlock.getType() == Material.WATER) continue;

                    targetBlock.breakNaturally(item);
                }
            }
        }

        isBulkBreaking = false;
    }
}
