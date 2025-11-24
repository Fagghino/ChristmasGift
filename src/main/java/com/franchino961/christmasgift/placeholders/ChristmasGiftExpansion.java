package com.franchino961.christmasgift.placeholders;

import com.franchino961.christmasgift.ChristmasGift;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ChristmasGiftExpansion extends PlaceholderExpansion {

    private final ChristmasGift plugin;

    public ChristmasGiftExpansion(ChristmasGift plugin) {
        this.plugin = plugin;
    }

    @Override
    @NotNull
    public String getIdentifier() {
        return "christmasgift";
    }

    @Override
    @NotNull
    public String getAuthor() {
        return "Franchino961";
    }

    @Override
    @NotNull
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        // %christmasgift_found%
        if (params.equalsIgnoreCase("found")) {
            if (player == null) return "0";
            return String.valueOf(plugin.getDataManager().getPlayerStats(player.getUniqueId()));
        }

        // %christmasgift_rank%
        if (params.equalsIgnoreCase("rank")) {
            if (player == null) return "N/A";
            int rank = plugin.getDataManager().getPlayerRank(player.getUniqueId());
            return rank == -1 ? "N/A" : String.valueOf(rank);
        }

        // %christmasgift_<position>_name%
        if (params.matches("\\d+_name")) {
            try {
                int position = Integer.parseInt(params.split("_")[0]);
                return getPlayerNameAtPosition(position);
            } catch (NumberFormatException e) {
                return "Invalid";
            }
        }

        // %christmasgift_<position>_displayname%
        if (params.matches("\\d+_displayname")) {
            try {
                int position = Integer.parseInt(params.split("_")[0]);
                return getPlayerDisplayNameAtPosition(position);
            } catch (NumberFormatException e) {
                return "Invalid";
            }
        }

        // %christmasgift_<position>_value%
        if (params.matches("\\d+_value")) {
            try {
                int position = Integer.parseInt(params.split("_")[0]);
                return getValueAtPosition(position);
            } catch (NumberFormatException e) {
                return "0";
            }
        }

        return null;
    }

    private String getPlayerNameAtPosition(int position) {
        List<Map.Entry<UUID, Integer>> leaderboard = plugin.getDataManager().getLeaderboard();
        
        if (position < 1 || position > leaderboard.size()) {
            return "N/A";
        }

        UUID uuid = leaderboard.get(position - 1).getKey();
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        
        return offlinePlayer.getName() != null ? offlinePlayer.getName() : "Unknown";
    }

    private String getPlayerDisplayNameAtPosition(int position) {
        List<Map.Entry<UUID, Integer>> leaderboard = plugin.getDataManager().getLeaderboard();
        
        if (position < 1 || position > leaderboard.size()) {
            return "N/A";
        }

        UUID uuid = leaderboard.get(position - 1).getKey();
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        
        if (offlinePlayer.isOnline() && offlinePlayer.getPlayer() != null) {
            return offlinePlayer.getPlayer().getDisplayName();
        }
        
        return offlinePlayer.getName() != null ? offlinePlayer.getName() : "Unknown";
    }

    private String getValueAtPosition(int position) {
        List<Map.Entry<UUID, Integer>> leaderboard = plugin.getDataManager().getLeaderboard();
        
        if (position < 1 || position > leaderboard.size()) {
            return "0";
        }

        return String.valueOf(leaderboard.get(position - 1).getValue());
    }
}
