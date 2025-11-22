package com.franchino961.christmasgift.data;

import org.bukkit.Location;

import java.util.UUID;

public class GiftBlock {

    private final Location location;
    private boolean claimed;
    private UUID claimedBy;

    public GiftBlock(Location location, boolean claimed, UUID claimedBy) {
        this.location = location;
        this.claimed = claimed;
        this.claimedBy = claimedBy;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isClaimed() {
        return claimed;
    }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }

    public UUID getClaimedBy() {
        return claimedBy;
    }

    public void setClaimedBy(UUID claimedBy) {
        this.claimedBy = claimedBy;
    }
}
