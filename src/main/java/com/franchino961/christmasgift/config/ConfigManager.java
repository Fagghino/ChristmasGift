package com.franchino961.christmasgift.config;

import com.franchino961.christmasgift.ChristmasGift;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class ConfigManager {

    private final ChristmasGift plugin;
    private FileConfiguration config;

    public ConfigManager(ChristmasGift plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    public void reloadConfiguration() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    public String getGiftBlockType() {
        return config.getString("gift-block.type", "PLAYER_HEAD");
    }

    public String getGiftBlockHeadTexture() {
        return config.getString("gift-block.head-texture", "");
    }

    public String getGiftBlockDisplayName() {
        return config.getString("gift-block.display-name", "&c&lChristmas Gift");
    }

    public boolean shouldReplaceBlock() {
        return config.getBoolean("gift-block.replace-after-claim", false);
    }

    public String getReplacementBlockType() {
        return config.getString("gift-block.replacement-block.type", "AIR");
    }

    public String getReplacementBlockHeadTexture() {
        return config.getString("gift-block.replacement-block.head-texture", "");
    }

    public ItemStack createGiftBlock() {
        String blockType = getGiftBlockType();
        ItemStack item;

        if (blockType.equalsIgnoreCase("PLAYER_HEAD")) {
            item = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(formatColor(getGiftBlockDisplayName()));
                
                // Apply custom texture if provided
                String texture = getGiftBlockHeadTexture();
                if (!texture.isEmpty()) {
                    try {
                        applyTexture(meta, texture);
                    } catch (Exception e) {
                        plugin.getLogger().warning("Failed to apply skull texture: " + e.getMessage());
                    }
                }
                
                item.setItemMeta(meta);
            }
        } else {
            try {
                Material material = Material.valueOf(blockType.toUpperCase());
                item = new ItemStack(material);
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Invalid material type: " + blockType + ". Using DIAMOND_BLOCK instead.");
                item = new ItemStack(Material.DIAMOND_BLOCK);
            }
        }

        return item;
    }

    private void applyTexture(SkullMeta meta, String texture) {
        try {
            // Create a game profile with the texture
            // Uses reflection to access Mojang's authlib (available at runtime in Spigot)
            Class<?> profileClass = Class.forName("com.mojang.authlib.GameProfile");
            Class<?> propertyClass = Class.forName("com.mojang.authlib.properties.Property");
            
            // Create GameProfile
            Object profile = profileClass.getConstructor(java.util.UUID.class, String.class)
                .newInstance(java.util.UUID.randomUUID(), null);
            
            // Get PropertyMap
            Object propertyMap = profileClass.getMethod("getProperties").invoke(profile);
            
            // Create and add Property
            Object property = propertyClass.getConstructor(String.class, String.class)
                .newInstance("textures", texture);
            propertyMap.getClass().getMethod("put", Object.class, Object.class)
                .invoke(propertyMap, "textures", property);
            
            // Set profile to SkullMeta
            java.lang.reflect.Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to apply skull texture: " + e.getMessage());
        }
    }

    private String formatColor(String text) {
        return text.replace("&", "§");
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
