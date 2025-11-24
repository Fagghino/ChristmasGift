package com.franchino961.christmasgift.commands;

import com.franchino961.christmasgift.ChristmasGift;
//import com.franchino961.christmasgift.data.GiftBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class ChristmasGiftCommand implements CommandExecutor, TabCompleter {

    private final ChristmasGift plugin;

    public ChristmasGiftCommand(ChristmasGift plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "give":
                return handleGive(sender);
            case "stats":
                return handleStats(sender);
            case "leaderboard":
            case "lb":
                return handleLeaderboard(sender);
            case "remove":
                return handleRemove(sender, args);
            case "removeall":
                return handleRemoveAll(sender);
            case "reset":
                return handleReset(sender, args);
            case "resetall":
                return handleResetAll(sender);
            case "reload":
                return handleReload(sender);
            default:
                sender.sendMessage(plugin.getMessagesManager().getMessage("messages.unknown-command"));
                return true;
        }
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage("§6§l=== ChristmasGift Commands ===");
        sender.sendMessage("§e/christmasgift give §7- Get a gift block");
        sender.sendMessage("§e/christmasgift stats §7- View your stats");
        
        if (sender.hasPermission("christmasgift.admin")) {
            sender.sendMessage("§e/christmasgift leaderboard §7- View leaderboard");
            sender.sendMessage("§e/christmasgift remove <x> <y> <z> §7- Remove gift block");
            sender.sendMessage("§e/christmasgift removeall §7- Remove all gift blocks");
            sender.sendMessage("§e/christmasgift reset <player> §7- Reset player stats");
            sender.sendMessage("§e/christmasgift resetall §7- Reset all player stats");
            sender.sendMessage("§e/christmasgift reload §7- Reload configuration");
        }
    }

    private boolean handleGive(CommandSender sender) {
        if (!sender.hasPermission("christmasgift.admin")) {
            sender.sendMessage(plugin.getMessagesManager().getMessage("messages.no-permission"));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getMessagesManager().getMessage("messages.player-only"));
            return true;
        }

        Player player = (Player) sender;
        ItemStack giftBlock = plugin.getConfigManager().createGiftBlock();
        player.getInventory().addItem(giftBlock);
        sender.sendMessage(plugin.getMessagesManager().getMessage("messages.gift-received"));
        
        return true;
    }

    private boolean handleStats(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getMessagesManager().getMessage("messages.player-only"));
            return true;
        }

        Player player = (Player) sender;
        int found = plugin.getDataManager().getPlayerStats(player.getUniqueId());
        int rank = plugin.getDataManager().getPlayerRank(player.getUniqueId());
        
        sender.sendMessage(plugin.getMessagesManager().getMessage("messages.stats-header"));
        sender.sendMessage(plugin.getMessagesManager().getMessage("messages.stats-found", 
            "{found}", String.valueOf(found)));
        
        if (rank != -1) {
            sender.sendMessage(plugin.getMessagesManager().getMessage("messages.stats-rank", 
                "{rank}", String.valueOf(rank)));
        }
        
        return true;
    }

    private boolean handleLeaderboard(CommandSender sender) {
        if (!sender.hasPermission("christmasgift.admin")) {
            sender.sendMessage(plugin.getMessagesManager().getMessage("messages.no-permission"));
            return true;
        }

        List<Map.Entry<UUID, Integer>> leaderboard = plugin.getDataManager().getLeaderboard();
        
        sender.sendMessage(plugin.getMessagesManager().getMessage("messages.leaderboard-header"));
        
        if (leaderboard.isEmpty()) {
            sender.sendMessage(plugin.getMessagesManager().getMessage("messages.leaderboard-empty"));
            return true;
        }

        int position = 1;
        for (Map.Entry<UUID, Integer> entry : leaderboard) {
            if (position > 10) break; // Show top 10
            
            OfflinePlayer player = Bukkit.getOfflinePlayer(entry.getKey());
            String playerName = player.getName() != null ? player.getName() : "Unknown";
            
            sender.sendMessage(plugin.getMessagesManager().getMessage("messages.leaderboard-entry",
                "{position}", String.valueOf(position),
                "{player}", playerName,
                "{found}", String.valueOf(entry.getValue())));
            
            position++;
        }
        
        return true;
    }

    private boolean handleRemove(CommandSender sender, String[] args) {
        if (!sender.hasPermission("christmasgift.admin")) {
            sender.sendMessage(plugin.getMessagesManager().getMessage("messages.no-permission"));
            return true;
        }

        if (args.length < 4) {
            sender.sendMessage(plugin.getMessagesManager().getMessage("messages.remove-usage"));
            return true;
        }

        try {
            double x = Double.parseDouble(args[1]);
            double y = Double.parseDouble(args[2]);
            double z = Double.parseDouble(args[3]);
            
            // Find the gift block at or near these coordinates
            Location targetLocation = null;
            for (Location loc : plugin.getDataManager().getGiftBlocks().keySet()) {
                if (loc.getBlockX() == (int)x && loc.getBlockY() == (int)y && loc.getBlockZ() == (int)z) {
                    targetLocation = loc;
                    break;
                }
            }
            
            if (targetLocation == null) {
                sender.sendMessage(plugin.getMessagesManager().getMessage("messages.gift-not-found"));
                return true;
            }
            
            plugin.getDataManager().removeGiftBlock(targetLocation);
            sender.sendMessage(plugin.getMessagesManager().getMessage("messages.gift-removed"));
            
        } catch (NumberFormatException e) {
            sender.sendMessage(plugin.getMessagesManager().getMessage("messages.invalid-coordinates"));
        }
        
        return true;
    }

    private boolean handleRemoveAll(CommandSender sender) {
        if (!sender.hasPermission("christmasgift.admin")) {
            sender.sendMessage(plugin.getMessagesManager().getMessage("messages.no-permission"));
            return true;
        }

        int count = plugin.getDataManager().getGiftBlocks().size();
        plugin.getDataManager().removeAllGiftBlocks();
        sender.sendMessage(plugin.getMessagesManager().getMessage("messages.all-gifts-removed",
            "{count}", String.valueOf(count)));
        
        return true;
    }

    private boolean handleReset(CommandSender sender, String[] args) {
        if (!sender.hasPermission("christmasgift.admin")) {
            sender.sendMessage(plugin.getMessagesManager().getMessage("messages.no-permission"));
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(plugin.getMessagesManager().getMessage("messages.reset-usage"));
            return true;
        }

        String playerName = args[1];
        OfflinePlayer target = Bukkit.getOfflinePlayer(playerName);
        
        if (!target.hasPlayedBefore() && !target.isOnline()) {
            sender.sendMessage(plugin.getMessagesManager().getMessage("messages.player-not-found",
                "{player}", playerName));
            return true;
        }

        int previousStats = plugin.getDataManager().getPlayerStats(target.getUniqueId());
        plugin.getDataManager().resetPlayerStats(target.getUniqueId());
        
        sender.sendMessage(plugin.getMessagesManager().getMessage("messages.player-reset",
            "{player}", target.getName(),
            "{count}", String.valueOf(previousStats)));
        
        return true;
    }

    private boolean handleResetAll(CommandSender sender) {
        if (!sender.hasPermission("christmasgift.admin")) {
            sender.sendMessage(plugin.getMessagesManager().getMessage("messages.no-permission"));
            return true;
        }

        int totalPlayers = plugin.getDataManager().getPlayerStats().size();
        plugin.getDataManager().resetAllPlayerStats();
        
        sender.sendMessage(plugin.getMessagesManager().getMessage("messages.all-stats-reset",
            "{count}", String.valueOf(totalPlayers)));
        
        return true;
    }

    private boolean handleReload(CommandSender sender) {
        if (!sender.hasPermission("christmasgift.admin")) {
            sender.sendMessage(plugin.getMessagesManager().getMessage("messages.no-permission"));
            return true;
        }

        plugin.getConfigManager().reloadConfiguration();
        plugin.getMessagesManager().reloadMessages();
        plugin.getDataManager().loadData();
        
        sender.sendMessage(plugin.getMessagesManager().getMessage("messages.config-reloaded"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            List<String> subCommands = new ArrayList<>();
            subCommands.add("give");
            subCommands.add("stats");
            
            if (sender.hasPermission("christmasgift.admin")) {
                subCommands.add("leaderboard");
                subCommands.add("remove");
                subCommands.add("removeall");
                subCommands.add("reset");
                subCommands.add("resetall");
                subCommands.add("reload");
            }
            
            return subCommands.stream()
                .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                .collect(Collectors.toList());
        }

        if (args.length >= 2 && args[0].equalsIgnoreCase("remove") && sender.hasPermission("christmasgift.admin")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Location loc = player.getLocation();
                
                if (args.length == 2) {
                    return Collections.singletonList(String.valueOf(loc.getBlockX()));
                } else if (args.length == 3) {
                    return Collections.singletonList(String.valueOf(loc.getBlockY()));
                } else if (args.length == 4) {
                    return Collections.singletonList(String.valueOf(loc.getBlockZ()));
                }
            }
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("reset") && sender.hasPermission("christmasgift.admin")) {
            return Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase()))
                .collect(Collectors.toList());
        }

        return completions;
    }
}
