package ru.riku.lightarrow.handlers.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;

public class LightAimArrowShootEvent extends EntityEvent {

    private Player player;
    private static final HandlerList handlers = new HandlerList();

    public LightAimArrowShootEvent(Entity entity, Player player) {
        super(entity);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
