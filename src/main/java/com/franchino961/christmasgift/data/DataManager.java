package com.franchino961.christmasgift.data;

import com.franchino961.christmasgift.ChristmasGift;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DataManager {

    private final ChristmasGift plugin;
    private File dataFile;
    private FileConfiguration dataConfig;
    
    private final Map<Location, GiftBlock> giftBlocks = new HashMap<>();
    private final Map<UUID, Integer> playerStats = new HashMap<>();

    public DataManager(ChristmasGift plugin) {
        this.plugin = plugin;
    }

    public void loadData() {
        dataFile = new File(plugin.getDataFolder(), "data.yml");
        
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Could not create data.yml!");
                e.printStackTrace();
            }
        }
        
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        
        // Load gift blocks
        if (dataConfig.contains("gift-blocks")) {
            for (String key : dataConfig.getConfigurationSection("gift-blocks").getKeys(false)) {
                String path = "gift-blocks." + key;
                String worldName = dataConfig.getString(path + ".world");
                double x = dataConfig.getDouble(path + ".x");
                double y = dataConfig.getDouble(path + ".y");
                double z = dataConfig.getDouble(path + ".z");
                boolean claimed = dataConfig.getBoolean(path + ".claimed");
                String claimedBy = dataConfig.getString(path + ".claimed-by");
                
                Location location = new Location(
                    plugin.getServer().getWorld(worldName),
                    x, y, z
                );
                
                UUID claimedById = claimedBy != null ? UUID.fromString(claimedBy) : null;
                GiftBlock giftBlock = new GiftBlock(location, claimed, claimedById);
                giftBlocks.put(location, giftBlock);
            }
        }
        
        // Load player stats
        if (dataConfig.contains("player-stats")) {
            for (String uuidString : dataConfig.getConfigurationSection("player-stats").getKeys(false)) {
                UUID uuid = UUID.fromString(uuidString);
                int found = dataConfig.getInt("player-stats." + uuidString);
                playerStats.put(uuid, found);
            }
        }
    }

    public void saveData() {
        dataConfig = new YamlConfiguration();
        
        // Save gift blocks
        int index = 0;
        for (Map.Entry<Location, GiftBlock> entry : giftBlocks.entrySet()) {
            Location loc = entry.getKey();
            GiftBlock block = entry.getValue();
            String path = "gift-blocks." + index;
            
            dataConfig.set(path + ".world", loc.getWorld().getName());
            dataConfig.set(path + ".x", loc.getX());
            dataConfig.set(path + ".y", loc.getY());
            dataConfig.set(path + ".z", loc.getZ());
            dataConfig.set(path + ".claimed", block.isClaimed());
            dataConfig.set(path + ".claimed-by", block.getClaimedBy() != null ? block.getClaimedBy().toString() : null);
            
            index++;
        }
        
        // Save player stats
        for (Map.Entry<UUID, Integer> entry : playerStats.entrySet()) {
            dataConfig.set("player-stats." + entry.getKey().toString(), entry.getValue());
        }
        
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save data.yml!");
            e.printStackTrace();
        }
    }

    public void addGiftBlock(Location location) {
        giftBlocks.put(location, new GiftBlock(location, false, null));
        saveData();
    }

    public void removeGiftBlock(Location location) {
        giftBlocks.remove(location);
        saveData();
    }

    public void removeAllGiftBlocks() {
        giftBlocks.clear();
        saveData();
    }

    public boolean isGiftBlock(Location location) {
        return giftBlocks.containsKey(location);
    }

    public GiftBlock getGiftBlock(Location location) {
        return giftBlocks.get(location);
    }

    public Map<Location, GiftBlock> getGiftBlocks() {
        return new HashMap<>(giftBlocks);
    }

    public void claimGiftBlock(Location location, UUID player) {
        GiftBlock block = giftBlocks.get(location);
        if (block != null && !block.isClaimed()) {
            block.setClaimed(true);
            block.setClaimedBy(player);
            
            // Increment player stats
            playerStats.put(player, playerStats.getOrDefault(player, 0) + 1);
            
            saveData();
        }
    }

    public int getPlayerStats(UUID player) {
        return playerStats.getOrDefault(player, 0);
    }

    public Map<UUID, Integer> getPlayerStats() {
        return new HashMap<>(playerStats);
    }

    public List<Map.Entry<UUID, Integer>> getLeaderboard() {
        List<Map.Entry<UUID, Integer>> leaderboard = new ArrayList<>(playerStats.entrySet());
        leaderboard.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        return leaderboard;
    }

    public int getPlayerRank(UUID player) {
        List<Map.Entry<UUID, Integer>> leaderboard = getLeaderboard();
        for (int i = 0; i < leaderboard.size(); i++) {
            if (leaderboard.get(i).getKey().equals(player)) {
                return i + 1;
            }
        }
        return -1;
    }
}
