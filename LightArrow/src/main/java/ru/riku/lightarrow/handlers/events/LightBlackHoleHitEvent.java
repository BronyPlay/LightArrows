package ru.riku.lightarrow.handlers.events;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

public class LightBlackHoleHitEvent extends BlockEvent {

    private Location location;
    private int radius;
    private static final HandlerList handlers = new HandlerList();

    public LightBlackHoleHitEvent(Block loc, int radius) {
        super(loc);
        this.location = loc.getLocation().clone();
        this.radius = radius;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Location getLocation() {
        return location;
    }

    public int getRadius() {
        return radius;
    }

}
