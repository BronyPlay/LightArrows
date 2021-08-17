package ru.riku.lightarrow.handlers.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class LightChangeArrowHitEvent extends PlayerEvent {

    private Player spoller;

    private static final HandlerList handlers = new HandlerList();

    public LightChangeArrowHitEvent(Player who, Player spoller) {
        super(who);
        this.spoller = spoller;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getSpoller() {
        return spoller;
    }
}
