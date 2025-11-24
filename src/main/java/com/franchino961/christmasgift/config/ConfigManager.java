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

    /**
     * Apply replacement block texture to a placed block in the world
     * @param block The block to apply the texture to
     */
    public void applyReplacementTexture(org.bukkit.block.Block block) {
        String replacementType = getReplacementBlockType();
        
        if (replacementType.equalsIgnoreCase("PLAYER_HEAD")) {
            block.setType(Material.PLAYER_HEAD);
            
            String texture = getReplacementBlockHeadTexture();
            if (!texture.isEmpty()) {
                org.bukkit.block.BlockState state = block.getState();
                if (state instanceof org.bukkit.block.Skull) {
                    org.bukkit.block.Skull skull = (org.bukkit.block.Skull) state;
                    
                    try {
                        // Create PlayerProfile with texture
                        org.bukkit.profile.PlayerProfile profile = org.bukkit.Bukkit.createPlayerProfile(
                            java.util.UUID.randomUUID(), 
                            "ChristmasGift"
                        );
                        
                        org.bukkit.profile.PlayerTextures textures = profile.getTextures();
                        String decoded = new String(java.util.Base64.getDecoder().decode(texture));
                        
                        String urlPattern = "\"url\":\"";
                        int urlStart = decoded.indexOf(urlPattern);
                        if (urlStart != -1) {
                            urlStart += urlPattern.length();
                            int urlEnd = decoded.indexOf("\"", urlStart);
                            if (urlEnd != -1) {
                                String urlString = decoded.substring(urlStart, urlEnd);
                                java.net.URL url = new java.net.URL(urlString);
                                textures.setSkin(url);
                                profile.setTextures(textures);
                                skull.setOwnerProfile(profile);
                                skull.update();
                                plugin.getLogger().info("Successfully applied replacement texture from URL: " + urlString);
                            }
                        }
                    } catch (Exception e) {
                        plugin.getLogger().severe("Failed to apply replacement texture: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        } else if (!replacementType.equalsIgnoreCase("AIR")) {
            try {
                Material material = Material.valueOf(replacementType.toUpperCase());
                block.setType(material);
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Invalid replacement material: " + replacementType);
                block.setType(Material.AIR);
            }
        } else {
            block.setType(Material.AIR);
        }
    }

    private void applyTexture(SkullMeta meta, String texture) {
        try {
            // Create PlayerProfile using Bukkit's API (Paper/Spigot 1.18+)
            org.bukkit.profile.PlayerProfile profile = org.bukkit.Bukkit.createPlayerProfile(
                java.util.UUID.randomUUID(), 
                "ChristmasGift"
            );
            
            // Get the textures and set the skin
            org.bukkit.profile.PlayerTextures textures = profile.getTextures();
            
            // Create URL from the texture value
            // The texture is the base64 encoded value, we need to decode it to get the URL
            String decoded = new String(java.util.Base64.getDecoder().decode(texture));
            
            // Parse JSON to extract texture URL
            String urlPattern = "\"url\":\"";
            int urlStart = decoded.indexOf(urlPattern);
            if (urlStart != -1) {
                urlStart += urlPattern.length();
                int urlEnd = decoded.indexOf("\"", urlStart);
                if (urlEnd != -1) {
                    String urlString = decoded.substring(urlStart, urlEnd);
                    java.net.URL url = new java.net.URL(urlString);
                    textures.setSkin(url);
                    profile.setTextures(textures);
                    meta.setOwnerProfile(profile);
                    plugin.getLogger().info("Successfully applied texture from URL: " + urlString);
                }
            }
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to apply skull texture: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String formatColor(String text) {
        return text.replace("&", "§");
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
