package org.ch4rlesexe.hammer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HammerListener implements Listener {

    private boolean isBulkBreaking = false;
    private final TrenchPickaxeManager manager;

    private static final Pattern effectPattern = Pattern.compile("^(\\d+)x(\\d+)$");

    public HammerListener(TrenchPickaxeManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (isBulkBreaking) return;
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || !item.hasItemMeta()) return;
        ItemMeta meta = item.getItemMeta();
        if (!meta.getPersistentDataContainer().has(TrenchPickaxePlugin.TRENCH_KEY, PersistentDataType.STRING)) return;
        String id = meta.getPersistentDataContainer().get(TrenchPickaxePlugin.TRENCH_KEY, PersistentDataType.STRING);
        if (id == null) return;
        TrenchPickaxe tp = manager.getPickaxe(id);
        if (tp == null) return;
        Location centerLocation = event.getBlock().getLocation();
        isBulkBreaking = true;

        String effect = tp.getEffect();
        Matcher matcher = effectPattern.matcher(effect);
        if (matcher.matches()) {
            int width = Integer.parseInt(matcher.group(1));
            int height = Integer.parseInt(matcher.group(2));
            int halfWidth = width / 2;
            int halfHeight = height / 2;
            double pitch = player.getLocation().getPitch();
            if (Math.abs(pitch) < 45) {
                double yaw = player.getLocation().getYaw();
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
                for (int i = -halfWidth; i <= halfWidth; i++) {
                    for (int j = -halfHeight; j <= halfHeight; j++) {
                        if (i == 0 && j == 0) continue;
                        Location targetLocation = centerLocation.clone()
                                .add(horizontal.clone().multiply(i))
                                .add(vertical.clone().multiply(j));
                        Block targetBlock = targetLocation.getBlock();
                        if (isProtected(targetBlock)) continue;
                        targetBlock.breakNaturally(item);
                    }
                }
            } else {
                // Horizontal plane (x, z)
                for (int dx = -halfWidth; dx <= halfWidth; dx++) {
                    for (int dz = -halfHeight; dz <= halfHeight; dz++) {
                        if (dx == 0 && dz == 0) continue;
                        Location targetLocation = centerLocation.clone().add(dx, 0, dz);
                        Block targetBlock = targetLocation.getBlock();
                        if (isProtected(targetBlock)) continue;
                        targetBlock.breakNaturally(item);
                    }
                }
            }
        }
        isBulkBreaking = false;
    }

    private boolean isProtected(Block block) {
        Material type = block.getType();
        return type == Material.AIR ||
                type == Material.BEDROCK ||
                type == Material.BARRIER ||
                type == Material.END_PORTAL ||
                type == Material.END_PORTAL_FRAME ||
                type == Material.END_GATEWAY ||
                type == Material.LAVA ||
                type == Material.WATER;
    }
}
