package it.samuconfaa.betterrepair;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterRepair extends JavaPlugin {

    private FIXListener fixListener;
    private ConfigurationManager configManager;
    private static Economy econ = null;

    @Override
    public void onEnable() {
        configManager = new ConfigurationManager(this);
        configManager.loadConfig();

        fixListener = new FIXListener(this);
        getCommand("fix").setExecutor(new FixCommand(this));
        getServer().getPluginManager().registerEvents(fixListener, this);
        if (!setupEconomy()) {
            getLogger().severe("Vault non è installato o nessun plugin economico è trovato!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public FIXListener getFixListener() {
        return fixListener;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public void removeMoney(Player player, double amount) {
        if (econ != null) {
            econ.withdrawPlayer(player, amount);
        }
    }
}