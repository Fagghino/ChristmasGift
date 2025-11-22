package com.franchino961.christmasgift.listeners;

import com.franchino961.christmasgift.ChristmasGift;
import com.franchino961.christmasgift.data.GiftBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BlockInteractListener implements Listener {

    private final ChristmasGift plugin;

    public BlockInteractListener(ChristmasGift plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItemInHand();
        
        // Check if the placed block is a gift block
        if (isGiftBlock(item)) {
            if (!player.hasPermission("christmasgift.admin")) {
                event.setCancelled(true);
                player.sendMessage(plugin.getMessagesManager().getMessage("messages.no-permission"));
                return;
            }
            
            Location location = event.getBlock().getLocation();
            plugin.getDataManager().addGiftBlock(location);
            player.sendMessage(plugin.getMessagesManager().getMessage("messages.gift-placed"));
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Location location = block.getLocation();
        
        // Check if the broken block is a registered gift block
        if (plugin.getDataManager().isGiftBlock(location)) {
            Player player = event.getPlayer();
            
            // Admins can break gift blocks
            if (player.hasPermission("christmasgift.admin")) {
                plugin.getDataManager().removeGiftBlock(location);
                player.sendMessage(plugin.getMessagesManager().getMessage("messages.gift-removed"));
                return;
            }
            
            // Regular players cannot break gift blocks
            event.setCancelled(true);
            player.sendMessage(plugin.getMessagesManager().getMessage("messages.cannot-break"));
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }

        Location location = block.getLocation();
        
        if (!plugin.getDataManager().isGiftBlock(location)) {
            return;
        }

        event.setCancelled(true);
        Player player = event.getPlayer();
        GiftBlock giftBlock = plugin.getDataManager().getGiftBlock(location);

        if (giftBlock.isClaimed()) {
            player.sendMessage(plugin.getMessagesManager().getMessage("messages.already-claimed"));
            return;
        }

        // Claim the gift
        plugin.getDataManager().claimGiftBlock(location, player.getUniqueId());
        
        // Get stats
        int totalFound = plugin.getDataManager().getPlayerStats(player.getUniqueId());
        
        player.sendMessage(plugin.getMessagesManager().getMessage("messages.gift-claimed",
            "{found}", String.valueOf(totalFound)));

        // Handle block replacement
        if (plugin.getConfigManager().shouldReplaceBlock()) {
            String replacementType = plugin.getConfigManager().getReplacementBlockType();
            
            if (replacementType.equalsIgnoreCase("AIR")) {
                block.setType(Material.AIR);
            } else {
                try {
                    Material material = Material.valueOf(replacementType.toUpperCase());
                    block.setType(material);
                } catch (IllegalArgumentException e) {
                    plugin.getLogger().warning("Invalid replacement material: " + replacementType);
                    block.setType(Material.AIR);
                }
            }
        }
    }

    private boolean isGiftBlock(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return false;
        }

        String displayName = plugin.getConfigManager().getGiftBlockDisplayName();
        String itemDisplayName = item.getItemMeta().getDisplayName();
        
        return itemDisplayName != null && itemDisplayName.equals(displayName.replace("&", "§"));
    }
}
