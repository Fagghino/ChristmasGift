package com.franchino961.christmasgift.config;

import com.franchino961.christmasgift.ChristmasGift;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MessagesManager {

    private final ChristmasGift plugin;
    private File messagesFile;
    private FileConfiguration messagesConfig;

    public MessagesManager(ChristmasGift plugin) {
        this.plugin = plugin;
    }

    public void loadMessages() {
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public void reloadMessages() {
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public void saveMessages() {
        try {
            messagesConfig.save(messagesFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save messages.yml!");
            e.printStackTrace();
        }
    }

    public String getMessage(String path) {
        String message = messagesConfig.getString(path, "Message not found: " + path);
        return formatColor(message);
    }

    public String getMessage(String path, String... replacements) {
        String message = getMessage(path);
        
        for (int i = 0; i < replacements.length - 1; i += 2) {
            message = message.replace(replacements[i], replacements[i + 1]);
        }
        
        return message;
    }

    private String formatColor(String text) {
        return text.replace("&", "§");
    }

    public FileConfiguration getMessagesConfig() {
        return messagesConfig;
    }
}
