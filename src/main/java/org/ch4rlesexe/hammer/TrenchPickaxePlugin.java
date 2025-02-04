package org.ch4rlesexe.hammer;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class TrenchPickaxePlugin extends JavaPlugin {

    private static TrenchPickaxePlugin instance;
    private TrenchPickaxeManager manager;

    // Global persistent key to mark custom pickaxes.
    public static NamespacedKey TRENCH_KEY;

    @Override
    public void onEnable() {
        instance = this;
        TRENCH_KEY = new NamespacedKey(this, "trenchpickaxe");
        saveDefaultConfig(); // Saves config.yml from resources if not present.

        manager = new TrenchPickaxeManager(this);
        manager.loadPickaxes();

        // Register event listeners
        getServer().getPluginManager().registerEvents(new HammerListener(manager), this);
        getServer().getPluginManager().registerEvents(new TrenchPickaxeCreationListener(), this);
        getServer().getPluginManager().registerEvents(new TrenchPickaxeCreationChatListener(), this);
        getServer().getPluginManager().registerEvents(new TrenchGiveGUIListener(), this);

        // Register commands
        getCommand("trenchcreate").setExecutor(new TrenchCreateCommand());
        TrenchGiveCommand giveCmd = new TrenchGiveCommand(manager);
        getCommand("trenchgive").setExecutor(giveCmd);
        getCommand("trenchgive").setTabCompleter(giveCmd);

        getLogger().info("TrenchPickaxePlugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("TrenchPickaxePlugin disabled!");
    }

    public static TrenchPickaxePlugin getInstance() {
        return instance;
    }

    public TrenchPickaxeManager getManager() {
        return manager;
    }
}
