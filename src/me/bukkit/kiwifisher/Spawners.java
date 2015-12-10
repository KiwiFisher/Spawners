package me.bukkit.kiwifisher;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * @author KiwiFisher
 */
public class Spawners extends JavaPlugin {

    public static Plugin plugin;
    public static Logger logger;
    public static FileConfiguration config;
    public static Economy economy = null;

    @Override
    public void onEnable() {

        plugin = this;
        logger = plugin.getLogger();
        config = plugin.getConfig();

        logger.info("Spawners is starting!");

        config.options().copyDefaults(true);
        saveConfig();
        setupEconomy();

        getServer().getPluginManager().registerEvents(new SpawnerClickListener(), this);
        getServer().getPluginManager().registerEvents(new SpawnersGUI(), this);
        getServer().getPluginManager().registerEvents(new SpawnerBreakListener(), this);
        getServer().getPluginManager().registerEvents(new SpawnerPlaceListener(), this);
        getCommand("spawners").setExecutor(new SpawnersCommandHandler());

        logger.info("Spawners has finished loading!");

    }

    @Override
    public void onDisable() {

        logger.info("Goodbye from Spawners!");

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public static boolean hasEconomy() {
        if (economy != null) {
            return true;
        }
        return false;
    }

}
