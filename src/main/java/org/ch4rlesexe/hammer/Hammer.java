package org.ch4rlesexe.hammer;

import org.bukkit.plugin.java.JavaPlugin;

public final class Hammer extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new HammerListener(), this);
        getCommand("hammer").setExecutor(new HammerCommand());
        getLogger().info("HammerPlugin enabled!");

    }

    @Override
    public void onDisable() {
        getLogger().info("HammerPlugin disabled!");
    }
}
