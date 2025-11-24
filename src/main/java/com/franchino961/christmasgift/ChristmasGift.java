package com.franchino961.christmasgift;

import com.franchino961.christmasgift.commands.ChristmasGiftCommand;
import com.franchino961.christmasgift.config.ConfigManager;
import com.franchino961.christmasgift.config.MessagesManager;
import com.franchino961.christmasgift.data.DataManager;
import com.franchino961.christmasgift.listeners.BlockInteractListener;
// import com.franchino961.christmasgift.listeners.HeadDatabaseListener;
import com.franchino961.christmasgift.placeholders.ChristmasGiftExpansion;
import org.bukkit.plugin.java.JavaPlugin;

public class ChristmasGift extends JavaPlugin {

    private static ChristmasGift instance;
    private ConfigManager configManager;
    private MessagesManager messagesManager;
    private DataManager dataManager;

    @Override
    public void onEnable() {
        instance = this;

        // Initialize managers
        configManager = new ConfigManager(this);
        messagesManager = new MessagesManager(this);
        dataManager = new DataManager(this);

        // Load configurations
        configManager.loadConfig();
        messagesManager.loadMessages();
        dataManager.loadData();

        // Register commands
        ChristmasGiftCommand commandExecutor = new ChristmasGiftCommand(this);
        getCommand("christmasgift").setExecutor(commandExecutor);
        getCommand("christmasgift").setTabCompleter(commandExecutor);

        // Register listeners
        getServer().getPluginManager().registerEvents(new BlockInteractListener(this), this);

        // Register PlaceholderAPI expansion if available
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new ChristmasGiftExpansion(this).register();
            getLogger().info("PlaceholderAPI hooked successfully!");
        }

        getLogger().info("ChristmasGift plugin enabled successfully!");
    }

    @Override
    public void onDisable() {
        // Save data before disabling
        if (dataManager != null) {
            dataManager.saveData();
        }
        getLogger().info("ChristmasGift plugin disabled!");
    }

    public static ChristmasGift getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public MessagesManager getMessagesManager() {
        return messagesManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
