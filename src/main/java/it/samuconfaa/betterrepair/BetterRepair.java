package it.samuconfaa.betterrepair;

import org.bukkit.plugin.java.JavaPlugin;

public final class BetterRepair extends JavaPlugin {

    private FIXListener fixListener;
    private ConfigurationManager configManager;

    @Override
    public void onEnable() {
        configManager = new ConfigurationManager(this);
        configManager.loadConfig();

        fixListener = new FIXListener(this);
        getCommand("fix").setExecutor(new FixCommand(this));
        getServer().getPluginManager().registerEvents(fixListener, this);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public FIXListener getFixListener() {
        return fixListener;
    }
}