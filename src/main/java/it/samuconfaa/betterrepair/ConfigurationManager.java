package it.samuconfaa.betterrepair;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

    public class ConfigurationManager {
        private final BetterRepair plugin;
        private static FileConfiguration config;
        private static File configFile;

        public ConfigurationManager(BetterRepair plugin) {
            this.plugin = plugin;
        }

        public void loadConfig() {
            plugin.saveDefaultConfig();
            config = plugin.getConfig();
            configFile = new File(plugin.getDataFolder(), "config.yml");
        }

        public static void reloadConfig() {
            config = YamlConfiguration.loadConfiguration(configFile);
        }
}
