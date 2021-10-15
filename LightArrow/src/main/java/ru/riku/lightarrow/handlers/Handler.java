package ru.riku.lightarrow.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import ru.riku.lightarrow.LightArrow;
import ru.riku.lightarrow.handlers.events.*;
import ru.riku.lightarrow.utills.custom.Item;

import java.util.List;
import java.util.Objects;

public class Handler implements Listener {

    public void switcher(String value, ProjectileHitEvent event) {
        Player p = (Player) event.getHitEntity();
        switch (value) {
            case "TNT": {
                if (p != null) {
                    Bukkit.getPluginManager().callEvent(new LightTntArrowHitEvent(p));
                }
                break;
            }
            case "CLEAR": {
                if (p != null) {
                    Bukkit.getPluginManager().callEvent(new LightClearArrowHitEvent(p));
                }
                break;
            }
            case "CHANGE": {
                if (p != null) {
                    Bukkit.getPluginManager().callEvent(new LightChangeArrowHitEvent(p, (Player) event.getEntity().getShooter()));
                }
                break;
            }
            case "MIXITEMS": {
                if (p != null) {
                    Bukkit.getPluginManager().callEvent(new LightItemMixArrowHitEvent(p));
                }
                break;
            }
            case "TELEPORT": {
                if (p != null) {
                    Player shooter = (Player) event.getEntity().getShooter();
                    Location loc = p.getLocation();
                    loc.setYaw(shooter.getLocation().getYaw());
                    loc.setPitch(shooter.getLocation().getPitch());
                    loc.add(0,1,0);
                    Bukkit.getPluginManager().callEvent(new LightTeleportHitEvent(shooter, loc));
                }
                break;
            }
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent event) {
        if (event.getHitEntity() instanceof Player) {
            if (event.getEntity().hasMetadata("CUSTOMARROW")) {
                List<MetadataValue> value = event.getEntity().getMetadata("CUSTOMARROW");
                String[] values = value.get(0).asString().split(",");
                for (String v : values) {
                    switcher(v, event);
                }
            }
        }
        if (event.getHitBlock() != null) {
            if (event.getEntity().hasMetadata("CUSTOMARROW")) {
                List<MetadataValue> value = event.getEntity().getMetadata("CUSTOMARROW");
                if (value.get(0).asString().contains("BLACKHOLE-")) {
                    String[] val = value.get(0).asString().split("-");
                    Bukkit.getPluginManager().callEvent(new LightBlackHoleHitEvent(event.getHitBlock(), Integer.parseInt(val[1])));
                } else if(value.get(0).asString().contains("TELEPORT")) {
                    Player shooter = (Player) event.getEntity().getShooter();
                    Location loc = event.getHitBlock().getLocation();
                    loc.setYaw(shooter.getLocation().getYaw());
                    loc.setPitch(shooter.getLocation().getPitch());
                    loc.add(0,1,0);
                    Bukkit.getPluginManager().callEvent(new LightTeleportHitEvent(shooter, loc));
                }
                event.getEntity().remove();
            }
        }
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            if (event.getEntity().hasMetadata("CUSTOMARROW")) {
                List<MetadataValue> value = event.getEntity().getMetadata("CUSTOMARROW");
                if (value.get(0).asString().contains("AIM")) {
                    Bukkit.getPluginManager().callEvent(new LightAimArrowShootEvent(event.getEntity(), (Player) event.getEntity().getShooter()));
                }
            }
        }
    }

    @EventHandler
    public void onLaunch(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            ItemStack arrow = event.getArrowItem();
            if (arrow.getItemMeta().hasDisplayName()) {
                String name = arrow.getItemMeta().getDisplayName();
                Item item = Objects.requireNonNull(LightArrow.getItems().entrySet().stream()
                        .filter(stringItemEntry -> stringItemEntry.getValue().getItem().getItemMeta().getDisplayName().equals(name))
                        .findAny().orElse(null)).getValue();
                if (item != null) {
                    Entity newEntity = event.getProjectile();
                    newEntity.setMetadata("CUSTOMARROW", new FixedMetadataValue(LightArrow.getPlugin(LightArrow.class), item.getMeta()));
                    event.setProjectile(newEntity);
                }
            }
        }
    }

}
