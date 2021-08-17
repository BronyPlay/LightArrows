package ru.riku.lightarrow.handlers.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class LightClearArrowHitEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    public LightClearArrowHitEvent(Player who) {
        super(who);
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
