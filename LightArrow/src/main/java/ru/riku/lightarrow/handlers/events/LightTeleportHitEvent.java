package ru.riku.lightarrow.handlers.events;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.player.PlayerEvent;

public class LightTeleportHitEvent extends PlayerEvent {

        private Location location;
        private static final HandlerList handlers = new HandlerList();

        public LightTeleportHitEvent(Player who, Location loc) {
            super(who);
            this.location = loc.clone();
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

}


