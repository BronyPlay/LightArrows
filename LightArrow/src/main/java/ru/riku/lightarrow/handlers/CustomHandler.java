package ru.riku.lightarrow.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import ru.riku.lightarrow.LightArrow;
import ru.riku.lightarrow.handlers.events.*;
import ru.riku.lightarrow.utills.HomingTask;

public class CustomHandler implements Listener {

    @EventHandler
    public void onBlackHoleHit(LightBlackHoleHitEvent e) {
        Location loc = e.getLocation().add(0.0D, 2.0D, 0.0D);
        BukkitTask s = (new BukkitRunnable() {
            public void run() {
                e.getLocation().getNearbyEntities(e.getRadius(), 15.0D, e.getRadius()).forEach(entity -> {
                    if (entity instanceof Player) {
                        Vector direct = e.getLocation().toVector().subtract(entity.getLocation().toVector()).normalize();
                        entity.setVelocity(direct);
                    }
                });
                e.getLocation().getWorld().playEffect(loc, Effect.SMOKE, 1);
                e.getLocation().getWorld().playEffect(loc, Effect.SMOKE, 2);
                e.getLocation().getWorld().playEffect(loc, Effect.SMOKE, 3);
                e.getLocation().getWorld().playEffect(loc, Effect.SMOKE, 4);
                e.getLocation().getWorld().playEffect(loc, Effect.SMOKE, 5);
            }
        }).runTaskTimer(LightArrow.getInstance(), 5, 5);
        Runnable r = s::cancel;
        Bukkit.getScheduler().runTaskLater(LightArrow.getInstance(), r, 120L);
    }

    @EventHandler
    public void onChangeArrowHit(LightChangeArrowHitEvent e) {
        Player p = e.getPlayer();
        Player spoller = e.getSpoller();
        Location locs = spoller.getLocation();
        Location locp = p.getLocation();
         p.teleport(locs);
         spoller.teleport(locp);
    }

    @EventHandler
    public void onClearArrowHit(LightClearArrowHitEvent e) {
        Player p = e.getPlayer();
        for (PotionEffect type : p.getActivePotionEffects()) {
            p.removePotionEffect(type.getType());
        }
    }

    @EventHandler
    public void onItemMixArrowHit(LightItemMixArrowHitEvent event) {
        Player p = event.getPlayer();
        if ((p.getInventory().getItemInMainHand() != null) && (p.getInventory().getItemInOffHand() != null)) {
            ItemStack right = p.getInventory().getItemInMainHand();
            ItemStack left = p.getInventory().getItemInOffHand();
            p.getInventory().setItemInOffHand(right);
            p.getInventory().setItemInMainHand(left);
        }
    }

    @EventHandler
    public void onTntArrowHit(LightTntArrowHitEvent e) {
        Location location = e.getPlayer().getLocation();
        location.createExplosion(0.10F, false, false);
    }

   @EventHandler
    public void onAimArrowShoot(LightAimArrowShootEvent e) {
        if ((e.getEntity() !=null) && (e.getEntity() instanceof  Arrow)) {
            Player player = e.getPlayer();
            double minAngle = 6.283185307179586D;
            Entity minEntity = null;
            for (Entity entity : player.getNearbyEntities(64.0D, 64.0D, 64.0D)) {
                if ((player.hasLineOfSight(entity)) && ((entity instanceof LivingEntity)) && (!entity.isDead()))
                {
                    Vector toTarget = entity.getLocation().toVector().clone().subtract(player.getLocation().toVector());
                    double angle = e.getEntity().getVelocity().angle(toTarget);
                    if (angle < minAngle)
                    {
                        minAngle = angle;
                        minEntity = entity;
                    }
                }
            }
            if (minEntity != null) {
                new HomingTask((Arrow)e.getEntity(), (LivingEntity)minEntity, LightArrow.getInstance());
            }
        }
    }

}

