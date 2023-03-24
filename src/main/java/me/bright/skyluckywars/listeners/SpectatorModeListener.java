package me.bright.skyluckywars.listeners;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import me.bright.skylib.SPlayer;
import net.minecraft.server.v1_16_R3.DataWatcherObject;
import net.minecraft.server.v1_16_R3.DataWatcherRegistry;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SpectatorModeListener implements Listener {

    private JavaPlugin pluign;

    public SpectatorModeListener(JavaPlugin plugin) {
        this.pluign = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            if (SPlayer.getPlayer((Player)event.getEntity()).isSpectator()) {
              //  Bukkit.getLogger().info("spect2");
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void enitityDamageByEntity(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            Player dmger = (Player)event.getDamager();
            if(SPlayer.getPlayer(dmger).isSpectator()) {
           //     Bukkit.getLogger().info("spect");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPickupExpirience(PlayerPickupExperienceEvent event) {
        SPlayer sp = SPlayer.getPlayer(event.getPlayer());
        if(sp.isSpectator()) event.setCancelled(true);
    }

    @EventHandler
    public void onPickupItem(PlayerAttemptPickupItemEvent event) {
        SPlayer sp = SPlayer.getPlayer(event.getPlayer());
        if(sp.isSpectator()) event.setCancelled(true);
    }

    @EventHandler
    public void onHitProjective(ProjectileHitEvent event) {
        if(event.getHitEntity() instanceof Player) {
            SPlayer sp = SPlayer.getPlayer((Player)event.getHitEntity());
            if (sp.isSpectator()) {
                ((CraftLivingEntity) event.getHitEntity()).getHandle().getDataWatcher().set(new DataWatcherObject<>(10, DataWatcherRegistry.b),-1);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        SPlayer sp = SPlayer.getPlayer(event.getPlayer());
        if(sp.isSpectator()) {
            event.setCancelled(true);
           // Bukkit.getLogger().info("spect2");
        }
    }
}
